package com.flink.ireview.ReviewRecycleView.like;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.http.Comment.CommentDeleteHttp;
import com.flink.ireview.http.board.getWriterInfoHttp;
import com.flink.ireview.http.board.reviewDisLikeHttp;
import com.flink.ireview.http.board.reviewDisLikeHttp2;
import com.flink.ireview.ui.recommendated_review.fragment_recommendated_review;
import com.flink.ireview.ui.review.ReviewReadPageFragment;

import java.util.ArrayList;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.CustomViewHolder>  {

    private ArrayList<Board> arrayList;
    private FragmentTransaction fragmentTransaction;
    private Context mcontext;
    private Member member;

    public RecommendationAdapter(ArrayList<Board> arrayList, FragmentTransaction fragmentTransaction, Context mcontext,Member member) {
        this.arrayList = arrayList;
        this.fragmentTransaction = fragmentTransaction;
        this.mcontext = mcontext;
        this.member = member;
    }

    @NonNull
    @Override
    public RecommendationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendated_review_item,parent,false);
        RecommendationAdapter.CustomViewHolder holder = new RecommendationAdapter.CustomViewHolder(view,this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.CustomViewHolder holder, int position) {
        holder.productName.setText(arrayList.get(position).getProductName());
        holder.title.setText(arrayList.get(position).getTitle());
        Glide.with(mcontext).load(arrayList.get(position).getImage1()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView image;
        protected TextView productName;
        protected TextView title;
        private ImageButton option;
        private RecommendationAdapter adapter;

        public CustomViewHolder(@NonNull View itemView,RecommendationAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            image = itemView.findViewById(R.id.recommendated_image);
            productName = itemView.findViewById(R.id.recommendated_review_product_name);
            title = itemView.findViewById(R.id.recommendated_review_item_product_title);
            option = itemView.findViewById(R.id.recommendated_button);
            option.setOnClickListener(onClickListener);
            image.setOnClickListener(onClickListener);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.recommendated_button :
                        adapter.select(getAdapterPosition(),0);
                        break;
                    case R.id.recommendated_image :
                        adapter.select(getAdapterPosition(),1);
                        break;
                }
            }
        };
    }
    public void select(int position,int control){
        if(control==1){
            // 1은 이미지 클릭
            getWriterInfoHttp http = new getWriterInfoHttp();
            http.setBodyContents(arrayList.get(position).getUserId());
            Member wmember  = http.send();
            Fragment fragment = new ReviewReadPageFragment(member,arrayList.get(position),wmember,0);
            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
        }else{
            // 옵션 버튼


            showRecommendationVer(position);

        }
    }
    void showRecommendationVer(final int num)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 게시물 좋아요를 취소하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reviewDisLikeHttp2 http = new reviewDisLikeHttp2();
                        http.setBodyContents(member.getId(),arrayList.get(num).getId());
                        if(http.send().equals("ok")){
                            fragment_recommendated_review recommendated_review = new fragment_recommendated_review(member.getId(),member);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,recommendated_review).commit();

                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }
}


