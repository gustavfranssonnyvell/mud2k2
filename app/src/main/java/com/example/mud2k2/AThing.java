package com.example.mud2k2;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class AThing {
    public AThing(GameRoom gr, MyAdapter myAdapter){
        this.gameRoom = gr;
        Log.i("mud2k2","gr: "+gr);

        if (gr != null) {
            gr.setMyAdapter(myAdapter);
            gr.moveHere(this);
        }
    }
    public AThing() {}
    public GameRoom getGameRoom() {
        return gameRoom;
    }
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    private GameRoom gameRoom;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Method>getActions() {
        Log.i("mud2k2","enumeration actions");
        ArrayList<Method> methods = new ArrayList<>();
        for (Method m:this.getClass().getMethods()) {
            Log.i("mud2k2","Method: " + m);
            if (m.getName().startsWith("action"))
            methods.add(m);
        }
        return methods;
    }


    private String name;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    private float weight; // In grams

    int actionPickup(Player player) {
        return 0;
    }
}
