package com.flink.ireview.Recycler_alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.CustomViewHolder>{

    private ArrayList<AlarmData> arrayList;

    public AlarmAdapter(ArrayList<AlarmData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AlarmAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmAdapter.CustomViewHolder holder, int position) {

        holder.alarm_image_profile.setImageResource(arrayList.get(position).getAlarm_image_profile());
        holder.alarm_item_id.setText(arrayList.get(position).getAlarm_item_id());
        holder.alarm_item_goodtext.setText(arrayList.get(position).getAlarm_item_goodtext());
        holder.alarm_item_time.setText(arrayList.get(position).getAlarm_item_time());
        holder.alarm_image_post.setImageResource(arrayList.get(position).getAlarm_image_post());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_Name = holder.alarm_item_id.getText().toString();
                Toast.makeText(v.getContext(), current_Name, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override

    public int getItemCount() {
        return (null != arrayList ? arrayList.size() :0);
    }

    public void remove(int position){
        try{
           arrayList.remove(position);
           notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView alarm_image_profile;
        protected TextView alarm_item_id;
        protected TextView alarm_item_goodtext;
        protected TextView alarm_item_time;
        protected ImageView alarm_image_post;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.alarm_image_profile = (ImageView) itemView.findViewById(R.id.alarm_image_profile);
            this.alarm_item_id = (TextView) itemView.findViewById(R.id.alarm_item_id);
            this.alarm_item_goodtext = (TextView) itemView.findViewById(R.id.alarm_item_goodtext);
            this.alarm_item_time = (TextView) itemView.findViewById(R.id.alarm_item_time);
            this.alarm_image_post = (ImageView) itemView.findViewById(R.id.alarm_image_post);

        }
    }
}
