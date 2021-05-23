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
import com.flink.ireview.Recycler_ranking_user.RankinguserAdapter;
import com.flink.ireview.Recycler_ranking_user.RankinguserData;

import java.util.ArrayList;

public class fragment_rank_user extends Fragment {

    private View view;

    private ArrayList<RankinguserData> arrayList3;
    private RankinguserAdapter rankinguserAdapter;
    private RecyclerView recyclerView3;
    private LinearLayoutManager linearLayoutManager3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rank_user, container, false);

        //리사이클러뷰 3
        recyclerView3 = (RecyclerView)view.findViewById(R.id.rv_ranking_user);
        linearLayoutManager3 = new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(linearLayoutManager3);

        arrayList3 = new ArrayList<>();
        rankinguserAdapter = new RankinguserAdapter(arrayList3);

        recyclerView3.setAdapter(rankinguserAdapter);

        return view;


    }
}
