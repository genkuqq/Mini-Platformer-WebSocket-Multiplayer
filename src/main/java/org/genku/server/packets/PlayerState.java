package org.genku.server.packets;

import org.genku.server.Equipment;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class PlayerState {
    private static final ObjectMapper json = new ObjectMapper();
    public String id;
    public float x;
    public float y;
    public float vx;
    public float vy;
    public int facing;
    public int level;
    public Equipment equipped;
    public static String getPacket(String id,float x,float y,float vx, float vy, int facing, Equipment equipped,int level){
        Map<String, Object> playerStatePacket = Map.of("id", id,"x",x,"y",y,"vx",vx,"vy",vy,"facing",facing,"equipped",equipped,"level",level);
        return json.writeValueAsString(Map.of("v", 1, "t", "PlayerState", "p", playerStatePacket));
    }

    public static String getPacket(PlayerState playerState) {
        Map<String, Object> playerStatePacket = Map.of("id", playerState.id,"x",playerState.x,"y",playerState.y,"vx",playerState.vx,"vy",playerState.vy,"facing",playerState.facing,"equipped",playerState.equipped,"level",playerState.level);
        return json.writeValueAsString(Map.of("v", 1, "t", "PlayerState", "p", playerStatePacket));
    }
}
