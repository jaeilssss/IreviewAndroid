package com.flink.ireview.find_password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.R;
import com.flink.ireview.http.User.FindPasswordHttp;

public class fragment_find_password extends Fragment {

    private FragmentFindPasswordViewModel mViewModel;
    private EditText find_password_textView_Email,find_password_textView_id,find_password_textView_name;
    private Button find_id_find_button, find_id_cancle_button;
    private FragmentTransaction fragmentTransaction;

    public fragment_find_password(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }

    public static fragment_find_password newInstance(FragmentTransaction fragmentTransaction) {
        return new fragment_find_password(fragmentTransaction);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_password, container, false);

        find_password_textView_Email = view.findViewById(R.id.find_password_textView_Email);
        find_password_textView_id = view.findViewById(R.id.find_password_textView_id);
        find_password_textView_name = view.findViewById(R.id.find_password_textView_name);
        find_id_cancle_button = view.findViewById(R.id.find_id_cancle_button);
        find_id_find_button = view.findViewById(R.id.find_id_find_button);
        find_id_find_button.setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentFindPasswordViewModel.class);
        // TODO: Use the ViewModel
    }
    View.OnClickListener onClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (v.getId()){
                    case R.id.find_id_find_button :
                    if(find_password_textView_name.getText().toString().length()==0 || find_password_textView_id.getText().toString().length()==0
                        || find_password_textView_Email.getText().toString().length()==0){
                        Toast.makeText(getContext(),"정보를 모두 입력해주세요",Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        FindPasswordHttp http = new FindPasswordHttp();
                        String account = find_password_textView_id.getText().toString();
                        String name = find_password_textView_name.getText().toString();
                        String email = find_password_textView_Email.getText().toString();
                        http.setBodyContents(account,name,email);
                        String result = http.send();
                        if(result.equals("ERROR")){
                            Toast.makeText(getContext(),"가입된 회원 정보가 없습니다",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            Fragment fragment = new PasswordSettingFragment(result);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }
                        break;
                }
        }
    };


}
