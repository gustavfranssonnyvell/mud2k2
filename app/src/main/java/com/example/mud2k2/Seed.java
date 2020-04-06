package com.example.mud2k2;

import android.util.Log;

public class Seed extends LivingThing {
    public Seed(GameRoom gr, MyAdapter myAdapter) {
        super(gr, myAdapter);
    }

    public Class getSeedModel() {
        return seedModel;
    }

    public void setSeedModel(Class seedModel) {
        this.seedModel = seedModel;
    }

    private Class seedModel;

    Plant spawn(GameRoom gr, MyAdapter myAdapter) {
        Plant p = null;
        try {
            p = (Plant)seedModel.newInstance();
            Log.i("mud2k2","Plant p: "+p);
            Class a = p.getClass();
            while(a != null) {
                Log.i("mud2k2", "Class: "+a.getName());
                a = a.getSuperclass();
            }
            p.setName("A "+p.getClass().getSimpleName());
            p.setGameRoom(gr);
            p.getGameRoom().setMyAdapter(myAdapter);

            p.getGameRoom().moveHere(p);
            p.getGameRoom().getMyAdapter().notifyDataSetChanged();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return p;
    }
}
