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
import com.flink.ireview.Recycler_ranking_product.RankingproductAdapter;
import com.flink.ireview.Recycler_ranking_product.RankingproductData;

import java.util.ArrayList;

public class fragment_rank_product extends Fragment {

    private View view;

    private ArrayList<RankingproductData> arrayList1;
    private RankingproductAdapter rankingproductAdapter;
    private RecyclerView recyclerView1;
    private LinearLayoutManager linearLayoutManager1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rank_product, container, false);

        //리사이클러뷰 1
        recyclerView1 = (RecyclerView)view.findViewById(R.id.rv_ranking_product);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);

        arrayList1 = new ArrayList<>();
        rankingproductAdapter = new RankingproductAdapter(arrayList1);

        recyclerView1.setAdapter(rankingproductAdapter);

        return view;


    }
}
