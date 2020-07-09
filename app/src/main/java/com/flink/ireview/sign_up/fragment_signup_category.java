package com.flink.ireview.sign_up;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.http.User.SignUpHttp;
import com.flink.ireview.ui.login.LoginFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_signup_category extends Fragment {

View view;
private Member udto ;
private String email;
private String password;

    public fragment_signup_category(Member udto) {
        this.udto = udto;
    }

    int count;
ArrayList<Integer> check;
ArrayList<Integer> interests;
CheckBox checkBox[];

    public fragment_signup_category( String email, String password) {

        this.email = email;
        this.password = password;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_signup_category, container, false);

        checkBox = new CheckBox[21]; // 일단 여섯개로 해놓고 나중에 수정할것 !!!!!!
        setCheckBox();

        checkBox[0].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[1].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[2].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[3].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[4].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[5].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[6].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[7].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[8].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[9].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[10].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[11].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[12].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[13].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[14].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[15].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[16].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[17].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[18].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[19].setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox[20].setOnCheckedChangeListener(onCheckedChangeListener);






        Button category_finish = (Button)view.findViewById(R.id.Signup_category_finish);
        category_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count != 5){
                    Toast.makeText(getContext(), "5개를 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    checkCategory();
                    SignUpHttp http = new SignUpHttp();
                    http.setBodyContents(udto.getAccount(),udto.getPassword(),udto.getEmail(),udto.getName(),udto.getNickName(),udto.getPhoneNumber()
                    ,"11022-222-33",udto.getYear(),"11",udto.getDate(),udto.getGender(),String.valueOf(interests.get(0)),String.valueOf(interests.get(1))
                    ,String.valueOf(interests.get(2)),String.valueOf(interests.get(3)),String.valueOf(interests.get(4)));
                    if(http.send().equals("success")){
                        Toast.makeText(getContext(), "가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new LoginFragment();
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                    }else{
                        Toast.makeText(getContext(),"가입을 실패 했습니다error",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        return view;
    }

    public void setCheckBox(){
        checkBox[0] = view.findViewById(R.id.check_fashion);
        checkBox[1] = view.findViewById(R.id.check_cosmetic);
        checkBox[2] = view.findViewById(R.id.check_life);
        checkBox[3] = view.findViewById(R.id.check_interior);
        checkBox[4] = view.findViewById(R.id.check_appliance);
        checkBox[5] = view.findViewById(R.id.check_it);
        checkBox[6] = view.findViewById(R.id.check_car);
        checkBox[7] = view.findViewById(R.id.check_leisure);
        checkBox[8] = view.findViewById(R.id.check_travel);
        checkBox[9] = view.findViewById(R.id.check_medical);
        checkBox[10] = view.findViewById(R.id.check_culture);
        checkBox[11] = view.findViewById(R.id.check_education);
        checkBox[12] = view.findViewById(R.id.check_book);
        checkBox[13] = view.findViewById(R.id.check_baby_products);
        checkBox[14] = view.findViewById(R.id.check_mate_products);
        checkBox[15] = view.findViewById(R.id.check_beauty);
        checkBox[16] = view.findViewById(R.id.check_instrument);
        checkBox[17] = view.findViewById(R.id.check_autobicycle);
        checkBox[18] = view.findViewById(R.id.check_restaurant);
        checkBox[19] = view.findViewById(R.id.check_food);
        checkBox[20] = view.findViewById(R.id.check_toy);


        check=new ArrayList<>();
        for(int i =0 ; i<30 ; i++){
            // 일단 30개로 해놓음!!!

            check.add(0);
        }
        count=0;
    }
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.check_fashion :

                    if(check.get(0)==0){
                        check.set(0,1);
                        count++;
                }else{

                        check.set(0,0);
                        count--;
                    }
                    break;
                case R.id.check_cosmetic :

                    if(check.get(1)==0){
                        check.set(1,1);
                        count++;
                    }else{

                        check.set(1,0);
                        count--;
                    }
                    break;
                case R.id.check_life :

                    if(check.get(2)==0){
                        check.set(2,1);
                        count++;
                    }else{

                        check.set(2,0);
                        count--;
                    }
                    break;
                case R.id.check_interior :

                    if(check.get(3)==0){
                        check.set(3,1);
                        count++;
                    }else{

                        check.set(3,0);
                        count--;
                    }
                    break;
                case R.id.check_appliance :

                    if(check.get(4)==0){
                        check.set(4,1);
                        count++;
                    }else{

                        check.set(4,0);
                        count--;
                    }
                    break;
                case R.id.check_it :

                    if(check.get(5)==0){
                        check.set(5,1);
                        count++;
                    }else{

                        check.set(5,0);
                        count--;
                    }
                    break;
                case R.id.check_car :

                    if(check.get(6)==0){
                        check.set(6,1);
                        count++;
                    }else{

                        check.set(6,0);
                        count--;
                    }
                    break;
                case R.id.check_leisure :

                    if(check.get(7)==0){
                        check.set(7,1);
                        count++;
                    }else{

                        check.set(7,0);
                        count--;
                    }
                    break;
                case R.id.check_travel :

                    if(check.get(8)==0){
                        check.set(8,1);
                        count++;
                    }else{

                        check.set(8,0);
                        count--;
                    }
                    break;case R.id.check_medical :

                    if(check.get(9)==0){
                        check.set(9,1);
                        count++;
                    }else{

                        check.set(9,0);
                        count--;
                    }
                    break;case R.id.check_culture :

                    if(check.get(10)==0){
                        check.set(10,1);
                        count++;
                    }else{

                        check.set(10,0);
                        count--;
                    }
                    break;
                case R.id.check_education :

                    if(check.get(11)==0){
                        check.set(11,1);
                        count++;
                    }else{

                        check.set(11,0);
                        count--;
                    }
                    break;
                    case R.id.check_book :

                    if(check.get(12)==0){
                        check.set(12,1);
                        count++;
                    }else{

                        check.set(12,0);
                        count--;
                    }
                    break;
                case R.id.check_baby_products :

                    if(check.get(13)==0){
                        check.set(13,1);
                        count++;
                    }else{

                        check.set(13,0);
                        count--;
                    }
                    break;
                case R.id.check_mate_products :

                    if(check.get(14)==0){
                        check.set(14,1);
                        count++;
                    }else{

                        check.set(14,0);
                        count--;
                    }
                    break;
                case R.id.check_beauty :

                    if(check.get(15)==0){
                        check.set(15,1);
                        count++;
                    }else{

                        check.set(15,0);
                        count--;
                    }
                    break;
                case R.id.check_instrument :

                    if(check.get(16)==0){
                        check.set(16,1);
                        count++;
                    }else{

                        check.set(16,0);
                        count--;
                    }
                    break;
                case R.id.check_autobicycle :

                    if(check.get(17)==0){
                        check.set(17,1);
                        count++;
                    }else{

                        check.set(17,0);
                        count--;
                    }
                    break;
                case R.id.check_restaurant :

                    if(check.get(18)==0){
                        check.set(18,1);
                        count++;
                    }else{

                        check.set(18,0);
                        count--;
                    }
                    break;
                case R.id.check_food :

                    if(check.get(19)==0){
                        check.set(19,1);
                        count++;
                    }else{

                        check.set(19,0);
                        count--;
                    }
                    break;
                case R.id.check_toy :

                    if(check.get(20)==0){
                        check.set(20,1);
                        count++;
                    }else{

                        check.set(20,0);
                        count--;
                    }
                    break;

            }

        }
    };

    //체크가 되면 1, 안되면 0 // 1,1 1,0 // 2,1 2,0 //

    public void clickable(){
        if(count==5){
            for(int i = 0 ; i<6; i++){

            }
        }else{
            for(int  i = 0 ; i <6 ; i++){
                checkBox[i].setClickable(true);
            }
        }



// 체크 한 카테고리 조사 메소드


    }
    public void checkCategory(){
        interests = new ArrayList<>();
            for(int i = 0 ; i<check.size() ; i++){
                if(check.get(i)==1){
                    interests.add(check.get(i)+1);
                }
            }
    }
}
