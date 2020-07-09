package com.flink.ireview.Recycler_category_tree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class CategorytreeAdapter extends RecyclerView.Adapter<CategorytreeAdapter.CustomViewHolder> {

    private ArrayList<CategorytreeData> arrayList;

    public CategorytreeAdapter(ArrayList<CategorytreeData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CategorytreeAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tree_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategorytreeAdapter.CustomViewHolder holder, int position) {

        holder.category_tree_item.setText("전체 보기");

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView category_tree_item;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category_tree_item = (TextView)itemView.findViewById(R.id.category_tree_item);

        }
    }
}
