package com.flink.ireview.ui.recommendated_review;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.like.RecommendationAdapter;
import com.flink.ireview.http.board.getMyLikeReviewHttp;

import java.util.ArrayList;

public class fragment_recommendated_review extends Fragment {
    private Long id;
    private Member member;
    public fragment_recommendated_review(Long id,Member member) {
        this.id = id;
        this.member = member;
    }

    private FragmentRecommendatedReviewViewModel mViewModel;

    public static fragment_recommendated_review newInstance(Long id,Member member) {
        return new fragment_recommendated_review(id,member);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_recommendated_review, container, false);
        RecyclerView rcv = view.findViewById(R.id.rv_my_recommendated_review);
        getMyLikeReviewHttp http = new getMyLikeReviewHttp();
        int start = 0 ;
        http.setBodyContents(id,start);
        ArrayList<Board> list = http.send();
        if(list.size()>0){
            RecommendationAdapter adapter = new RecommendationAdapter(list,getFragmentManager().beginTransaction(),getContext(),member);
            rcv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            rcv.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentRecommendatedReviewViewModel.class);
        // TODO: Use the ViewModel
    }

}
