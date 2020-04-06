package com.example.mud2k2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.SyncStateContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GestureDetector gestureDetector;
    private boolean gestureDown = false;
    private Point downAtCoords;

    public TextView getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(TextView selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    private TextView selectedCharacter = null;
    private Player player;

    public void GUILeft(View v) {
        Log.i("mud2k2", "This: "+this);
        this.moveTo(LEFT);
    }
    public void GUIRight(View v) {
        this.moveTo(RIGHT);
    }
    public void GUIInventory(View vx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(player.getContext());
        builder.setTitle("Inventory");


// add a list
        Log.i("mud2k2","Inventory size: "+player.getInventory().size());

        int numOfPlants = 0;
        for(AThing thing: player.getInventory()) {
                numOfPlants++;

        }

        final AThing[] objects = new AThing[numOfPlants];
        final String[] things = new String[numOfPlants];
        int c = 0;
        for(AThing thing: player.getInventory()) {
            Log.i("mud2k2","Thing before class test: "+thing);
                objects[c] = thing;
                things[c] = thing.getName();
                c++;
        }


        builder.setItems(things, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AThing thing = (AThing) objects[which];
                Log.i("mud2k2",which + "was chosen: "+objects[which]);

            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Attack (View v) {
        if (selectedCharacter != null) {
            String monsterid = selectedCharacter.getText().toString();
            Log.i("mud2k2", "Mud string: ATTACK " + monsterid);
            int p = recyclerView.getChildAdapterPosition(selectedCharacter);
            Log.i("mud2k2","Pos: "+p);
            MyAdapter ma = (MyAdapter)recyclerView.getAdapter();
            ma.removeAt(p);
        } else {
            Log.i("mud2k2", "Swing in the air");
        }
    }

    final static int LEFT = 1;
    final static int RIGHT = 2;
    final static int UP = 3;
    final static int DOWN = 4;

    public void updateControls() {
        if (player.getGameRoom().getLeft() == null)
            findViewById(R.id.buttonLeft).setEnabled(false);
        else if (player.getGameRoom().getLeft() != null)
            findViewById(R.id.buttonLeft).setEnabled(true);

        if (player.getGameRoom().getRight() == null)
            findViewById(R.id.buttonRight).setEnabled(false);
        else if (player.getGameRoom().getRight() != null)
            findViewById(R.id.buttonRight).setEnabled(true);

        if (player.getGameRoom().getUp() == null)
            findViewById(R.id.buttonUp).setEnabled(false);
        else if (player.getGameRoom().getUp() != null)
            findViewById(R.id.buttonUp).setEnabled(true);

        if (player.getGameRoom().getDown() == null)
            findViewById(R.id.buttonDown).setEnabled(false);
        else if (player.getGameRoom().getDown() != null)
            findViewById(R.id.buttonDown).setEnabled(true);
    }

    public void moveTo(int direction) {
        switch (direction) {
            case LEFT:
                GameRoom leftRoom = player.getGameRoom().getLeft();
                if (leftRoom != null) {
                    player.setGameRoom(leftRoom);
                    recyclerView.setAdapter(player.getGameRoom().getMyAdapter());
                    updateControls();
                }
                break;
            case RIGHT:
                GameRoom rightRoom = player.getGameRoom().getRight();
                if (rightRoom != null) {
                    player.setGameRoom(rightRoom);
                    recyclerView.setAdapter(player.getGameRoom().getMyAdapter());
                    updateControls();

                }
                break;
            case UP:
                GameRoom upRoom = player.getGameRoom().getUp();
                if (upRoom != null) {
                    player.setGameRoom(upRoom);
                    recyclerView.setAdapter(player.getGameRoom().getMyAdapter());
                    updateControls();

                }
                break;
            case DOWN:
                GameRoom downRoom = player.getGameRoom().getDown();
                if (downRoom != null) {
                    player.setGameRoom(downRoom);
                    recyclerView.setAdapter(player.getGameRoom().getMyAdapter());
                    updateControls();

                }
                break;
        }
    }

    public void searchForThings(int x, int y) {

    }

    public void addButtonsForSelectedThing(LinearLayout ll) {
        Log.i("mud2k2","HERE!");
        int p = recyclerView.getChildAdapterPosition(selectedCharacter);
        MyAdapter ma = (MyAdapter)recyclerView.getAdapter();
        final AThing a = ma.getThingAt(p);
        ll.removeAllViews();
//        Animal b = new Animal();
        ArrayList<Method> methods = a.getActions();
        for (final Method method : methods) {
            Log.i("mud2k2", "Method: "+method.getName());
            Button btn = new Button(this);
            String s = method.getName().substring(6);
            btn.setText(s);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("mud2k2","Button pushed a:"+a+" player: "+player+" " + method);
                    try {
                        method.invoke(a, player);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
            ll.addView(btn);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyAdapter mAdapter = new MyAdapter((RecyclerView) findViewById(R.id.rv1), this);



        GameRoom firstRoom = new GameRoom(mAdapter);
        GameRoom toLeft = new GameRoom(mAdapter);
        Dirt dirt = new Dirt(toLeft, mAdapter);
        dirt.setName("Pile of dirt");
        dirt.setWeight(2500);

        Gold g = new Gold(toLeft,mAdapter);
        g.setName("Some gold");


        ArrayList<AThing> arrayOfSomeThings = new ArrayList<>();
        arrayOfSomeThings.add(dirt);
        arrayOfSomeThings.add(g);
        toLeft.setThings(arrayOfSomeThings);

        firstRoom.setLeft(toLeft);
        toLeft.setRight(firstRoom);

        player = new Player(this, firstRoom,mAdapter,this);

        Nail n = new Nail(null, mAdapter);
        n.setName("Small nail");
        player.getInventory().add(n);

        Seed seedPlant = new Seed(null,mAdapter);
        seedPlant.setName("Pretty flower seed");
        seedPlant.setSeedModel(Flower.class);
        player.getInventory().add(seedPlant);

        final Context ct = this;
        final MainActivity activity = this;
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv1);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN) {
                    gestureDown = true;
                    downAtCoords = new Point((int) e.getX(), (int)e.getY());
                }
                if (e.getAction() == MotionEvent.ACTION_UP && gestureDown) {
                    gestureDown = false;
                    Point thisPoint = new Point((int)e.getX(), (int)e.getY());
                    if (downAtCoords.equals(thisPoint)) {
                        Log.i("mud2k2", "Was action up!");
                        Log.i("mud2k2", "View getID: " + recyclerView.getId());
                        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        final TextView tv = (TextView) view;
                        Log.i("mud2k2", "View touched: " + view + " " + tv.getText());
                        Log.i("mud2k2","id: "+tv.getId());
                        if (selectedCharacter == tv) {
                            // Deselect
                            tv.setAllCaps(false);
                            selectedCharacter = null;
                        } else {
                            if(selectedCharacter != null) {
                                selectedCharacter.setAllCaps(false);
                                selectedCharacter = null;
                            }

                            tv.setAllCaps(true);
                            selectedCharacter = tv;
                            /*
                            New object selected
                             */

                            LinearLayout ll = findViewById(R.id.buttonMenu);
                            activity.addButtonsForSelectedThing(ll);
/*                            Button b = new Button(ct);
                            b.setText("Hello");

                            ll.addView(b);*/
                        }
                    }

                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Log.i("mud2k2", "touch event");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        /*gestureDetector =
                new GestureDetectorCompat(this,new RecyclerViewDemoOnGestureListener());*/


/*        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("mud2k2", "Was action up!");
                }
                int[] locs;
                Log.i("mud2k2","class: "+event.getClass());
                Log.i("mud2k2", "HELLO"+v);
                Log.i("mud2k2","pointercount: "+event.getPointerCount());
                return false;
            }
        });*/

        //toLeft.setMyAdapter(mAdapter);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Plant plant1 = new Plant(firstRoom, mAdapter);
        plant1.setName("A plant");
        Animal animal1 = new Animal(firstRoom, mAdapter);
        animal1.setName("Kangaroo");
        Animal animal2 = new Animal(firstRoom, mAdapter);
        animal2.setName("Kangaroo");
        Animal animal3 = new Animal(firstRoom, mAdapter);
        animal3.setName("Kangaroo");
        Animal animal4 = new Animal(firstRoom, mAdapter);
        animal4.setName("Kangaroo");
        Animal animal5 = new Animal(firstRoom, mAdapter);
        animal5.setName("Kangaroo");
        //AThing[] things = {plant1,animal1,animal2,animal3,animal4,animal5};
        ArrayList<AThing> things = new ArrayList<>();// = {plant1,animal1,animal2,animal3,animal4,animal5};
        things.add(plant1);
        things.add(animal1);

        int i = 20;
        while(i > 0) {
            AThing a = new Animal(firstRoom, mAdapter);
            a.setName("Anml1");
            things.add(a);
            i--;
        }


        android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams)recyclerView.getLayoutParams();

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        lp.height = (int)(((double)height) * 0.5);

        ConstraintLayout cl = findViewById(R.id.holdingView);
        int btm = getWindow().getDecorView().getBottom();
        Log.i("mud2k2", "Height HERE: "+btm+" or here: "+cl.getHeight()+" or here: "+cl.getBottom()+" or! here: "+height);

        recyclerView.setLayoutParams(lp);



        firstRoom.setThings(things);
        mAdapter.setmDataset(things);

        ArrayList<AThing> dataset = (ArrayList<AThing>)mAdapter.getmDataset();

        Log.i("Mud2k2", "dataset length: "+dataset.size());

        //firstRoom.setMyAdapter(mAdapter);
        //firstRoom.setWindowRoom();
        recyclerView.setAdapter(mAdapter);

        updateControls();


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



    }


    /*
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add) {
            // fab click
            addItemToList();
        } else if (view.getId() == R.id.container_list_item) {
            // item click
            int idx = recyclerView.getChildPosition(view);
            if (actionMode != null) {
                myToggleSelection(idx);
                return;
            }
            DemoModel data = adapter.getItem(idx);
            View innerContainer = view.findViewById(R.id.container_inner_item);
            innerContainer.setTransitionName(SyncStateContract.Constants.NAME_INNER_CONTAINER + "_" + data.id);
            Intent startIntent = new Intent(this, CardViewDemoActivity.class);
            startIntent.putExtra(SyncStateContract.Constants.KEY_ID, data.id);
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, innerContainer, SyncStateContract.Constants.NAME_INNER_CONTAINER);
            this.startActivity(startIntent, options.toBundle());
        }
    }


    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            // Start the CAB using the ActionMode.Callback defined above
            actionMode = startActionMode(RecyclerViewDemoActivity.this);
            int idx = recyclerView.getChildPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }
*/


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected(android.view.MenuItem)">onOptionsItemSelected()</a></code>
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_inventory:
                GUIInventory(null);
                break;
        }
        return true;
    }
}
