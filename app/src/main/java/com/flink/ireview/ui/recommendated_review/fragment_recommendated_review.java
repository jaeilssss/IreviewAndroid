package com.flink.ireview.ui.recommendated_review;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flink.ireview.R;

public class fragment_recommendated_review extends Fragment {

    private FragmentRecommendatedReviewViewModel mViewModel;

    public static fragment_recommendated_review newInstance() {
        return new fragment_recommendated_review();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendated_review, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentRecommendatedReviewViewModel.class);
        // TODO: Use the ViewModel
    }

}
