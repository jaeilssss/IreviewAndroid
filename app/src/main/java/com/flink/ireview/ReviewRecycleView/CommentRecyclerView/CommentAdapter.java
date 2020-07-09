package com.flink.ireview.ReviewRecycleView.CommentRecyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Dao.CommentDao;
import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

private Context mcontext;

private FragmentTransaction fragmentTransaction;

private ArrayList<CommentDto> list;

private UsersDto udto ;

private ReviewDto rdto;
   private String category;

    public CommentAdapter(Context mcontext, FragmentTransaction fragmentTransaction, ArrayList<CommentDto> list, ReviewDto rdto, UsersDto udto, String category) {
        this.mcontext = mcontext;
        this.fragmentTransaction = fragmentTransaction;
        this.list = list;
        this.udto = udto;
        this.rdto =rdto;
        this.category = category;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseview = View.inflate(mcontext, R.layout.comment_item,null);

        CommentViewHolder commentViewHolder = new CommentViewHolder(baseview , this);

        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        CommentDto dto = list.get(position);
        holder.nickname.setText(dto.getComment_nickname());
        holder.date.setText(dto.getCreate_time());
        holder.content.setText(dto.getComment());
        if(udto.getUsers_nickname()!=null) {
            if (udto.getUsers_nickname().equals(dto.getComment_nickname())) {
                holder.delete.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void commentDelete(int positon){
        CommentDto dto = list.get(positon);
        CommentDao dao = new CommentDao(mcontext,fragmentTransaction);
        dao.delete(dto,rdto,udto,category);
    }
}
