package org.genku.server.packets;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class Pong {
    int at;
    private static final ObjectMapper json = new ObjectMapper();
    public static String getPacket(int at){
        Map<String, Object> pongPacket = Map.of("at", at);
        return json.writeValueAsString(Map.of("v", 1, "t", "Pong", "p", pongPacket));
    }
}
