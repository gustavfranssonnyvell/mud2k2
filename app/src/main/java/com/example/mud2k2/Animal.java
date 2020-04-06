package com.example.mud2k2;

import android.util.Log;

import java.util.Random;

public class Animal extends LivingThing {
    public Animal(GameRoom gameRoom, MyAdapter myAdapter) {
        super(gameRoom, myAdapter);
    }
    public int actionPet(Player playerPetting) {
        Log.i("mud2k2","Animal petted");

        return 20;
    }
    public int actionKill(Player player) {
        this.getGameRoom().moveFromHere(this);
        return 0;
    }
}
