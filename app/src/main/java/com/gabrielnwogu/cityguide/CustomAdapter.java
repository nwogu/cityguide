package com.gabrielnwogu.cityguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    private ArrayList<Place> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView placeName;
        TextView placeAddress;
        ImageView placeImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.placeName = (TextView) itemView.findViewById(R.id.place_name);
            this.placeAddress = (TextView) itemView.findViewById(R.id.place_address);
            this.placeImage = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(ArrayList<Place> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.placeName;
        TextView textViewVersion = holder.placeAddress;
        ImageView imageView = holder.placeImage;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(dataSet.get(listPosition).getAddress());
        Picasso.get().load(dataSet.get(listPosition).getPhoto()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
