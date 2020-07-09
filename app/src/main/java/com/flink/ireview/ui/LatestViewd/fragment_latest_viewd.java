package com.flink.ireview.ui.LatestViewd;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flink.ireview.R;

public class fragment_latest_viewd extends Fragment {

    private FragmentLatestViewdViewModel mViewModel;

    public static fragment_latest_viewd newInstance() {
        return new fragment_latest_viewd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_latest_viewd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentLatestViewdViewModel.class);
        // TODO: Use the ViewModel
    }

}
