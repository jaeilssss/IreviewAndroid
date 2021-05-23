package com.flink.ireview.ui.MyPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.R;
import com.flink.ireview.ui.Option.FragmentOptionViewModel;

public class fragment_modify_password extends Fragment {

    private FragmentOptionViewModel mViewModel;

    public static fragment_modify_password newInstance() {
        return new fragment_modify_password();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentOptionViewModel.class);
        // TODO: Use the ViewModel
    }

}
