package com.flink.ireview.ui.MyPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.find_password.fragment_find_password;
import com.flink.ireview.http.Comment.getCountMyCommentHttp;
import com.flink.ireview.http.User.LogOutHttp;
import com.flink.ireview.http.board.getCountMyReview;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.ui.LatestViewd.fragment_latest_viewd;
import com.flink.ireview.ui.Main.MainFragment;
import com.flink.ireview.ui.Option.fragment_option;
import com.flink.ireview.ui.recommendated_review.fragment_recommendated_review;
import com.flink.ireview.ui.service_center.fragment_service_center;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageFragment extends Fragment {
    private transmissionListener onMyListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MyPageViewModel myPageViewModel;
    int i ;
    private UsersDto usersDto =null;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private TextView my_nickname,my_review_number,my_comment_number , my_scrap_number,mypage_modify_password_page,logout;
    ImageButton mypage_modify_page;
    ImageView myImage;
    private TextView passwordModify , myReviewNumber,myReplyNumber;
    private String nickname;
    View view;
    private Member member;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof transmissionListener){
            onMyListener = (transmissionListener)getActivity();
        }
    }

    public MyPageFragment(Member member) {
        this.member = member;
    }

    public MyPageFragment(UsersDto usersDto) {
        this.usersDto = usersDto;
    }
    public MyPageFragment( ) {
        usersDto=new UsersDto();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);
        int start = 0;
        setmyCount(view);
        passwordModify = view.findViewById(R.id.mypage_modify_password_page);
        passwordModify.setOnClickListener(onClickListener);
        myImage = view.findViewById(R.id.my_image);
        mypage_modify_page= view.findViewById(R.id.mypage_modify_page);
        mypage_modify_page.setOnClickListener(onClickListener);
        my_nickname = view.findViewById(R.id.my_nickname);
        my_nickname.setText(member.getNickName());
        mypage_modify_password_page = view.findViewById(R.id.mypage_modify_password_page);

        mypage_modify_password_page.setOnClickListener(onClickListener);
        myReviewNumber = view.findViewById(R.id.my_review_number);

        View view1 = view.findViewById(R.id.latest_review_page);

        if(!member.getSumNailImage().equals("null")){
            Glide.with(getContext()).load(member.getSumNailImage()).into(myImage);
        }
        logout = view.findViewById(R.id.mypage_logout_button);
        logout.setOnClickListener(onClickListener);

        final Fragment fragment = new fragment_latest_viewd();

        TextView textView2 = (TextView) view.findViewById(R.id.recommendated_review);
        View view2 = view.findViewById(R.id.recommendated_review_page);
        final Fragment fragment2 = new fragment_recommendated_review(member.getId(),member);
        textView2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view2) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment2).commit();

            }
        });
        TextView textView3 = (TextView) view.findViewById(R.id.mypage_option_button);
        View view3 = view.findViewById(R.id.option_page);
        final Fragment fragment3 = new fragment_option();
        textView3.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view3) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment3).commit();

            }
        });

        TextView textView4 = (TextView) view.findViewById(R.id.mypage_service_center_button);
        View view4 = view.findViewById(R.id.service_center_page);
        final Fragment fragment4 = new fragment_service_center();
        textView4.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view4) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment4).commit();

            }
        });
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mypage_modify_page :
                    Fragment fragment = new MyPageModifyFragment(member);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                    break;
                case R.id.mypage_logout_button :
                    LogOutHttp logOutHttp = new LogOutHttp();
                    String result = logOutHttp.send();
                    if(result.equals("성공")){
                        Toast.makeText(getContext(),"로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                        onMyListener.onReceivedData(null);
                        Fragment fragment2 = new MainFragment(null);
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment2).commit();
                    }else{
                        Toast.makeText(getContext(),"로그아웃을 실패했습니다",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.mypage_modify_password_page :
                    Fragment fragment1 = new PasswordModifyFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.main_frame,fragment1).commit();
                    break;
            }
        }
    };
    public void setmyCount(View view){
        myReviewNumber = view.findViewById(R.id.my_review_number);
        getCountMyReview http = new getCountMyReview();
        http.setBodyContents(member.getId());
        myReviewNumber.setText(String.valueOf(http.send()));
        myReplyNumber = view.findViewById(R.id.my_reply_number);
        getCountMyCommentHttp http2 = new getCountMyCommentHttp();
        http2.setBodyContents(member.getId());
        myReplyNumber.setText(String.valueOf(http2.send()));

    }
}