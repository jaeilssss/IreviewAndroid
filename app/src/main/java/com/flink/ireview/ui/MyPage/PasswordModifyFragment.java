package com.flink.ireview.ui.MyPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flink.ireview.R;
import com.flink.ireview.find_password.PasswordSettingFragment;
import com.flink.ireview.http.User.FindPasswordHttp;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordModifyFragment extends Fragment {

    private View view ;

    private EditText name, id,email;

    private Button confirm , cancel;

    public PasswordModifyFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_password_modify, container, false);
        name = view.findViewById(R.id.modify_password_textView_name);
        id = view.findViewById(R.id.modify_password_textView_id);
        email = view.findViewById(R.id.modify_password_textView_Email);
        confirm = view.findViewById(R.id.modify_password_find_button);
        cancel = view.findViewById(R.id.modify_password_cancle_button);
        confirm.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.modify_password_find_button :
                    if(name.getText().toString().length()==0 || id.getText().toString().length()==0
                            || email.getText().toString().length()==0){
                        Toast.makeText(getContext(),"정보를 모두 입력해주세요",Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        FindPasswordHttp http = new FindPasswordHttp();
                        String account = id.getText().toString();
                        String mname = name.getText().toString();
                        String memail = email.getText().toString();
                        http.setBodyContents(account,mname,memail);
                        String result = http.send();
                        if(result.equals("ERROR")){
                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            Fragment fragment = new PasswordSettingFragment(-1,result);
                            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }
                    break;
            }
        }
    };
}
