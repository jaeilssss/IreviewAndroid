package com.flink.ireview.Recycler_ranking_product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class RankingproductAdapter extends RecyclerView.Adapter<RankingproductAdapter.CustomViewHolder> {

    private ArrayList<RankingproductData> arrayList;

    public RankingproductAdapter(ArrayList<RankingproductData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RankingproductAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item,parent, false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingproductAdapter.CustomViewHolder holder, int position) {
        holder.ranking_item_number.setText("1");
        holder.ranking_item_image.setImageResource(R.drawable.airpods);
        holder.ranking_item_company.setText("애플 (Apple)");
        holder.ranking_item_name.setText("airpods pro");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView ranking_item_number;
        protected ImageView ranking_item_image;
        protected TextView ranking_item_company;
        protected TextView ranking_item_name;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ranking_item_number = (TextView)itemView.findViewById(R.id.ranking_item_number);
            this.ranking_item_image = (ImageView) itemView.findViewById(R.id.ranking_item_image);
            this.ranking_item_company = (TextView)itemView.findViewById(R.id.ranking_item_company);
            this.ranking_item_name = (TextView)itemView.findViewById(R.id.ranking_item_name);
        }
    }
}
