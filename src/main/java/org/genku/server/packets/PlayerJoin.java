package org.genku.server.packets;

import org.genku.server.Equipment;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class PlayerJoin {
    private static final ObjectMapper json = new ObjectMapper();
    String id;
    String name;
    float x;
    float y;
    Equipment equipment;
    public static String getPacket(String id,String name,float x,float y, Equipment equipped){
        Map<String, Object> playerJoinPacket = Map.of("id", id, "name",name,"x",x,"y",y,"equipped", equipped);
        return json.writeValueAsString(Map.of("v", 1, "t", "PlayerJoin", "p", playerJoinPacket));
    }
}