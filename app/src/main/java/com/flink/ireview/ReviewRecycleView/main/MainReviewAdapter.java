package com.flink.ireview.ReviewRecycleView.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.http.board.getWriterInfoHttp;
import com.flink.ireview.interfaces.goToNewFrag;
import com.flink.ireview.ui.review.ReviewReadPageFragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;

public class MainReviewAdapter extends RecyclerView.Adapter<MainReviewViewHolder> {

private Context mcontext;
private goToNewFrag newFrag;

private ArrayList<Board> list;
private FragmentTransaction fragmentTransaction ;

private Fragment fragment;

private Member member;

public Fragment getFragment() {
        return fragment;
        }

public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        }

public MainReviewAdapter(Context mcontext, Member member , FragmentTransaction fragmentTransaction, ArrayList<Board>list, goToNewFrag newFrag) {
        this.mcontext = mcontext;
        this.member = member;
        this.fragmentTransaction = fragmentTransaction;
        this.list = list;
        this.newFrag = newFrag;
        }

@NonNull
@Override
public MainReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mcontext, R.layout.category_list_item,null);
    MainReviewViewHolder reviewViewHolder = new MainReviewViewHolder(baseView , this);
        return reviewViewHolder;
        }
@Override
public void onBindViewHolder(@NonNull MainReviewViewHolder holder, int position) {
        if(list.size()!=0){
                Board board = list.get(position);
                        holder.title1.setText(board.getTitle());
                        holder.name.setText(board.getProductName());
        // 원래 이미지가 여러개 이므로 for문을 돌려야한다!!!
Glide.with(mcontext).load(board.getImage1()).into(holder.imageView1);
        }
        }
@Override
public int getItemCount() {
        if(list.size()==0){
                return 3;
        }else{
                return list.size();
        }
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
//                        newFrag.goToFrag(fragment).addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                }
        }

}
