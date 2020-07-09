package com.flink.ireview.ui.Category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.Recycler_category_tree.CategorytreeAdapter;
import com.flink.ireview.Recycler_category_tree.CategorytreeData;
import com.flink.ireview.ReviewRecycleView.ReviewAdapter;
import com.flink.ireview.ui.review.reviewWriteFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class fragment_category extends Fragment {
    public fragment_category(Member member) {
        this.member = member;
    }

    private Member member;
    private ArrayList<CategorytreeData> arrayList;
    private CategorytreeAdapter categorytreeAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    private FragmentCategoryViewModel mViewModel;
    private Button write;
    private UsersDto dto;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user ;
    private RecyclerView rcv;
    ArrayList<ReviewDto> list;
    Fragment current;
    View view;
    FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        if(member!=null){
            Toast.makeText(getContext(),"닉네임 : "+member.getNickName(),Toast.LENGTH_SHORT).show();
        }
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_category_tree);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        arrayList = new ArrayList<>();

        categorytreeAdapter = new CategorytreeAdapter(arrayList);
        recyclerView.setAdapter(categorytreeAdapter);

        CategorytreeData categorytreeData = new CategorytreeData("전체 보기");
        arrayList.add(categorytreeData);


        categorytreeAdapter.notifyDataSetChanged();




        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            System.out.println("여기!");
            setMyInfo();
            user = FirebaseAuth.getInstance().getCurrentUser();
        }else{
            setReviewList();
        }
        fragmentTransaction = getFragmentManager().beginTransaction();
        write = view.findViewById(R.id.review_write_button);
        dto = new UsersDto();
        final Fragment fragment = new reviewWriteFragment(dto);
        write.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Toast.makeText(getContext(),"회원만 이용 가능 합니다!!", Toast.LENGTH_SHORT).show();

                }else{
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
                }
            }
        });

        return view;
    }

    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(FragmentCategoryViewModel.class);
//        // TODO: Use the ViewModel
//
//    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.review_write_button :
                    if(FirebaseAuth.getInstance().getCurrentUser()==null){
                        Toast.makeText(getContext(),"회원만 이용 가능 합니다!!", Toast.LENGTH_SHORT).show();
                        break;
                    }else{
                        Fragment fragment = new reviewWriteFragment(dto);
                        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.nav_host_fragment,fragment).commit();
                        break;
                    }
            }
        }
    };
    public void setReviewList(){
        list = new ArrayList<>();
        db.collection("category").document("test")
                .collection("review")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        Map<String, Object> map = documentSnapshot.getData();
                        ReviewDto dto = new ReviewDto(
                                String.valueOf(map.get("reviewer_nickname")), String.valueOf(map.get("reviewer_uid")),
                                String.valueOf(map.get("review_create_time")), String.valueOf(map.get("review_category_UID")),
                                String.valueOf(map.get("review_main_title")), String.valueOf(map.get("review_main_string")),
                                (ArrayList<String>)map.get("review_main_image"),(ArrayList<String>)map.get("review_main_advantage"),
                                (ArrayList<String>)map.get("review_main_weakness"),(ArrayList<String>)map.get("recommend_list"),
                                Integer.parseInt(String.valueOf(map.get("recommend_count"))), Integer.parseInt(String.valueOf(map.get("review_view_number"))),
                                (ArrayList<String>)map.get("comment_list"),(ArrayList<String>)map.get("scrap_list"));
                        System.out.println(documentSnapshot.getId());
                        dto.setReview_UID(documentSnapshot.getId());
                        list.add(dto);

                    }
                    rcv = view.findViewById(R.id.rv_category);
                    //  FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    ReviewAdapter adapter = new ReviewAdapter(getContext(),list,fragmentTransaction,dto);
                    rcv.setLayoutManager(new GridLayoutManager(getContext(),3));
                    rcv.setAdapter(adapter);

                }
            }
        });
    }
    public void setMyInfo(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dto = documentSnapshot.toObject(UsersDto.class);
                setReviewList();
            }
        });
    }
    public void setMyinfoGoToRevieWrite(){
        // 이게 사실은 우리 플젝 구조상 dao 클래스에 들어가 있는게 맞지만 네비게이션은 해당 dao 메소드로 못들어가서 일단 여기에 둠
        db.collection("users").document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dto = documentSnapshot.toObject(UsersDto.class);
                Fragment fragment = new reviewWriteFragment(dto);
                fragmentTransaction.addToBackStack(null).replace(R.id.nav_host_fragment,fragment).commit();

            }
        });
    }
}

