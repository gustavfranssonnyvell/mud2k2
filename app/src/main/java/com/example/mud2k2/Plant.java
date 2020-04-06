package com.example.mud2k2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Plant extends LivingThing {
    public Plant(GameRoom gameRoom, MyAdapter myAdapter) {
        super(gameRoom, myAdapter);
    }
    public Plant() {
        super();
    }
    public void actionPlant(Player player) {
        // Plant somewhere
        Log.i("mud2k2", "Planting "+this);

        Log.i("mud2k2","Player context: "+player.getContext());


        /*
        AlertDialog alertDialog = new AlertDialog.Builder(player.context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();*/
    }
}

