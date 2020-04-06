package com.example.mud2k2;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class Player {
    public ArrayList<AThing> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<AThing> inventory) {
        this.inventory = inventory;
    }

    private ArrayList<AThing> inventory;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    private MyAdapter mAdapter;
    private int iterator = 0;

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom) {
        if (gameRoom != null) {
            iterator++;
            Log.i("mud2k2", "Setting room " + gameRoom);
            ArrayList<AThing> things = gameRoom.getThings();
            Log.i("mud2k2", "Things: " + things + " mAdapter: " + mAdapter);
            mAdapter.setmDataset(things);
            Log.i("mud2k2", "Got here!");
            mAdapter.notifyDataSetChanged();

            EditText et = mainActivity.findViewById(R.id.editText2);
            et.setText(et.getText() + "\nNew room! " + iterator);
            et.setMovementMethod(new ScrollingMovementMethod());
        }
        this.gameRoom = gameRoom;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private MainActivity mainActivity;
    private GameRoom gameRoom;
    int x, y;
    Player(Context c, GameRoom gr, MyAdapter mAdapter, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.context = c;
        this.gameRoom = gr;
        this.mAdapter = mAdapter;
        this.inventory = new ArrayList<>();
    }
}
