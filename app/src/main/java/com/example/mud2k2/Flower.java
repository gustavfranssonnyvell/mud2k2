package com.example.mud2k2;

public class Flower extends Plant {
    public Flower(GameRoom gameRoom, MyAdapter myAdapter) {
        super(gameRoom, myAdapter);
    }
    public Flower() { super();}
    public void actionPick(Player player) {
        this.getGameRoom().moveFromHere(this);
        player.getInventory().add(this);
    }
}
