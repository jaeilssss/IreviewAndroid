package com.flink.ireview.ui.review;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flink.ireview.Dao.CommentDao;
import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Comment;
import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.Event_ViewPagerAdapter;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.CommentRecyclerView.CommentAdapter;
import com.flink.ireview.classfile.Review;
import com.flink.ireview.http.Comment.CommentDeleteHttp;
import com.flink.ireview.http.Comment.CommentSetListHttp;
import com.flink.ireview.http.Comment.CommentWriteHttp;
import com.flink.ireview.http.User.UserGetInfoHttp;
import com.flink.ireview.http.board.getWriterInfoHttp;
import com.flink.ireview.http.board.reviewDeleteHttp;
import com.flink.ireview.http.board.reviewDisLikeHttp;
import com.flink.ireview.http.board.reviewLikeUphttp;
import com.flink.ireview.http.board.reviewSearchLikeHttp;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.ui.Category.fragment_category;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag1;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag2;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag3;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag4;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag5;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag6;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag7;
import com.flink.ireview.ui.ReviewImageListFrag.ImageFrag8;
import com.flink.ireview.viewPager.ReviewImageViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import  android.view.ContextMenu.ContextMenuInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewReadPageFragment extends Fragment {
    View view;
    ReviewImageViewPager viewPager;
    ReviewDto dto;
    private Context context;
    TextView review_page_title , review_page_nickname ,review_page_date, review_page_view_count , review_page_recommend , review_page_scrap ,review_page_content, review_page_advantage, review_page_weakness;
    ImageView wImage;
    TextView review_read_product_name;
    ImageButton option ,commentSubmit;
    Button btn ;
    TextView commentCount;
    Button delete;
    ReviewDao dao;
    UsersDto udto;
    EditText comment;
    ImageButton review_read_page_list, modify, comment_submit;
    TextView wNickName  , wdate ,wtitle , wcontent;
    ArrayList<Board> list;
    private int position;
    int i;
    String checkedLike;
    FirebaseUser user = null;
    FirebaseFirestore db ;
    CheckBox like;
    FragmentTransaction fragmentTransaction;
    private Member member,wmember;
    private int categoryId;
    private Board board;
    private ViewPager viewPageradapter;
    private int start;
    TabLayout tabLayout ;

    public ReviewReadPageFragment(Member member, ArrayList<Board> list ,Member wmember,int position,int start) {
        this.member = member;
        this.list = list;
        this.wmember = wmember;
        this.position = position;
        this.start =start;
    }

    public ReviewReadPageFragment(Member member, Board board ,Member wmember,int start) {
        this.member = member;
        this.board = board;
        this.wmember = wmember;
        list = null;
        this.start = start;
    }


    public ReviewReadPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_review_read_page, container, false);


        if(list!=null){
            board = list.get(position);
        }
        viewPager = new ReviewImageViewPager(getFragmentManager(), getContext());
        context = getContext();
        viewPageradapter = view.findViewById(R.id.review_image_view_pager);
        tabLayout = view.findViewById(R.id.review_read_tab);
        review_read_product_name = view.findViewById(R.id.review_read_product_name);
        review_read_product_name.setText(board.getProductName());
        setReviewImage(board);

        fragmentTransaction = getFragmentManager().beginTransaction();
        like = view.findViewById(R.id.iv_like);
        option = view.findViewById(R.id.review_menu);
        commentCount = view.findViewById(R.id.comment_count);
        review_page_title = view.findViewById(R.id.review_page_title);
        review_page_nickname = view.findViewById(R.id.review_page_nickname);
        review_page_date = view.findViewById(R.id.review_page_date);
        review_page_content = view.findViewById(R.id.review_page_content);
//        review_page_advantage = view.findViewById(R.id.review_page_advantage);
//        review_page_weakness = view.findViewById(R.id.review_page_weakness);
        review_page_view_count = view.findViewById(R.id.review_page_view_count);
        review_page_view_count.setText(String.valueOf(board.getTotalView()+1));
        review_page_recommend = view.findViewById(R.id.review_page_recommend);
        review_page_recommend.setText(String.valueOf(board.getTotalRecommend()));
        review_page_scrap = view.findViewById(R.id.review_page_scrap);
        comment_submit = view.findViewById(R.id.review_read_comment_submit);
        comment = view.findViewById(R.id.review_read_edit_comment);
        comment_submit.setOnClickListener(onClickListener);
        review_page_title.setText(board.getTitle());
        review_read_page_list = view.findViewById(R.id.review_read_page_list);
        review_read_page_list.setOnClickListener(onClickListener);
// 이미지가 여러개 일수도 있으므로 원래는 반복문 연결 시켜야함!!!!!!!
        review_page_view_count.setText(String.valueOf(board.getTotalView()));
        review_page_recommend.setText(String.valueOf(board.getTotalRecommend()));
        review_page_scrap.setText(String.valueOf(board.getScrapCount()));
        wNickName = view.findViewById(R.id.review_page_nickname);
        wdate = view.findViewById(R.id.review_page_date);
        wdate.setText(board.getCreatedDate());
        wNickName.setText(wmember.getNickName());
        wtitle = view.findViewById(R.id.review_page_title);
        wtitle.setText(board.getTitle());
        wcontent = view.findViewById(R.id.review_page_content);
        wcontent.setText(board.getContentString());
        option.setOnClickListener(onClickListener);
        like.setOnClickListener(onClickListener);
        commentCount.setText(board.getTotalComment().toString());
        if(member==null){
            option.setEnabled(false);
        }else if(!member.getId().equals(board.getUserId())){
            option.setEnabled(false);
        }
        registerForContextMenu(option);
        i = 0;
        wImage = view.findViewById(R.id.w_profile_image);

        if (!wmember.getSumNailImage().equals("null")) {
            Glide.with(this).load(wmember.getSumNailImage()).into(wImage);
        }
        reviewSearchLikeHttp http = new reviewSearchLikeHttp();

        if(member!=null){
            System.out.println("이거 맞아???");
            http.setBodyContents(member.getId(),board.getId(),board.getCategoryId());
            checkedLike = http.send();
            if(!checkedLike.equals("null")){
                like.setChecked(true);
            }else{
//                like.setChecked(false);
            }
        }
        RecyclerView rcv = view.findViewById(R.id.rv_commentlist);
        setCommentList(start,rcv);
        return view;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle("작성자 메뉴");
        inflater.inflate(R.menu.review_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
         super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_review_delete :
//                Toast.makeText(getContext(),"삭제버튼",Toast.LENGTH_SHORT).show();
                show();
                return true;
            case R.id.menu_review_modify :
                    Fragment fragment  = new ReviewModifyFragment(member,board);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                return true;
        }
        return false;
    }





    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
//                case R.id.review_modify :
//                Fragment fragment = new ReviewModifyFragment(dto);
//                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
//                    break;
                case R.id.review_read_comment_submit :
                    if(member==null){
                        Toast.makeText(getContext(),"회원만 댓글을 작성 할 수 있습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        //댓글 작성할때 필요한것은 댓글 내용  , 해당 리뷰 uid ,  카테고리 이름 , 작성자 dto
                        CommentWriteHttp http = new CommentWriteHttp();
                        http.setBodyContents(member.getId(),board.getId(),comment.getText().toString(),member.getNickName());
                        if(http.send().equals("ok")){
                            Toast.makeText(getContext(),"댓글 작성이 완료되었습니다!",Toast.LENGTH_SHORT).show();
                            board.setTotalComment(board.getTotalComment()+1);
                            Fragment fragment = new ReviewReadPageFragment(member,board,wmember,start);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }
                     break;
                case R.id.review_read_page_list :
                    Fragment fragment1 = new fragment_category(member,board.getCategoryId());
                    getFragmentManager().beginTransaction().replace(R.id.main_frame,fragment1).commit();
                    break;
                case R.id.review_menu :
                    view.showContextMenu();
                    break;
                case R.id.iv_like :
                    if(member!=null) {
                        if (like.isChecked()==true) {
                            reviewLikeUphttp http = new reviewLikeUphttp();
                            http.setBodyContents(member.getId(), board.getId(),board.getCategoryId());
                            checkedLike = http.send();
                            if(!checkedLike.equals("error")){
                                like.setChecked(true);
                                Toast.makeText(getContext(),"좋아요",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            reviewDisLikeHttp http = new reviewDisLikeHttp();
                            http.setBodyContents(checkedLike,board.getId(),board.getCategoryId());
                            if(http.send().equals("ok")){
                                like.setChecked(false);
                            }else{
                                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"로그인 후 이용가능합니다",Toast.LENGTH_SHORT).show();
                        like.setChecked(false);
                    }
                    break;
            }
        }
    };

    public void setReviewImage(Board board){
System.out.println(board.getImage1());
        if(!board.getImage1().equals("null")){
            Fragment fragment = new ImageFrag1(board.getImage1(),getContext());
            viewPager.addItem(fragment);
        }
        if(!board.getImage2().equals("null")){
            Fragment fragment = new ImageFrag2(board.getImage2(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage3().equals("null")){
            Fragment fragment = new ImageFrag3(board.getImage3(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage4().equals("null")){
            Fragment fragment = new ImageFrag4(board.getImage4(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage5().equals("null")){
            Fragment fragment = new ImageFrag5(board.getImage5(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage6().equals("null")){
            Fragment fragment = new ImageFrag6(board.getImage6(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage7().equals("null")){
            Fragment fragment = new ImageFrag7(board.getImage7(),context);
            viewPager.addItem(fragment);
        }
        if(!board.getImage8().equals("null")){
            Fragment fragment = new ImageFrag8(board.getImage8(),context);
            viewPager.addItem(fragment);
        }
        viewPageradapter.setAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPageradapter);

    }

    void show()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 게시글을 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reviewDeleteHttp http = new reviewDeleteHttp();
                        http.setBodyContents(board.getId());
                        String result = http.send();
                        if(result.equals("OK")){
                            Toast.makeText(getContext(),"삭제가 완료되었습니다",Toast.LENGTH_SHORT).show();
                            if(list==null){
                                Fragment fragment = new fragment_category( member,board.getCategoryId());
                                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                            }else{
                                list.remove(position);
//                            Fragment fragment = new fragment_category( member,board.getCategoryId());
                                Fragment fragment = new fragment_category(board.getCategoryId(), member, list);
                                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame, fragment).commit();
                            }
                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
    public void setCommentList(int start,RecyclerView rcv) {
        CommentSetListHttp http = new CommentSetListHttp();
        http.setBodyContents(board.getId(), start);
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<Member> cmember = new ArrayList<>();
        comments = http.send();
        {
            for (int i = 0; i < comments.size(); i++) {
                // 댓글 작성자의 닉네임 , 프로필 사진 가저오면서 해당 작성자가 아직 회원인지 확인함
                // 회원 아닐 시 댓글은 안보여줌
                // 추후 삭제코드 작성 해야함!!!
            getWriterInfoHttp uhttp = new getWriterInfoHttp();
            uhttp.setBodyContents(comments.get(i).getUserId());
                Member temp = uhttp.send();
                if(temp==null){
                    comments.remove(i);
                }else{
                    cmember.add(temp);
                }
            }
            CommentAdapter adapter = new CommentAdapter(getContext(), fragmentTransaction, member, comments,cmember);

            // 여기서 list<Board> 형태로 주고 받는대 그냥 Board 형태로 주고 받으면 어떨지 고민해봐야..........
            adapter.setBoard(board);
            adapter.setPosition(position);
            adapter.setStart(start);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

}

