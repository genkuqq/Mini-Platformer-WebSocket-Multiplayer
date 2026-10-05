package org.genku.server.packets;

import org.genku.server.Equipment;
import org.genku.server.Player;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

public class Welcome {
    private static final ObjectMapper json = new ObjectMapper();
    String your_id;
    int catalog_version;
    int bank;
    String[] owned;
    Equipment equipped;
    Player player;
    public static String getPacket(String your_id, int bank, String[] owned, Equipment equipped, Collection<Player> players){
        Map<String, Object> welcomePacket = Map.of("your_id", your_id,"catalog_version",1, "bank", bank, "owned", owned, "equipped", equipped, "players", players);
        return json.writeValueAsString(Map.of("v", 1, "t", "Welcome", "p", welcomePacket));
    }
}