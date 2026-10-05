package org.genku.server.packets;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class Ping {
    int at;
    private static final ObjectMapper json = new ObjectMapper();
    public static String getPacket(int at){
        Map<String, Object> pingPacket = Map.of("at", at);
        return json.writeValueAsString(Map.of("v", 1, "t", "Ping", "p", pingPacket));
    }
}
