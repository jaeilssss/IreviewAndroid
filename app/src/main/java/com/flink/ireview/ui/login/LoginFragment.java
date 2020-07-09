package com.flink.ireview.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.find_id.fragment_find_id;
import com.flink.ireview.find_password.fragment_find_password;
import com.flink.ireview.http.User.LoginHttp;
import com.flink.ireview.http.User.MyInfoHttp;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.sign_up.fragment_signup;
import com.flink.ireview.ui.Main.MainFragment;

public class LoginFragment extends Fragment {

    private com.flink.ireview.ui.login.LoginViewModel LoginViewModel;
    private Button login;
    private EditText account , password ;
    UsersDao dao ;
    private transmissionListener onMyListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() !=null && getActivity() instanceof transmissionListener){
            onMyListener = (transmissionListener)getActivity();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


       /* LoginViewModel =
                ViewModelProviders.of(this).get(com.flink.ireview.ui.login.LoginViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_login);
        /*LoginViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        login = root.findViewById(R.id.Button_Login);
        dao =  new UsersDao(getContext() , getFragmentManager().beginTransaction()); // dao 초기값 !!!!
        account = root.findViewById(R.id.textView_Login_id);
        password = root.findViewById(R.id.textView_Login_password);
        login.setOnClickListener(onClickListener);

        Button button1= (Button)root.findViewById(R.id.Button_find_id);
        View view1 = root.findViewById(R.id.find_id_page);
        final Fragment fragment1 = new fragment_find_id();
        button1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view1) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment1).commit();
            }
        });

        Button button2= (Button)root.findViewById(R.id.Button_find_password);
        View view2 = root.findViewById(R.id.find_password_page);
        final Fragment fragment2 = new fragment_find_password();
        button2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view2) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment2).commit();
            }
        });

        Button button3= (Button)root.findViewById(R.id.Button_signup);
        View view3 = root.findViewById(R.id.signup_page);
        final Fragment fragment3 = new fragment_signup();
        button3.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view3) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment3).commit();
            }
        });
        return root;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Button_Login :
                    if(account.getText().toString().length()>0 && password.getText().toString().length()>6){
//                        dao.login(email.getText().toString(),password.getText().toString());
                        LoginHttp http = new LoginHttp();
                        http.setBodyContents(account.getText().toString(),password.getText().toString());
                        String data = http.send();

                        if(data.equals("failure")){
                            Toast.makeText(getContext(),"존재하지 않는 계정 , 비밀번호 입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            MyInfoHttp myInfoHttp = new MyInfoHttp();
                            myInfoHttp.setBodyContents(account.getText().toString());
                            Member member = myInfoHttp.send();
                            if(member==null){
                                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                            }else{
//                                data = "환경합니다 "+member.getNickName() +"님";
                                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                                onMyListener.onReceivedData(member);
                                Fragment fragment = new MainFragment();
                                getFragmentManager().beginTransaction().addToBackStack(null)
                                        .replace(R.id.main_frame,fragment).commit();
                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

}