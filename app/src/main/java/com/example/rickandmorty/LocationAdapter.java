package com.example.rickandmorty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<Location> locations;
    private LayoutInflater mInflater;
    public LocationAdapter(List<Location> locations) {
//        this.mInflater = LayoutInflater.from(context);
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View locationView = inflater.inflate(R.layout.item_locations, parent, false);
//        View locationView = mInflater.inflate(R.layout.item_locations, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.textView_name.setText(location.getName());
        holder.textView_type.setText(location.getType());
        holder.textView_dimension.setText(location.getDimension());
    }

//    void setNewList(List<Location> location){
//        this.locations = location;
//    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_name;
        TextView textView_type;
        TextView textView_dimension;



        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.name_textView);
            textView_type = itemView.findViewById(R.id.type_textView);
            textView_dimension = itemView.findViewById(R.id.dimension_textView);

        }

        @Override
        public void onClick(View v) {
            System.out.println("hihihihihihihihi");
        }
    }
}
