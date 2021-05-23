package com.flink.ireview.RecyclerView.Recycler_recomment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Comment;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.Recomment;
import com.flink.ireview.R;
import com.flink.ireview.http.Reply.ReplyDeleteHttp;
import com.flink.ireview.http.User.MyInfoHttp;
import com.flink.ireview.http.User.UserGetInfoHttp;
import com.flink.ireview.http.board.reviewDeleteHttp;
import com.flink.ireview.ui.Category.fragment_category;
import com.flink.ireview.ui.review.fragment_recomment;

import java.util.ArrayList;

public class RecommentAdapter extends RecyclerView.Adapter<RecommentAdapter.CustomViewHolder> {

    private ArrayList<Recomment> arrayList;
    private Context context;
    private Member member;
    FragmentTransaction fragmentTransaction;
    Long boardNo;
    int start;
    Comment comment;
    String wnickname;
    String wphoto;

    public RecommentAdapter(Context context , ArrayList<Recomment> arrayList, Member member, FragmentTransaction fragmentTransaction, Long boardNo,int start,Comment comment,
                            String wnickname,String wphoto) {
        this.arrayList = arrayList;
        this.context = context;
        this.member = member;
        this.fragmentTransaction = fragmentTransaction;
        this.boardNo = boardNo;
        this.start = start;
        this.comment = comment;
        this.wnickname = wnickname;
        this.wphoto = wphoto;

    }

    @NonNull
    @Override
    public RecommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomment_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommentAdapter.CustomViewHolder holder, int position) {

        Recomment recomment = arrayList.get(position);
        UserGetInfoHttp http = new UserGetInfoHttp();
        http.setBodyContents(recomment.getUserId());
        Member member2 = http.send();
     holder.recomment_nickname.setText(member2.getNickName());
        holder.recomment_date.setText(recomment.getCreatedAt());
        if(member2.getSumNailImage().equals("null")){
            holder.recomment_photo.setImageResource(R.drawable.profile_human);
        }else {
            Glide.with(context).load(member2.getSumNailImage()).into(holder.recomment_photo);
        }
        holder.recomment_content.setText(recomment.getReplyContent());
//        holder.recomment_like_button.setImageResource(R.drawable.btn_thumsup);
//        holder.recomment_like_number.setText("20");
        if(member!=null&& member.getId().equals(member2.getId())){
            holder.recomment_delete_button.setVisibility(View.VISIBLE);
        }
        holder.recomment_modify_button.setText("수정");
        holder.recomment_delete_button.setText("삭제");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView recomment_photo;
        protected TextView recomment_nickname;
        protected TextView recomment_date;
        protected TextView recomment_content;
        protected ImageView recomment_like_button;
        protected TextView recomment_like_number;
        protected TextView recomment_modify_button;
        protected TextView recomment_delete_button;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recomment_photo = (ImageView)itemView.findViewById(R.id.recomment_photo);
            this.recomment_nickname = (TextView)itemView.findViewById(R.id.recomment_nickname);
            this.recomment_date = (TextView)itemView.findViewById(R.id.recomment_date);
            this.recomment_content = (TextView)itemView.findViewById(R.id.recomment_content);
//            this.recomment_like_button = (ImageView)itemView.findViewById(R.id.recomment_like_button);
//            this.recomment_like_number = (TextView)itemView.findViewById(R.id.recomment_like_number);
            this.recomment_modify_button = (TextView) itemView.findViewById(R.id.recomment_modify_button);
            this.recomment_delete_button = (TextView) itemView.findViewById(R.id.recomment_delete_button);
            recomment_delete_button.setOnClickListener(onClickListener);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.recomment_delete_button :
                            show(getAdapterPosition());
                    }
            }
        };

}

    void show(final int position)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 댓글을 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReplyDeleteHttp http = new ReplyDeleteHttp();
                        http.setBodyContents(arrayList.get(position).getId(),comment.getId());
                                String result = http.send();
                                if(result.equals("OK")){
                                    Toast.makeText(context,"삭제가 되었습니다",Toast.LENGTH_SHORT).show();
                                    comment.setReplyCount(comment.getReplyCount()-1);
                                    Fragment fragment = new fragment_recomment(member,start,boardNo,comment,wnickname,wphoto);
                                    fragmentTransaction.addToBackStack(null)
                                            .replace(R.id.main_frame,fragment)
                                            .commit();
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
