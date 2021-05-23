package com.flink.ireview.find_id;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flink.ireview.R;
import com.flink.ireview.ui.login.LoginFragment;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindResultIdFragment extends Fragment {
    private String name;
    private String id;
    private TextView textViewName, textViewId;
    private View view;
    private Button login,find;

    public FindResultIdFragment(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public FindResultIdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_find_result_id, container, false);

        textViewName= view.findViewById(R.id.result_page_name);
        textViewId = view.findViewById(R.id.result_page_id);
        textViewId.setText(id);
        textViewName.setText(name);
        login = view.findViewById(R.id.result_id_page_login_button);
        find = view.findViewById(R.id.find_id_password_button);
        login.setOnClickListener(onClickListener);
        find.setOnClickListener(onClickListener);
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.result_id_page_login_button :
                    Fragment fragment = new LoginFragment();
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.main_frame,fragment).commit();
                    break;
                case R.id.find_id_password_button :
                    Fragment fragment1 = new find_id_password();
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.main_frame,fragment1).commit();
                    break;
            }
        }
    };
}
