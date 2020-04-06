package com.example.mud2k2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.AccessController.getContext;

public class Dirt extends AThing {
    public Dirt(GameRoom gr, MyAdapter myAdapter) {
        super(gr, myAdapter);
    }

    public void actionPlantFromInventory(final Player player) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(player.getContext());
        builder.setTitle("Choose a living thing from inventory");


// add a list
        Log.i("mud2k2","Inventory size: "+player.getInventory().size());

        int numOfPlants = 0;
        for(AThing thing: player.getInventory()) {
            if (Seed.class.isAssignableFrom(thing.getClass()))
                numOfPlants++;

        }

        final AThing[] objects = new AThing[numOfPlants];
        final String[] plants = new String[numOfPlants];
        int c = 0;
        for(AThing thing: player.getInventory()) {
            Log.i("mud2k2","Thing before class test: "+thing);
            if (Seed.class.isAssignableFrom(thing.getClass())) {
                Log.i("mud2k2", "HEREEE!!!! Seed! c="+c+", thing: "+thing);
                objects[c] = thing;
                plants[c] = thing.getName();
                c++;
                Log.i("mud2k2","Still here");
            }
        }

        final Dirt d = this;

        builder.setItems(plants, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Seed seed = (Seed)objects[which];
                Log.i("mud2k2",which + "was chosen: "+objects[which]);
                player.getInventory().remove(seed);
                Log.i("mud2k2","Trying to remove d: "+d+" which is in room: "+d.getGameRoom());
                d.getGameRoom().moveFromHere(d);
                for(AThing t: d.getGameRoom().getThings())
                    Log.i("mud2k2","In room: "+t);
                seed.spawn(d.getGameRoom(), d.getGameRoom().getMyAdapter());

                RecyclerView recyclerView = (RecyclerView) player.getMainActivity().findViewById(R.id.rv1);
                int c = 0;
                while(recyclerView.getChildCount() < c) {
                    View child = recyclerView.getChildAt(c);
                    TextView text = (TextView)child;
                    text.setAllCaps(false);
                    Log.i("mud2k2", "Text object: "+text);
                    c++;
                }

            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();










        //---------------------------------
        /*// setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(player.getContext());
        builder.setTitle("Choose a thing from inventory");

        //this.getGameRoom().searchForThings();

        // add a list
        //        String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        /*ArrayList<AThing> thingsFromInventory = player.getInventory();
        Log.i("mud2k2","Thins from inventory: "+thingsFromInventory);
        String[] strings = new String[thingsFromInventory.size()];
        int i = 0;
        for(AThing o: thingsFromInventory) {
            strings[i++] = ((AThing)o).getName();
        }
        Log.i("mud2k2","Strings: "+strings);


        String[] brutalthings = strings;//{"a","b"};
        Log.i("mud2k2","Brutalthings: "+brutalthings);

        builder.setItems(brutalthings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // horse
                    case 1: // cow
                    case 2: // camel
                    case 3: // sheep
                    case 4: // goat
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.i("mud2k2", "Hello from plant from inventory");*/
    }
}
