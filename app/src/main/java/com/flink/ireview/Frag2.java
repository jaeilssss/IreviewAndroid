package com.flink.ireview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Recycler_ranking_product.RankingproductAdapter;
import com.flink.ireview.Recycler_ranking_product.RankingproductData;
import com.flink.ireview.Recycler_ranking_review.RankingreviewAdapter;
import com.flink.ireview.Recycler_ranking_review.RankingreviewData;
import com.flink.ireview.Recycler_ranking_user.RankinguserAdapter;
import com.flink.ireview.Recycler_ranking_user.RankinguserData;

import java.util.ArrayList;

public class Frag2 extends Fragment {

    private View view;

    private ArrayList<RankingproductData> arrayList1;
    private RankingproductAdapter rankingproductAdapter;
    private RecyclerView recyclerView1;
    private LinearLayoutManager linearLayoutManager1;

    private ArrayList<RankingreviewData> arrayList2;
    private RankingreviewAdapter rankingreviewAdapter;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager2;

    private ArrayList<RankinguserData> arrayList3;
    private RankinguserAdapter rankinguserAdapter;
    private RecyclerView recyclerView3;
    private LinearLayoutManager linearLayoutManager3;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag2, container, false);


        //리사이클러뷰 1
        recyclerView1 = (RecyclerView)view.findViewById(R.id.rv_ranking_product);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);

        arrayList1 = new ArrayList<>();
        rankingproductAdapter = new RankingproductAdapter(arrayList1);

        recyclerView1.setAdapter(rankingproductAdapter);


        //리사이클러뷰 2
        recyclerView2 = (RecyclerView)view.findViewById(R.id.rv_ranking_review);
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);

        arrayList2 = new ArrayList<>();
        rankingreviewAdapter = new RankingreviewAdapter(arrayList2);

        recyclerView2.setAdapter(rankingreviewAdapter);


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
