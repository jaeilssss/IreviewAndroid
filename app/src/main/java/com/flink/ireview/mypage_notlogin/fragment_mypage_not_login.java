package com.flink.ireview.mypage_notlogin;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flink.ireview.R;

public class fragment_mypage_not_login extends Fragment {

    private FragmentMypageNotLoginViewModel mViewModel;

    public static fragment_mypage_not_login newInstance() {
        return new fragment_mypage_not_login();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mypage_not_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentMypageNotLoginViewModel.class);
        // TODO: Use the ViewModel
    }

}
