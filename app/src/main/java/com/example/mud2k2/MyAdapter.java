package com.example.mud2k2;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<AThing> getmDataset() {
        return mDataset;
    }

    MainActivity mainActivity;
    RecyclerView rv;


    //private String[] mDataset;
    private ArrayList<AThing> mDataset;

    public void setmDataset(ArrayList<AThing> newData) {
        if (newData != null)
            this.mDataset = newData;
    }

    public void addThing(AThing thing) {
        mDataset.add(thing);
    }
    public void removeThing(AThing thing) {
        mDataset.remove(thing);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public MyAdapter(RecyclerView rv, MainActivity activity) {
        this.rv = rv;
        this.mainActivity = activity;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(AThing[] myDataset) {
        ArrayList<AThing> l = new ArrayList<>();
//        l.add()
        for (AThing s : myDataset) {
            l.add(s);
        }
        mDataset = l;
        //mDataset = myDataset;
    }

    public AThing getThingAt(int position) {
        return mDataset.get(position);
    }

    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        //parent.setMinimumHeight(300);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // Default setting for textView-element in recyclerView
        holder.textView.setText(mDataset.get(position).getName());
        holder.textView.setTextSize(30);
        holder.textView.setAllCaps(false); // Default setting, not caps
        mainActivity.setSelectedCharacter(null);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}