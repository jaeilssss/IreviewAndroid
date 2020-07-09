package com.flink.ireview.ReviewRecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  TextView title1;
  ImageView imageView1;
  ReviewAdapter reviewAdapter ;
    public ReviewViewHolder(@NonNull View itemView, ReviewAdapter reviewAdapter) {
        super(itemView);
        this.reviewAdapter = reviewAdapter;
        title1 = itemView.findViewById(R.id.review_list_title1);
        imageView1 = itemView.findViewById(R.id.review_list_image1);

        imageView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.review_list_image1 :
                reviewAdapter.selectReview(getAdapterPosition());
                break;

        }
    }
}
