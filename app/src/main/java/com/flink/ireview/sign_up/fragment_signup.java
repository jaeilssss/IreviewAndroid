package com.flink.ireview.sign_up;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.http.User.checkEmailHttp;
import com.flink.ireview.http.User.checkInfoHttp;
import com.flink.ireview.http.User.checkNickNameHttp;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fragment_signup extends Fragment {

    private Button signup , exit , checkNickname ,checkId,checkEmail;
    private boolean checknick=false , checkid=false , checkemail= false;
    private CheckBox agree_all, agree_1, agree_2;
    private String currentId,currentNick,currentEmail;
    private String signup_email,signup_nickname,signup_id;
    private FragmentSignupViewModel mViewModel;
    private EditText email , password , name , nickname , phone , repassword , year , date, month ,account;
    View view;
    Spinner Spinner_singup_month;
    Spinner Spinner_singup_gender;

    private String regExp_symbol = "([0-9].*[~,`,!,@,#,$,%,^,&,*,(,)])|([~,`,!,@,#,$,%,^,&,*,(,)].*[0-9])";
    private String regExp_alpha = "([a-z])";

    Pattern pattern_symbol = Pattern.compile(regExp_symbol);
    Pattern pattern_alpha = Pattern.compile(regExp_alpha);

    public boolean spaceCheck(String spaceCheck){
        for(int i = 0 ; i < spaceCheck.length() ; i++) {
            if(spaceCheck.charAt(i) == ' ')
                return true;
        }
        return false;
    }


    Date time = new Date();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
    SimpleDateFormat format2 = new SimpleDateFormat("MM");
    SimpleDateFormat format3 = new SimpleDateFormat("dd");
    // 현재 년월일 을 저장하고 있음 !!!!!!
    String current_year = format1.format(time);
    String current_month = format2.format(time);
    String current_date = format3.format(time);
    ///////////////////////////
    public static fragment_signup newInstance() {
        return new fragment_signup();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        checkemail=false;
        checkid = false;
        checknick=false;

        view= inflater.inflate(R.layout.fragment_signup, container, false);
        signup = view.findViewById(R.id.Signup_signup_button);
        signup.setOnClickListener(onClickListener);
        Spinner_singup_month = (Spinner)view.findViewById(R.id.Spinner_signup_month);
        String singup_month = Spinner_singup_month.getSelectedItem().toString();
        checkNickname = view.findViewById(R.id.signup_check_nickname);
        checkNickname.setOnClickListener(onClickListener);
        nickname = view.findViewById(R.id.Signup_textView_nickname);
        account =view.findViewById(R.id.Signup_textView_id);
        email = view.findViewById(R.id.Signup_textView_user_email);
        checkId =view.findViewById(R.id.signup_check_id);
        checkId.setOnClickListener(onClickListener);
        checkEmail = view.findViewById(R.id.signup_check_email);
        checkEmail.setOnClickListener(onClickListener);
        Spinner_singup_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner_singup_gender = (Spinner)view.findViewById(R.id.Spinner_singup_gender);

        Spinner_singup_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        agree_all = (CheckBox) view.findViewById(R.id.Signup_CheckBox_agree_all);
        agree_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agree_all.isChecked()) {
                    agree_1.setChecked(true);
                    agree_2.setChecked(true);
                }
                else
                {
                    agree_1.setChecked(false);
                    agree_2.setChecked(false);
                }
            }
        });
        agree_1 = (CheckBox) view.findViewById(R.id.Signup_CheckBox_agree_1);
        agree_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agree_1.isChecked() == true && agree_2.isChecked() == true){
                    agree_all.setChecked(true);
                }else if(agree_1.isChecked() == false || agree_2.isChecked() == false){
                    agree_all.setChecked(false);
                }

            }
        });

        agree_2 = (CheckBox) view.findViewById(R.id.Signup_CheckBox_agree_2);
        agree_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(agree_1.isChecked() == true && agree_2.isChecked() == true){
                    agree_all.setChecked(true);
                }else if(agree_1.isChecked() == false || agree_2.isChecked() == false){
                    agree_all.setChecked(false);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentSignupViewModel.class);
        // TODO: Use the ViewModel


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Signup_signup_button :
                signup_id = account.getText().toString();
                 signup_email = email.getText().toString();
                    password = view.findViewById(R.id.Signup_textView_password);
                    String signup_password = password.getText().toString();
                    repassword = view.findViewById(R.id.Signup_textView_repassword);
                    String signup_repassword = repassword.getText().toString();
                    name = view.findViewById(R.id.Signup_textView_name);
                    String signup_name = name.getText().toString();

                     signup_nickname = nickname.getText().toString();
                    phone = view.findViewById(R.id.Signup_textView_phone_number);
                    String signup_phone = phone.getText().toString();
                    year = view.findViewById(R.id.signup_year);
                    String signup_year = year.getText().toString();
                    date = view.findViewById(R.id.signup_date);
                    String signup_date = date.getText().toString();
                    currentId  = account.getText().toString();

                    String signup_month = Spinner_singup_month.getSelectedItem().toString();
                    System.out.println(signup_month);
                    String gender = Spinner_singup_gender.getSelectedItem().toString();

                    Matcher matcher_symbol = pattern_symbol.matcher(signup_password);
                    Matcher matcher_alpha = pattern_alpha.matcher(signup_password);






                    if(signup_password.equals(signup_repassword)){
                        if(signup_email.length()==0){
                            Toast.makeText(getContext(),"이메일을 입력해주세요", Toast.LENGTH_SHORT);
                        }else if(signup_password.length()<6){
                            Toast.makeText(getContext(),"비밀번호 7자리 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_name.length()==0){
                            Toast.makeText(getContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_nickname.length()==0){
                            Toast.makeText(getContext(),"닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_phone.length()==0) {
                            Toast.makeText(getContext(), "연락처를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_password.length()<7 || signup_password.length()>20) {
                            Toast.makeText(getContext(), "7~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", Toast.LENGTH_SHORT).show();
                        }else if((matcher_symbol.find() && matcher_alpha.find()) == false) {
                            Toast.makeText(getContext(), "7~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", Toast.LENGTH_SHORT).show();
                        }else if(spaceCheck(signup_password)){
                            Toast.makeText(getContext(), "비밀번호에 공백을 허용하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }else if(signup_year.length()!=4){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요(년도는 4자리로 입력하시길 바랍니다)", Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(signup_year)<1900 || Integer.parseInt(signup_year)>Integer.parseInt(current_year)){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(signup_date) < 1 || Integer.parseInt(signup_date)> 31){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_month.equals("월")){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if((signup_month.equals("월") || Integer.parseInt(signup_month)==4 || Integer.parseInt(signup_month)==6 || Integer.parseInt(signup_month)==9 || Integer.parseInt(signup_month)==11) && Integer.parseInt(signup_date)==31){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(signup_month)==2){
                            boolean feb_date_check = (Integer.parseInt(signup_year) % 4 == 0 && (Integer.parseInt(signup_year) % 100 != 0 || Integer.parseInt(signup_year) % 400 == 0));

                            if(Integer.parseInt(signup_date) > 29 || (Integer.parseInt(signup_date)==29 && !feb_date_check)){
                                Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                            }
                        }else if((Integer.parseInt(signup_year)==Integer.parseInt(current_year)) && (Integer.parseInt(signup_month) > Integer.parseInt(current_month))){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if((Integer.parseInt(signup_year)==Integer.parseInt(current_year)) && (Integer.parseInt(signup_month) == Integer.parseInt(current_month)) && (Integer.parseInt(signup_date) > Integer.parseInt(current_date))){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if(signup_date.length()==0){
                            Toast.makeText(getContext(), "생년월일을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }else if(agree_1.isChecked() == false || agree_2.isChecked() == false){
                            Toast.makeText(getContext(), "이용약관과 개인정보 수집 및 이용에 대한 안내 모두 동의해주세요", Toast.LENGTH_SHORT).show();
                        }else if(checkid==false || !signup_id.equals(currentId)){
                            Toast.makeText(getContext(),"아이디 중복확인이 필요합니다",Toast.LENGTH_SHORT).show();
                        }else if(checknick==false|| !signup_nickname.equals(currentNick)){
                            Toast.makeText(getContext(),"닉네임 중복확인이 필요합니다",Toast.LENGTH_SHORT).show();
                        }else if(checkemail==false || !signup_email.equals(currentEmail)){
                            Toast.makeText(getContext(),"이메일 중복확인이 필요합니다",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            System.out.println(checkemail);
                            Member usersDto = new Member(currentId,signup_password,signup_email,signup_name,signup_nickname,
                                    signup_phone,signup_year,signup_month,signup_date,gender,"user","null");
                            Fragment fragment = new fragment_signup_category(usersDto);
                            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }else{
                        Toast.makeText(getContext(),"비밀번호를 동일하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.signup_check_nickname :

                    currentNick = nickname.getText().toString();
                    checkNickNameHttp http = new checkNickNameHttp();
                    http.setBodyContents(currentNick);
                    if(http.send().equals("일치")){
                        Toast.makeText(getContext(),"이미 존재하는 닉네임입니다",Toast.LENGTH_SHORT).show();
                        checknick=false;
                    }else{
                        Toast.makeText(getContext(),"사용 가능 닉네임입니다",Toast.LENGTH_SHORT).show();
                        checknick = true;
                    }
                    break;
                case R.id.signup_check_id :
                    currentId = account.getText().toString();
                    checkInfoHttp http1 = new checkInfoHttp();
                    http1.setBodyContents(currentId,"null");
                    if(http1.send().equals("일치")){
                        Toast.makeText(getContext(),"이미 존재하는 아이디입니다",Toast.LENGTH_SHORT).show();
                        checkid=false;
                    }else{
                        Toast.makeText(getContext(),"사용 가능한 아이디입니다",Toast.LENGTH_SHORT).show();
                        checkid = true;
                    }
                    break;
                case R.id.signup_check_email :
                    currentEmail = email.getText().toString();
                    checkEmailHttp http2 = new checkEmailHttp();
                    http2.setBodyContents(currentEmail);
                    if(http2.send().equals("일치")){
                        Toast.makeText(getContext(),"이미 존재하는 이메일입니다",Toast.LENGTH_SHORT).show();
                        checkemail=false;
                    }else{
                        Toast.makeText(getContext(),"사용 가능한 이메일 입니다",Toast.LENGTH_SHORT).show();
                        checkemail=true;
                    }
                    break;

            }
        }
    };
}
