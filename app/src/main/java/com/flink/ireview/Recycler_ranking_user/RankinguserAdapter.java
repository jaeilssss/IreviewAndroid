package com.flink.ireview.Recycler_ranking_user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class RankinguserAdapter extends RecyclerView.Adapter<RankinguserAdapter.CustomViewHolder> {

    private ArrayList<RankinguserData> arrayList;

    public RankinguserAdapter(ArrayList<RankinguserData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RankinguserAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_user_item,parent, false);

        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankinguserAdapter.CustomViewHolder holder, int position) {
        holder.ranking_user_item_number.setText("1");
        holder.ranking_user_item_image.setImageResource(R.drawable.profile_human);
        holder.ranking_user_item_nickname.setText("evn.ha");
        holder.ranking_user_item_recommend.setText("추천수");
        holder.ranking_user_item_recommendnumber.setText("6");
        holder.ranking_user_item_commend.setText("댓글");
        holder.ranking_user_item_commendnumber.setText("700");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView ranking_user_item_number;
        protected ImageView ranking_user_item_image;
        protected TextView ranking_user_item_nickname;
        protected TextView ranking_user_item_recommend;
        protected TextView ranking_user_item_recommendnumber;
        protected TextView ranking_user_item_commend;
        protected TextView ranking_user_item_commendnumber;


        public CustomViewHolder(View itemView) {
            super(itemView);

            this.ranking_user_item_number = (TextView)itemView.findViewById(R.id.ranking_user_item_number);
            this.ranking_user_item_image = (ImageView) itemView.findViewById(R.id.ranking_user_item_image);
            this.ranking_user_item_nickname = (TextView)itemView.findViewById(R.id.ranking_user_item_nickname);
            this.ranking_user_item_recommend = (TextView)itemView.findViewById(R.id.ranking_user_item_recommend);
            this.ranking_user_item_recommendnumber = (TextView)itemView.findViewById(R.id.ranking_user_item_recommendnumber);
            this.ranking_user_item_commend = (TextView)itemView.findViewById(R.id.ranking_user_item_commend);
            this.ranking_user_item_commendnumber = (TextView)itemView.findViewById(R.id.ranking_user_item_commendnumber);
        }
    }
}
