package com.flink.ireview.find_id;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flink.ireview.R;
import com.flink.ireview.http.User.FIndIdHttp;

public class fragment_find_id extends Fragment {

    private FragmentFindIdViewModel mViewModel;
    Button confirm,cancel;
    EditText name, email;

    public fragment_find_id(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }

    private FragmentTransaction fragmentTransaction;

    public static fragment_find_id newInstance(FragmentTransaction fragmentTransaction) {
        return new fragment_find_id(fragmentTransaction);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_id, container, false);
        confirm = view.findViewById(R.id.find_id_find_button);
        confirm.setOnClickListener(onClickListener);
        name = view.findViewById(R.id.find_id_name);
        email = view.findViewById(R.id.find_id_email);
        return view;    }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.find_id_find_button :
                        if(name.getText().toString().length()==0 || email.getText().toString().length()==0){
                            Toast.makeText(getContext(),"이름 및 이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            FIndIdHttp http = new FIndIdHttp();
                            System.out.println(name.getText().toString());
                            String ename = name.getText().toString();
                            String eemail = email.getText().toString();
                            http.setBodyContents(ename,eemail);
                            String result = http.send();
                            if(result.equals("error")){
                                Toast.makeText(getContext(),"존재하지않은 아이디 입니다",Toast.LENGTH_SHORT).show();
                            }else{
                                Fragment fragment = new FindResultIdFragment(ename,result);
                                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                            }
                            break;
                        }

                }
            }
        };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentFindIdViewModel.class);
        // TODO: Use the ViewModel
    }

}
