package com.flink.ireview.RecyclerView.Recycler_evnet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder> {

    private ArrayList<EventData> arrayList;

    public EventAdapter(ArrayList<EventData> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public EventAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.CustomViewHolder holder, int position) {
//        holder.event_photo.setImageResource(arrayList.get(position).getEvent_photo());
//        holder.event_name.setText(arrayList.get(position).getEvent_name());
//        holder.event_date.setText(arrayList.get(position).getEvent_date());
    }

    @Override
    public int getItemCount() {
//        return (null != arrayList ? arrayList.size() : 0);
        return 3;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView event_photo;
        protected TextView event_name;
        protected TextView event_date;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.event_photo = (ImageView) itemView.findViewById(R.id.event_photo);
            this.event_name = (TextView) itemView.findViewById(R.id.event_name);
            this.event_date = (TextView) itemView.findViewById(R.id.event_date);
        }
    }
}
