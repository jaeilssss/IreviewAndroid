package com.flink.ireview.ui.MyPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.find_password.fragment_find_password;
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
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MyPageViewModel myPageViewModel;
    int i ;
    private UsersDto usersDto =null;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private TextView my_nickname,my_review_number,my_comment_number , my_scrap_number,mypage_modify_page,mypage_modify_password_page,logout;
    private String nickname;
    View view;

    public MyPageFragment(UsersDto usersDto) {
        this.usersDto = usersDto;
    }
    public MyPageFragment( ) {
        usersDto=new UsersDto();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);


        setInfo();

        mypage_modify_page= view.findViewById(R.id.mypage_modify_page);
        mypage_modify_page.setOnClickListener(onClickListener);

//        mypage_modify_password_page = view.findViewById(R.id.mypage_modify_password_page);
//
//        mypage_modify_password_page.setOnClickListener(onClickListener);

        View view1 = view.findViewById(R.id.latest_review_page);


        logout = view.findViewById(R.id.mypage_logout_button);
        logout.setOnClickListener(onClickListener);

        final Fragment fragment = new fragment_latest_viewd();

        TextView textView2 = (TextView) view.findViewById(R.id.recommendated_review);
        View view2 = view.findViewById(R.id.recommendated_review_page);
        final Fragment fragment2 = new fragment_recommendated_review();
        textView2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view2) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment2).commit();

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
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment3).commit();

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
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment4).commit();

            }
        });

        return view;
}
//        if(usersDto.getUsers_nickname().equals(" ")){
//            UsersDao dao = new UsersDao(getContext(),getFragmentManager().beginTransaction());
//            dao.ReadInfo(user.getEmail());
//
//        }


//        switch (usersDto.getUsers_nickname()) {
//            case " ":
//                UsersDao dao = new UsersDao(getContext(), getFragmentManager().beginTransaction());
//                getFragmentManager().beginTransaction().addToBackStack(null).commit();
//                dao.ReadInfo(user.getEmail());
//                break;
//            default:
//
//                //각 텍스트필드별로 화면 페이지 전환
//                my_nickname = view.findViewById(R.id.my_nickname);
//                my_nickname.setText(usersDto.getUsers_nickname());
//                my_review_number = view.findViewById(R.id.my_review_number);
//                my_comment_number = view.findViewById(R.id.reply_number);
//                my_scrap_number = view.findViewById(R.id.scrab_number);
//                my_review_number.setText(String.valueOf(usersDto.getMy_review_list().size()));
//                my_scrap_number.setText(String.valueOf(usersDto.getMy_scrap_list().size()));
//                my_comment_number.setText(String.valueOf(usersDto.getMy_comment_list().size()));
//                mypage_modify_page= view.findViewById(R.id.mypage_modify_page);
//                mypage_modify_page.setOnClickListener(onClickListener);
//                TextView textView1 = (TextView) view.findViewById(R.id.latest_review);
//                mypage_modify_password_page = view.findViewById(R.id.mypage_modify_password_page);
//                mypage_modify_password_page.setOnClickListener(onClickListener);
//                View view1 = view.findViewById(R.id.latest_review_page);
//
//                final Fragment fragment = new fragment_latest_viewd();
//                textView1.setOnClickListener(new TextView.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        //stack에 저장해두면 back버튼을 누를때마다 이전으로 돌아갈 수 있따.
//                        //또한 그 내용들이 안에 저장되어 사라지지 않는다.
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment).commit();
//                    }
//                });
//
//                TextView textView2 = (TextView) view.findViewById(R.id.recommendated_review);
//                View view2 = view.findViewById(R.id.recommendated_review_page);
//                final Fragment fragment2 = new fragment_recommendated_review();
//                textView2.setOnClickListener(new TextView.OnClickListener() {
//                    @Override
//                    public void onClick(View view2) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment2).commit();
//
//                    }
//                });
//
//                TextView textView3 = (TextView) view.findViewById(R.id.mypage_option_button);
//                View view3 = view.findViewById(R.id.option_page);
//                final Fragment fragment3 = new fragment_option();
//                textView3.setOnClickListener(new TextView.OnClickListener() {
//                    @Override
//                    public void onClick(View view3) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment3).commit();
//
//                    }
//                });
//
//                TextView textView4 = (TextView) view.findViewById(R.id.mypage_service_center_button);
//                View view4 = view.findViewById(R.id.service_center_page);
//                final Fragment fragment4 = new fragment_service_center();
//                textView4.setOnClickListener(new TextView.OnClickListener() {
//                    @Override
//                    public void onClick(View view4) {
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.replace(R.id.nav_host_fragment, fragment4).commit();
//
//                    }
//                });
//
//
//        }
//        return view;
//    }

    public void setInfo(){
        db.collection("users").document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usersDto = new UsersDto();
                usersDto = documentSnapshot.toObject(UsersDto.class);
                my_nickname = view.findViewById(R.id.my_nickname);
                my_nickname.setText(usersDto.getUsers_nickname());
                my_review_number = view.findViewById(R.id.my_review_number);
                my_comment_number = view.findViewById(R.id.reply_number);
                my_scrap_number = view.findViewById(R.id.scrab_number);
                my_review_number.setText(String.valueOf(usersDto.getMy_review_list().size()));
                my_scrap_number.setText(String.valueOf(usersDto.getMy_scrap_list().size()));
                my_comment_number.setText(String.valueOf(usersDto.getMy_comment_list().size()));
            }

        });
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mypage_modify_page :
                    Fragment fragment = new MyPageModifyFragment(usersDto);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment).commit();
                    break;
//                case R.id.mypage_modify_password_page :
//                    Fragment fragment1= new fragment_find_password();
//                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment1).commit();
//                    break;
                case R.id.mypage_logout_button :
                    mauth.signOut();
                    Toast.makeText(getContext(),"로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                    Fragment fragment2 = new MainFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment2).commit();
                    break;

            }
        }
    };
}