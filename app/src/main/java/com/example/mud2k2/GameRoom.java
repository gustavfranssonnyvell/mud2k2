package com.example.mud2k2;

import android.util.Log;

import java.util.ArrayList;

public class GameRoom {
    public ArrayList<AThing> getThings() { return things; }
    public void setThings(ArrayList<AThing> things) { this.things = things; }
    public ArrayList<AThing> searchForThings() { return null; }

    public GameRoom(MyAdapter myAdapter) {
        this.things = new ArrayList<>();
        this.myAdapter = myAdapter;
    }

    private ArrayList<AThing> things;

    public GameRoom getUp() {
        return up;
    }

    public void setUp(GameRoom up) {
        this.up = up;
    }

    public GameRoom getLeft() {
        return left;
    }

    public void setLeft(GameRoom left) {
        this.left = left;
    }

    public GameRoom getRight() {
        return right;
    }

    public void setRight(GameRoom right) {
        this.right = right;
    }

    public GameRoom getDown() {
        return down;
    }

    public void setDown(GameRoom down) {
        this.down = down;
    }

    private GameRoom up, left, right, down;

    public MyAdapter getMyAdapter() {
        return myAdapter;
    }

    public void setWindowRoom() {
        myAdapter.setmDataset(this.things);
    }

    public void setMyAdapter(MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    private MyAdapter myAdapter;

    public void moveHere(AThing thing) {
        Log.i("mudk2k","In here 2, adapter: "+this.myAdapter);
        thing.getGameRoom().moveFromHere(thing);
        things.add(thing);
        myAdapter.notifyDataSetChanged();
    }
    public  void moveFromHere(AThing thing) {
        Log.i("mud2k2","move from here: "+thing);
        things.remove(thing);
        if (myAdapter.getmDataset() != null)
            myAdapter.notifyItemRemoved(myAdapter.getmDataset().indexOf(thing));
        myAdapter.notifyDataSetChanged();

    }
}
