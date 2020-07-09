package com.flink.ireview.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{
    private ArrayList<PostItem> arrayList;

    public CustomAdapter(ArrayList<PostItem> arrayList){
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postitem,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    //실제적으로 매칭
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textView1.setText(arrayList.get(position).getName1());
        holder.textView2.setText(arrayList.get(position).getName2());
        holder.textView2.setText(arrayList.get(position).getName2());
    }

    //삼항연산자
    @Override
    public int getItemCount() {
        return (arrayList!=null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView1=itemView.findViewById(R.id.rvt_1_1);
            this.textView2=itemView.findViewById(R.id.rvt_1_2);
            this.textView3=itemView.findViewById(R.id.rvt_1_3);
        }
    }
}
