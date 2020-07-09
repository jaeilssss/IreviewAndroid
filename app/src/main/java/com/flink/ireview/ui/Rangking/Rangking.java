package com.flink.ireview.ui.Rangking;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class Rangking extends Fragment {

    private RangkingViewModel mViewModel;

    RecyclerView product_rvList;
    RecyclerView review_rvList;
    RecyclerView user_rvList;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager1;
    RecyclerView.LayoutManager layoutManager2;


    RecyclerView.Adapter product_adapter;
    RecyclerView.Adapter review_adapter;
    RecyclerView.Adapter user_adapter;

    public static Rangking newInstance() {
        return new Rangking();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rangking, container, false);

        final String TAG = "DocSnippets";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        final CollectionReference usersRef = db.collection("users");
        usersRef.orderBy("users_reliability").limit(5);
        Button rangking_button = view.findViewById(R.id.rangking_button);
        rangking_button.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.orderBy("users_reliability").limit(5)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        UsersDto usersDto = document.toObject(UsersDto.class);
                                        String temp = usersDto.getUsers_nickname();
                                        Toast.makeText(getContext(), temp, LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Log.d(TAG,"ERROR",task.getException());
                                }
                            }
                        });
            }
        });






        product_rvList = view.findViewById(R.id.rangking_product_list);
        product_rvList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        product_rvList.setLayoutManager(layoutManager);

        review_rvList = view.findViewById(R.id.rangking_review_list);
        review_rvList.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext());
        review_rvList.setLayoutManager(layoutManager1);

        user_rvList = view.findViewById(R.id.rangking_user_list);
        user_rvList.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getContext());
        user_rvList.setLayoutManager(layoutManager2);

        ArrayList<RecyclerView_PostItem> product_Item = new ArrayList<>();
        product_Item.clear();

        ArrayList<RecyclerView_PostItem> review_Item = new ArrayList<>();
        review_Item.clear();

        ArrayList<RecyclerView_PostItem> user_Item = new ArrayList<>();
        user_Item.clear();



        for(int i=0;i<5;i++){
            //스트링 다섯개!
            RecyclerView_PostItem product_postItem = new RecyclerView_PostItem("qwer","qwer","qwer","qwer");
            product_Item.add(product_postItem);

            RecyclerView_PostItem review_postItem = new RecyclerView_PostItem("qwer","qwer","qwer","qwer");
            review_Item.add(review_postItem);

            RecyclerView_PostItem user_postItem = new RecyclerView_PostItem("qwer","qwer","qwer","qwer");
            user_Item.add(user_postItem);

        }
        product_adapter = new RecyclerView_Custm_Adapter(product_Item);
        review_adapter = new RecyclerView_Custm_Adapter(review_Item);
        user_adapter = new RecyclerView_Custm_Adapter(user_Item);

        product_rvList.setAdapter(product_adapter);
        review_rvList.setAdapter(review_adapter);
        user_rvList.setAdapter(user_adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RangkingViewModel.class);
        // TODO: Use the ViewModel
    }

}
