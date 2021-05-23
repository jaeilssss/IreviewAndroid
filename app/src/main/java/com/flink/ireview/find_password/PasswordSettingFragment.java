package com.flink.ireview.find_password;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flink.ireview.R;
import com.flink.ireview.http.User.PasswordSettingHttp;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.ui.login.LoginFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordSettingFragment extends Fragment {
    private View view;
    private int control;
    private transmissionListener onMyListener;
    private String id;
    private Button confirm,cancel;
    private EditText password,password2;
    private String regExp_symbol = "([0-9].*[~,`,!,@,#,$,%,^,&,*,(,)])|([~,`,!,@,#,$,%,^,&,*,(,)].*[0-9])";
    private String regExp_alpha = "([a-z])";
    Pattern pattern_symbol = Pattern.compile(regExp_symbol);
    Pattern pattern_alpha = Pattern.compile(regExp_alpha);
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof transmissionListener){
            onMyListener = (transmissionListener)getActivity();
        }
    }

    public boolean spaceCheck(String spaceCheck){
        for(int i = 0 ; i < spaceCheck.length() ; i++) {
            if(spaceCheck.charAt(i) == ' ')
                return true;
        }
        return false;
    }
    public PasswordSettingFragment(String id) {
        this.id = id;
    }

    public PasswordSettingFragment() {
        // Required empty public constructor
    }


    public PasswordSettingFragment(int control, String id) {
        this.control = control;
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view= inflater.inflate(R.layout.fragment_password_setting, container, false);
        confirm = view.findViewById(R.id.password_setting_confirm);
        cancel = view.findViewById(R.id.password_setting_cancel);
        confirm.setOnClickListener(onClickListener);
        password = view.findViewById(R.id.password_setting_new_password);
        password2 = view.findViewById(R.id.password_setting_new_password2);
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.password_setting_confirm :
                    String insertPassword1 = password.getText().toString();
                    String insertPassword2 = password2.getText().toString();
                    Matcher matcher_symbol = pattern_symbol.matcher(insertPassword1);
                    Matcher matcher_alpha = pattern_alpha.matcher(insertPassword1);
                    if(!insertPassword1.equals(insertPassword2)){
                        Toast.makeText(getContext(),"가입 할 비밀번호 와 재입력 비밀번호가 다릅니다",Toast.LENGTH_SHORT).show();
                    }else{
                            if(insertPassword1.length()<7 || insertPassword2.length()>20) {
        Toast.makeText(getContext(), "7~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", Toast.LENGTH_SHORT).show();
    }else if((matcher_symbol.find() && matcher_alpha.find()) == false) {
        Toast.makeText(getContext(), "7~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", Toast.LENGTH_SHORT).show();
    }else if(spaceCheck(insertPassword1)){
        Toast.makeText(getContext(), "비밀번호에 공백을 허용하지 않습니다.", Toast.LENGTH_SHORT).show();
    }
                            else{
                                PasswordSettingHttp http = new PasswordSettingHttp();
                                http.setBodyContents(insertPassword1,id);
                                String result= http.send();
                                if(result.equals("ok")){
                                    Toast.makeText(getContext(),"설정이 완료되었습니다",Toast.LENGTH_SHORT).show();
                                    if(control==-1){
                                        onMyListener.onReceivedData(null);
                                    }
                                    Fragment fragment = new LoginFragment();
                                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                                }else{
                                    Toast.makeText(getContext(),"설정이 완료되지 못했습니다",Toast.LENGTH_SHORT).show();
                                }
                            }
                    }
                    break;
                case R.id.password_setting_cancel:
                    break;
            }
        }
    };
//

}
