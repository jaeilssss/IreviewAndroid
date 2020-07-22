package com.flink.ireview.ui.review;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.CommentDao;
import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.CommentRecyclerView.CommentAdapter;
import com.flink.ireview.classfile.Review;
import com.flink.ireview.ui.Category.fragment_category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewReadPageFragment extends Fragment {
    private Member member;
    View view;
    ReviewDto dto;
    TextView review_page_title , review_page_nickname ,review_page_date, review_page_view_count , review_page_recommend , review_page_scrap ,review_page_content;
    ImageView review_read_image;
    Button modify , delete,comment_submit;
    ReviewDao dao;
    UsersDto udto;
    TextView comment, review_page_advantage, review_page_weakness;
    ImageButton review_read_page_list;
    ArrayList<CommentDto> list;
    int i;
    FirebaseUser user = null;
    FirebaseFirestore db ;
    CheckBox checkBox;
    FragmentTransaction fragmentTransaction;
    public ReviewReadPageFragment(ReviewDto dto, UsersDto udto, ArrayList<CommentDto> list) {
        this.dto = dto;
        this.udto = udto;
        this.list = list;
    }

    public ReviewReadPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_review_read_page, container, false);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            user = FirebaseAuth.getInstance().getCurrentUser();
            setMyInfo();
        }

        fragmentTransaction = getFragmentManager().beginTransaction();

        modify = view.findViewById(R.id.review_modify);
        delete = view.findViewById(R.id.review_delete);
        review_page_title = view.findViewById(R.id.review_page_title);
        review_page_nickname = view.findViewById(R.id.review_page_nickname);
        review_page_date = view.findViewById(R.id.review_page_date);
        review_read_image = view.findViewById(R.id.review_read_image);
        review_page_content = view.findViewById(R.id.review_page_content);
        review_page_advantage = view.findViewById(R.id.review_page_advantage);
        review_page_weakness = view.findViewById(R.id.review_page_weakness);
        review_page_view_count = view.findViewById(R.id.review_page_view_count);
        review_page_recommend = view.findViewById(R.id.review_page_recommend);
        review_page_scrap = view.findViewById(R.id.review_page_scrap);
        comment_submit = view.findViewById(R.id.review_read_comment_submit);
        comment = view.findViewById(R.id.review_read_edit_comment);
        comment_submit.setOnClickListener(onClickListener);

        review_read_page_list = view.findViewById(R.id.review_read_page_list);
        review_read_page_list.setOnClickListener(onClickListener);
// 이미지가 여러개 일수도 있으므로 원래는 반복문 연결 시켜야함!!!!!!!
        review_page_nickname.setText(dto.getReviewer_name());
        review_page_view_count.setText(String.valueOf(dto.getReview_view_number()));
        review_page_recommend.setText(String.valueOf(dto.getRecommend_list().size()));
        review_page_scrap.setText(String.valueOf(dto.getReview_scrap_count()));
        review_page_advantage.setText(dto.getReview_main_advantage().get(0));
        review_page_weakness.setText(dto.getReview_main_weakness().get(0));
        Glide
                .with(this)
                .load(dto.getReview_main_image().get(0))
                .into(review_read_image);
        review_page_title.setText(dto.getReview_main_title());
        review_page_nickname.setText(dto.getReviewer_nickname());
        review_page_date.setText(dto.getReview_create_time());
        review_page_content.setText(dto.getReview_main_string());

        RecyclerView rcv = (RecyclerView)view.findViewById(R.id.rv_commentlist);
        CommentAdapter adapter = new CommentAdapter(getContext(),getFragmentManager().beginTransaction(),list,dto, udto,"test");
        rcv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        rcv.setAdapter(adapter);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null &&
                FirebaseAuth.getInstance().getCurrentUser().getUid().equals(dto.getReviewer_uid())){
            delete.setVisibility(View.VISIBLE);
            modify.setVisibility(View.VISIBLE);
        }
        modify.setOnClickListener(onClickListener);
        delete.setOnClickListener(onClickListener);
        checkBox = view.findViewById(R.id.iv_like);

        //체크박스 상태 확인
        //기본 상태는 트루로 시작한다.눌렀을때 false로 바뀜.


        i = 0;
        System.out.println(i);
        System.out.println("여기 왜 들어와 ㅡㅡ ");
        while (true) {

            if (user != null && udto.getMy_recommend_list().size() > 0 && udto.getMy_recommend_list().size()>i) {
                if (udto.getMy_recommend_list().get(i).get("test").equals(dto.getReviewer_uid()) && dto.getRecommend_list().size() > i) {
                    checkBox.setChecked(true);
                    break;
                } else {
                    i++;
                }
            }else{
                break;
            }

        }
//        if (((checkBox)).isChecked()) {
//            Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
//        }

//                if (((CheckBox)v).isChecked()) {
//                    Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }

        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기서 서버에다 count++로 바꿔주기
                //상태에 따라 카운트시켜주는 것이다.
                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    if (checkBox.isChecked()) {
                        UsersDto current = udto;
                        dao = new ReviewDao(getContext(), fragmentTransaction);
                        dto.getRecommend_list().add(user.getUid());
                        dao.setCategory("test");
                        dao.setClick(checkBox.isChecked());
                        dao.setDto(dto);
                        dao.setUdto(udto);
                        dao.recommendListUpdate();
                        i++;
                        setMyInfo();
                    } else {
                        Toast.makeText(getContext(),"좋아요 취소",Toast.LENGTH_SHORT).show();
                        dao = new ReviewDao(getContext(), fragmentTransaction);
                        dto.getRecommend_list().remove(i);
                        dao = new ReviewDao(getContext(), fragmentTransaction);
                        dto.getRecommend_list().add(user.getUid());
                        dao.setCategory("test");
                        dao.setClick(checkBox.isChecked());
                        dao.setDto(dto);
                        dao.setUdto(udto);
                        dao.setI(i);
                        dao.recommendListUpdate();
                        setMyInfo();
                    }
                }else{
                    Toast.makeText(getContext(),"회원만 좋아요를 누를 수 있습니다",Toast.LENGTH_SHORT).show();
                }
//                if (((CheckBox)v).isChecked()) {
//                    Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
            }
        }) ;
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.review_modify :
                    Fragment fragment = new ReviewModifyFragment(dto);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment).commit();
                    break;
                case R.id.review_delete :
                    dao = new ReviewDao(getContext(),getFragmentManager().beginTransaction());
                    dao.delete(dto);
                    break;
                case R.id.review_read_comment_submit :
                    if(FirebaseAuth.getInstance().getCurrentUser()==null){
                        Toast.makeText(getContext(),"회원만 댓글을 작성 할 수 있습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        //댓글 작성할때 필요한것은 댓글 내용  , 해당 리뷰 uid ,  카테고리 이름 , 작성자 dto
                        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                        Date time = new Date();
                        String date = format.format(time);
                        CommentDto cdto = new CommentDto(dto.getReview_UID(), udto.getUsers_nickname(), comment.getText().toString(), new ArrayList<String>(), date);
                        CommentDao cdao = new CommentDao(getContext(), getFragmentManager().beginTransaction());
                        cdao.write(cdto, dto, udto);
                    }
                    break;
                case R.id.review_read_page_list :
                    Fragment fragment1 = new fragment_category(member);
                    getFragmentManager().beginTransaction().replace(R.id.main_frame,fragment1).commit();
                    break;

            }
        }
    };

    public void setMyInfo(){
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                udto = documentSnapshot.toObject(UsersDto.class);
                System.out.println("셋 마이 인포@@@@@");
//                dao = new ReviewDao(getContext(), fragmentTransaction);
////                    dto.getRecommend_list().add(user.getUid());
////                    dao.recommendListUpdate(dto, "test", udto, checkBox.isChecked(), i);
            }
        }) ;
    }

}