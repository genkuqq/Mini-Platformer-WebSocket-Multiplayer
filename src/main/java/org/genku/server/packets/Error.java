package org.genku.server.packets;

import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class Error {
    String code;
    String message;
    private static final ObjectMapper json = new ObjectMapper();
    public static String getPacket(String code,String message){
        Map<String, Object> errorPacket = Map.of("code", code,"message",message);
        return json.writeValueAsString(Map.of("v", 1, "t", "Error", "p", errorPacket));
    }
}
