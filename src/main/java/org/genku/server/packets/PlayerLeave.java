package org.genku.server.packets;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class PlayerLeave {
    private static final ObjectMapper json = new ObjectMapper();
    String id;
    public static String getPacket(String id){
        Map<String, Object> playerLeavePacket = Map.of("id", id );
        return json.writeValueAsString(Map.of("v", 1, "t", "PlayerLeave", "p", playerLeavePacket));
    }
}
