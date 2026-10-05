package org.genku.server;

import org.genku.server.packets.*;
import org.genku.server.packets.Error;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    ConcurrentHashMap<WebSocketSession, Player> players = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        players.put(session, new Player(session.getId()));
        System.out.println("WS OPEN: " + session.getId());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        players.remove(session);
        for (Map.Entry<WebSocketSession,Player> player : players.entrySet()){
            if (!player.getKey().getId().equals(session.getId()) && player.getKey().isOpen()){
                player.getKey().sendMessage(new TextMessage(PlayerLeave.getPacket(session.getId())));
            }
        }
        System.out.println("WS CLOSE: " + session.getId() + " " + status);
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable ex) throws Exception {
        System.out.println("WS ERROR: " + ex);
    }
    private final ObjectMapper json = new ObjectMapper();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received: "+payload);
        JsonNode payloadRoot;
        payloadRoot = json.readTree(payload);
        int version = payloadRoot.path("v").asInt();
        String type = payloadRoot.path("t").asString();
        JsonNode packet = payloadRoot.path("p");
        switch (type){
            case "Hello" ->{
                Hello h = json.treeToValue(packet, Hello.class);
                players.put(session,new Player(session.getId(),h.name,h.equipped));
                session.sendMessage(new TextMessage(Welcome.getPacket(session.getId(),0, new String[]{"skin_mavi", "scarf_kirmizi","sapka_yok"},h.equipped,players.values().stream().filter(player -> player.level == 0).collect(Collectors.toSet()))));
                for (Map.Entry<WebSocketSession,Player> player : players.entrySet()){
                    if (!player.getKey().getId().equals(session.getId()) && player.getKey().isOpen()){
                        player.getKey().sendMessage(new TextMessage(PlayerJoin.getPacket(session.getId(),h.name,0,0,h.equipped)));
                    }
                }
            }
            case "PlayerState"->{
                PlayerState playerState = json.treeToValue(packet, PlayerState.class);
                Player p = players.get(session);
                p.x = playerState.x;
                p.y = playerState.y;
                p.vx = playerState.vx;
                p.vy = playerState.vy;
                p.facing = playerState.facing;
                p.equipped = playerState.equipped;
                p.level = playerState.level;
                for (Map.Entry<WebSocketSession,Player> player : players.entrySet()){
                    if (!player.getKey().getId().equals(session.getId()) && player.getKey().isOpen() && player.getValue().level == playerState.level){
                        player.getKey().sendMessage(new TextMessage(PlayerState.getPacket(playerState)));
                    }
                }
            }
            case "Ping" ->{
                session.sendMessage(new TextMessage(Pong.getPacket(Instant.now().getNano())));
            }
            default -> {
                session.sendMessage(new TextMessage(Error.getPacket("Bad Packet","Bad Packet Type.")));
            }
        }
    }
}
