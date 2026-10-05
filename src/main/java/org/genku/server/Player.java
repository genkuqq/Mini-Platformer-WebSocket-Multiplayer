package org.genku.server;

public class Player {
    public String id;
    public String name;
    public float x;
    public float y;
    public float vx;
    public float vy;
    public int facing;
    public Equipment equipped;
    public int level;
    public Player(String id, String name, Equipment equipped){
        this.id = id;
        this.name = name;
        this.equipped = equipped;
        this.x = 0;
        this.y = 0;
        this.vx = 0;
        this.vy = 0;
        this.facing = 0;
        this.level = 0;
    }

    public Player(String id) {
        this.id = id;
        this.name = "";
        this.equipped = null;
        this.x = 0;
        this.y = 0;
        this.vx = 0;
        this.vy = 0;
        this.facing = 0;
        this.level = 0;
    }
}
