package com.flink.ireview.ui.MyPage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
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
        modify_name.setText(current_dto.getUsers_name());
        modify_nickname = view.findViewById(R.id.mypage_modify_my_nickname);
        modify_nickname.setText(current_dto.getUsers_nickname());
        modify_phone = view.findViewById(R.id.mypage_modify_my_phonenumber);
        modify_phone.setText(current_dto.getUsers_phone_number());
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
                    String password = check_password.getText().toString();
                    String nickname = modify_nickname.getText().toString();
                    String phone = modify_phone.getText().toString();
                    UsersDto dto = new UsersDto(user.getUid(),user.getEmail(),name,phone,current_dto.getUsers_reliability(),nickname,current_dto.getMy_comment_list(),
                            current_dto.getMy_review_list(),current_dto.getMy_scrap_list(),current_dto.getMy_recommend_list());
                    UsersDao dao = new UsersDao(getContext(),getFragmentManager().beginTransaction());
                    dao.modifyMyInfo(dto);
                    break;
                case R.id.mypage_modify_exit :
                    Fragment fragment = new MyPageFragment(current_dto);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment).commit();
                    break;
            }
        }
    };

}
