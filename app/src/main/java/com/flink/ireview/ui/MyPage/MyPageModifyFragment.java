package com.flink.ireview.ui.MyPage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.http.User.MyInfoModifyHttp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageModifyFragment extends Fragment {

    View view;
    EditText modify_name , modify_nickname , modify_phone , check_password;
    Button modify , exit ;
    UsersDto current_dto ;
    Member member;

    public MyPageModifyFragment(Member member) {
        this.member = member;
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public MyPageModifyFragment() {
        // Required empty public constructor
    }
    public MyPageModifyFragment(UsersDto current_dto){
        this.current_dto = current_dto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage_modify, container, false);
        modify_name = view.findViewById(R.id.mypage_modify_my_name);
        modify_name.setText(member.getName());
        modify_nickname = view.findViewById(R.id.mypage_modify_my_nickname);
        modify_nickname.setText(member.getNickName());
        modify_phone = view.findViewById(R.id.mypage_modify_my_phonenumber);
        modify_phone.setText(member.getPhoneNumber());
        modify = view.findViewById(R.id.mypage_modify_button);
        modify.setOnClickListener(onClickListener);
        exit = view.findViewById(R.id.mypage_modify_exit);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mypage_modify_button :
                    String name = modify_name.getText().toString();
//                    String password = check_password.getText().toString();
                    String nickname = modify_nickname.getText().toString();
                    String phone = modify_phone.getText().toString();
                    MyInfoModifyHttp http = new MyInfoModifyHttp();
                    http.setBodyContents(member.getId(),member.getAccount(),member.getPassword(),member.getEmail(),name,nickname,phone
                            ,"11022-222-33",member.getYear(),"11",member.getDate(),member.getGender(),String.valueOf(member.getInterest1()),String.valueOf(member.getInterest2())
                            ,String.valueOf(member.getInterest3()),String.valueOf(member.getInterest4()),String.valueOf(member.getInterest5()));
                    Member newMember = http.send();
                    if(newMember!=null){
                        Toast.makeText(getContext(),"회원정보 수정이 완료 되었습니다",Toast.LENGTH_SHORT).show();
                        Fragment fragment = new MyPageFragment(newMember);
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                    }
                    break;
                case R.id.mypage_modify_exit :
                    Fragment fragment = new MyPageFragment(member);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                    break;
            }
        }
    };

}
