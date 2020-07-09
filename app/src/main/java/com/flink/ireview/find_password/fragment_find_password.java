package com.flink.ireview.find_password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.R;

public class fragment_find_password extends Fragment {

    private FragmentFindPasswordViewModel mViewModel;
    private EditText find_password_textView_Email;
    private Button find_id_find_button, find_id_cancle_button;

    public static fragment_find_password newInstance() {
        return new fragment_find_password();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_password, container, false);
        find_password_textView_Email = view.findViewById(R.id.find_password_textView_Email);
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
                        UsersDao dao  = new UsersDao(getContext(),getFragmentManager().beginTransaction());
                        String email = find_password_textView_Email.getText().toString();
                        dao.passwordEmailSend(email);
                        break;
                }
        }
    };


}
