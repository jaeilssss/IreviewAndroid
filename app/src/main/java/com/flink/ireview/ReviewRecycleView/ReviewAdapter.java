package com.flink.ireview.ReviewRecycleView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.CommentDao;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private Context mcontext;

    private ArrayList<ReviewDto> listItem;

    UsersDto udto;
    private FragmentTransaction fragmentTransaction ;

    private Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ReviewAdapter(Context mcontext, ArrayList<ReviewDto> listItem, FragmentTransaction fragmentTransaction, UsersDto udto) {
        this.mcontext = mcontext;
        this.listItem = listItem;
        this.fragmentTransaction = fragmentTransaction;
        this.udto = udto;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(mcontext, R.layout.category_list_item,null);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(baseView , this);
        return reviewViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewDto dto = listItem.get(position);
        holder.title1.setText(dto.getReview_main_title());
        // 원래 이미지가 여러개 이므로 for문을 돌려야한다!!!
        Glide.with(mcontext).load(dto.getReview_main_image().get(0)).into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return listItem.size();

    }
    public void selectReview(int position){
        //수정

        CommentDao dao = new CommentDao(mcontext , fragmentTransaction);
        ReviewDto dto = listItem.get(position);
        dao.goToReview(dto,udto,"test");
//        Fragment fragment = new ReviewReadPageFragment(dto,udto);
//        fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
    }

}
