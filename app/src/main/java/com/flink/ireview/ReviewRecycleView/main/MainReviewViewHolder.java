package com.flink.ireview.ReviewRecycleView.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.ReviewAdapter;

public class MainReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView title1,name;
    ImageView imageView1;
    MainReviewAdapter reviewAdapter ;
    public MainReviewViewHolder(@NonNull View itemView, MainReviewAdapter reviewAdapter) {
        super(itemView);
        this.reviewAdapter = reviewAdapter;
        title1 = itemView.findViewById(R.id.review_list_title1);
        name = itemView.findViewById(R.id.review_list_name);
        imageView1 = itemView.findViewById(R.id.review_list_image1);

        imageView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            reviewAdapter.selectReview(getAdapterPosition());
    }
}
