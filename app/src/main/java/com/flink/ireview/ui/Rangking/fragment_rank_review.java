package com.flink.ireview.ui.Rangking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;
import com.flink.ireview.Recycler_ranking_review.RankingreviewAdapter;
import com.flink.ireview.Recycler_ranking_review.RankingreviewData;

import java.util.ArrayList;

public class fragment_rank_review extends Fragment {

    private View view;

    private ArrayList<RankingreviewData> arrayList2;
    private RankingreviewAdapter rankingreviewAdapter;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rank_review, container, false);

        //리싸이클러뷰 2
        recyclerView2 = (RecyclerView)view.findViewById(R.id.rv_ranking_review);
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);

        arrayList2 = new ArrayList<>();
        rankingreviewAdapter = new RankingreviewAdapter(arrayList2);

        recyclerView2.setAdapter(rankingreviewAdapter);


        return view;


    }
}
