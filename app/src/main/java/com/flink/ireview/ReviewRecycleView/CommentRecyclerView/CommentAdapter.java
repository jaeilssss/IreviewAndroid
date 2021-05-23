package com.flink.ireview.ReviewRecycleView.CommentRecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.CommentDao;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Comment;
import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.Recomment;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.http.Comment.CommentDeleteHttp;
import com.flink.ireview.http.Reply.ReplyListHttp;
import com.flink.ireview.http.User.MyInfoHttp;
import com.flink.ireview.ui.review.ReviewReadPageFragment;
import com.flink.ireview.ui.review.fragment_recomment;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

private Context mcontext;

private FragmentTransaction fragmentTransaction;

private Member member ;
private ArrayList<Comment> list;
private ArrayList<Member> cmember;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStart(int start) {
        this.start = start;
    }


    private int position;
private int start;
private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public CommentAdapter(Context mcontext, FragmentTransaction fragmentTransaction, Member member, ArrayList<Comment> list , ArrayList<Member> cmember){
        this.mcontext = mcontext;
        this.fragmentTransaction = fragmentTransaction;
        this.member = member;
        this.list = list;
        this.cmember = cmember;
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

        Comment comment = list.get(position);
        Member cwriter = cmember.get(position);
        holder.nickname.setText(cwriter.getNickName());
        holder.date.setText(comment.getCreatedAt().toString());
        holder.content.setText(comment.getContentString());
        holder.comment_like_number.setText(String.valueOf(comment.getLikeCount()));
        MyInfoHttp http = new MyInfoHttp();
        holder.replyCount.setText(String.valueOf(comment.getReplyCount()));
        http.setBodyContents(comment.getUserId());
        if(!cwriter.getSumNailImage().equals("null")) {
            Glide.with(mcontext).load(cwriter.getSumNailImage()).into(holder.photo);
        }
        if(member!=null) {
            if (member.getNickName().equals(cwriter.getNickName())){
                holder.delete.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void commentDelete(int positon){
        showCommentVer(positon);
    }

    public void goToReComment(int position){

        Fragment fragment =  new fragment_recomment(member,0,list.get(position).getReviewId(),list.get(position),cmember.get(position).getNickName(),cmember.get(position).getSumNailImage());
    fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
    }

    void showCommentVer(final int num)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 댓글을 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CommentDeleteHttp http = new CommentDeleteHttp();
                        http.setBodyContents(list.get(num).getId(),list.get(num).getReviewId());
                        String result = http.send();
                        if(result.equals("ok")){
                            Toast.makeText(mcontext,"삭제가 완료되었습니다",Toast.LENGTH_SHORT).show();

                            board.setTotalComment(board.getTotalComment()-1);
                            Fragment fragment = new ReviewReadPageFragment(member,board,cmember.get(num),start);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mcontext,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
