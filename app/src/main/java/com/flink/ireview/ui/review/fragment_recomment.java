package com.flink.ireview.ui.review;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Comment;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.Recomment;
import com.flink.ireview.R;
import com.flink.ireview.RecyclerView.Recycler_recomment.RecommentAdapter;
import com.flink.ireview.RecyclerView.Recycler_recomment.RecommentData;
import com.flink.ireview.http.Comment.CommentWriteHttp;
import com.flink.ireview.http.Reply.ReplyListHttp;
import com.flink.ireview.http.Reply.ReplyWriteHttp;
import com.flink.ireview.http.User.UserGetInfoHttp;
import com.flink.ireview.http.board.reviewDisLikeHttp;
import com.flink.ireview.http.board.reviewLikeUphttp;
import com.flink.ireview.ui.Category.fragment_category;


import java.util.ArrayList;

public class fragment_recomment extends Fragment {


    private RecommentAdapter recommentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Member member;
    private Long boardNo;
    private Long commentNo;
    private TextView nickname , content , date;
    private EditText recommentEdit;
    private TextView like_count, reply_count;
    private ImageButton recommentButton;
    private ImageView photo;
    private int start;
    LayoutInflater inflater;
     ViewGroup container;
  Bundle savedInstanceState;
  Comment comment;
  String wnickname , wphoto;
    public fragment_recomment(Member member, int start , Long boardNo, Comment comment, String wnickname ,String wphoto ) {
        this.member = member;
        this.boardNo = boardNo;
        this.start=start;
        this.comment = comment;
        this.wnickname = wnickname;
        this.wphoto = wphoto;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recomment, container, false);
            this.inflater = inflater ;
            this.container = container;
            this.savedInstanceState = savedInstanceState;
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_recomment);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recommentEdit = view.findViewById(R.id.review_recomment_edit_comment);
        nickname = view.findViewById(R.id.recomment_comment_nickname);
        photo = view.findViewById(R.id.recomment_comment_photo);
        if(!wphoto.equals("null")){
            Glide.with(this).load(wphoto).into(photo);
        }
        date = view.findViewById(R.id.recomment_comment_date);
        content = view.findViewById(R.id.recomment_comment_content);
        nickname.setText(wnickname);
        like_count = view.findViewById(R.id.recomment_comment_like_number);
        like_count.setText(String.valueOf(comment.getLikeCount()));
        content.setText(comment.getContentString());
        date.setText(comment.getCreatedAt());
        ReplyListHttp http = new ReplyListHttp();
        http.setBodyContents(comment.getId(),start);
        ArrayList<Recomment> arrayList = http.send();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        recommentAdapter = new RecommentAdapter(getContext(),arrayList,member,fragmentTransaction,boardNo,start,comment,wnickname,wphoto);
        recyclerView.setAdapter(recommentAdapter);
        recommentButton = view.findViewById(R.id.review_recomment_comment_submit);
        recommentButton.setOnClickListener(onClickListener);
        reply_count = view.findViewById(R.id.recomment_comment_count);
        reply_count.setText(String.valueOf(comment.getReplyCount()));
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.review_recomment_comment_submit :
                    if(member==null){
                        Toast.makeText(getContext(),"로그인 후 이용가능 합니다",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    String edit = recommentEdit.getText().toString();
                    if(edit.length()!=0){
                        ReplyWriteHttp http = new ReplyWriteHttp();
                     http.setBodyContents(member.getId(),comment.getId(),boardNo,edit);
                     if(http.send().equals("OK")){
                         Toast.makeText(getContext(),"댓글 작성이 완료 되었습니다 ",Toast.LENGTH_SHORT).show();

                         // 이거 그 대댓글 페이지 에서 댓글 다시 서버에서 가저오는 방식이 아니라서 일단 여기서 플러스 1 처리됌
                         //  나중에 좀 더 고민해서 수정해봐야됌 첨 부터 대댓글 페이지에서  코맨트 데이터를 가저올지!!
                         comment.setReplyCount(comment.getReplyCount()+1);
                         Fragment fragment = new fragment_recomment(member, start ,boardNo,comment,wnickname,wphoto);
                         getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                         break;
                     }
                    }
            }
        }
    };


}
