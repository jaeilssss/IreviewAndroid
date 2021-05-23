package com.flink.ireview.ReviewRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.R;
import com.flink.ireview.http.board.getWriterInfoHttp;
import com.flink.ireview.ui.review.ReviewReadPageFragment;

import java.util.ArrayList;

public class ReviewAdapter2 extends RecyclerView.Adapter<ReviewViewHolder2> {

    private Context mcontext;

    private ArrayList<ReviewDto> listItem;

    private  ArrayList<Board> list;
    private FragmentTransaction fragmentTransaction ;

    private Fragment fragment;

    private Member member;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ReviewAdapter2(Context mcontext, Member member , FragmentTransaction fragmentTransaction, ArrayList<Board>list) {
        this.mcontext = mcontext;
        this.member = member;
        this.fragmentTransaction = fragmentTransaction;
        this.list = list;
    }

    @NonNull
    @Override
    public ReviewViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mcontext, R.layout.category_list_item2,null);
        ReviewViewHolder2 reviewViewHolder = new ReviewViewHolder2(baseView , this);
        return reviewViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder2 holder, int position) {
        Board board = list.get(position);
        holder.title1.setText(board.getTitle());
        // 원래 이미지가 여러개 이므로 for문을 돌려야한다!!!
        Glide.with(mcontext).load(list.get(position).getImage1()).override(holder.imageView1.getWidth(),holder.imageView1.getHeight()).fitCenter().into(holder.imageView1);
//        Glide.with(mcontext).load(list.get(position).getImage1()).thumbnail(1f).into(holder.imageView1);
//        (Drawable)Glide.with(mcontext).load(list.get(position));
        holder.recommend.setText(board.getTotalRecommend().toString());
        holder.comment.setText(board.getTotalComment().toString());
        holder.productName.setText(board.getProductName());

    }
    @Override
    public int getItemCount() {
        return list.size();

    }
    public void selectReview(int position){
        //수정
//        CommentDao dao = new CommentDao(mcontext , fragmentTransaction);
//        ReviewDto dto = listItem.get(position);
//        dao.goToReview(dto,udto,"test");
        Board board = list.get(position);
        getWriterInfoHttp whttp = new getWriterInfoHttp();
        whttp.setBodyContents(board.getUserId());
        Member wmember = whttp.send();
        if(wmember==null){
            Toast.makeText(mcontext,"error",Toast.LENGTH_SHORT).show();
        }else{
            Fragment fragment = new ReviewReadPageFragment(member, list,wmember,position,0);
//        Fragment fragment = new ReviewReadPageFragment(dto,udto);
            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
        }

    }

}
