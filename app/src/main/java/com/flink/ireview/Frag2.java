package com.flink.ireview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Recycler_ranking_product.RankingproductAdapter;
import com.flink.ireview.Recycler_ranking_product.RankingproductData;
import com.flink.ireview.Recycler_ranking_review.RankingreviewAdapter;
import com.flink.ireview.Recycler_ranking_review.RankingreviewData;
import com.flink.ireview.Recycler_ranking_user.RankinguserAdapter;
import com.flink.ireview.Recycler_ranking_user.RankinguserData;
import com.flink.ireview.ui.Rangking.fragment_rank_product;
import com.flink.ireview.ui.Rangking.fragment_rank_review;
import com.flink.ireview.ui.Rangking.fragment_rank_user;
import com.flink.ireview.ui.recommendated_review.fragment_recommendated_review;

import java.util.ArrayList;

public class Frag2 extends Fragment {

    private Button rank_product_more, rank_review_more, rank_user_more;

    private View view;
    private TextView product_more, review_more, user_more;

    public static Frag2 newinstance(){
        Frag2 frag2 = new Frag2();
        return frag2;
    }

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



        product_more = view.findViewById(R.id.rank_product_more);
        product_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragment_rank_product fragment_rank_product = new fragment_rank_product();
                transaction.replace(R.id.main_frame, fragment_rank_product);
                transaction.commit();
            }
        });

        review_more = view.findViewById(R.id.rank_review_more);
        review_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragment_rank_review fragment_rank_review = new fragment_rank_review();
                transaction.replace(R.id.main_frame, fragment_rank_review);
                transaction.commit();
            }
        });

        user_more = view.findViewById(R.id.rank_user_more);
        user_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragment_rank_user fragment_rank_user = new fragment_rank_user();
                transaction.replace(R.id.main_frame, fragment_rank_user);
                transaction.commit();
            }
        });


        return view;

    }
}
