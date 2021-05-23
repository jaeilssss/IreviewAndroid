package com.flink.ireview.ReviewRecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

public class ReviewViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener  {

    TextView title1,recommend,comment,productName;
    ImageView imageView1;
    ReviewAdapter2 reviewAdapter ;
    public ReviewViewHolder2(@NonNull View itemView, ReviewAdapter2 reviewAdapter) {
        super(itemView);
        this.reviewAdapter = reviewAdapter;
        title1 = itemView.findViewById(R.id.review_list2_title1);
        imageView1 = itemView.findViewById(R.id.review_list2_image1);
        recommend = itemView.findViewById(R.id.review_list2_recommend);
        comment = itemView.findViewById(R.id.review_list2_comment);
    productName = itemView.findViewById(R.id.review_list2_name);
        imageView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.review_list2_image1 :
                reviewAdapter.selectReview(getAdapterPosition());
                break;
        }
    }
}
