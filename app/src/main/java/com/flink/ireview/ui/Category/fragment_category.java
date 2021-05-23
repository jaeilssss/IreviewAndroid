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

import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.Recycler_category_tree.CategorytreeAdapter;
import com.flink.ireview.Recycler_category_tree.CategorytreeData;
import com.flink.ireview.ReviewRecycleView.ReviewAdapter;
import com.flink.ireview.ReviewRecycleView.ReviewAdapter2;
import com.flink.ireview.http.board.reviewSetListHttp;
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
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.ArrayList;
import java.util.Map;

public class fragment_category extends Fragment {
    private int categoryId;

    public fragment_category(Member member) {
        this.member = member;
    }

    public fragment_category(Member member , int categoryId) {
        this.member = member;
        this.categoryId= categoryId;
        list = null;
    }

    public fragment_category(int categoryId, Member member, ArrayList<Board> list) {
        this.categoryId = categoryId;
        this.member = member;
        this.list = list;
    }

    private Member member;
    private ArrayList<Integer> arrayList;
    private CategorytreeAdapter categorytreeAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private TextView categoryName;
    private Button write;
    private FirebaseUser user ;
    private RecyclerView rcv;
    private int start;
    private ArrayList<Board> list ;
    View view;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_category_tree);
        gridLayoutManager = new GridLayoutManager(getContext(), 17);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryName = view.findViewById(R.id.category_name);
        arrayList = new ArrayList<>();
    arrayList.add(categoryId);
    int j=0;
    for(int i=0; i <17;i++){
        if(arrayList.get(0)!=i){
            arrayList.add(i);
        }
    }
        categorytreeAdapter = new CategorytreeAdapter(arrayList,categoryId);
        recyclerView.setAdapter(categorytreeAdapter);


        categorytreeAdapter.notifyDataSetChanged();
        start = 0;
         fragmentTransaction =  getFragmentManager().beginTransaction();
        setReviewList();
        setCategoryName(categoryId);
        return view;
    }
public void setReviewList(){
        if(list==null){
            reviewSetListHttp reviewSetListHttp = new reviewSetListHttp();
            reviewSetListHttp.setBodyContents(categoryId , start);
            list = new ArrayList<>();
            list = reviewSetListHttp.send();
        }
    if(list!=null){
        rcv = view.findViewById(R.id.rv_category);
        ReviewAdapter2 adapter = new ReviewAdapter2(getContext(),member,fragmentTransaction,list);
        rcv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcv.setAdapter(adapter);
    }

}
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    };

    public void setCategoryName(int categoryId){
        switch (categoryId){
            case 0:
              categoryName.setText("패션");
                break;
            case 1:
            categoryName.setText("의료");
                break;
            case 2:
                categoryName.setText("뷰티");
                break;
            case 3:
                categoryName.setText("문화");
                break;
            case 4:
                categoryName.setText("생활용품");
                break;
            case 5:
                categoryName.setText("교육");
                break;
            case 6:
                categoryName.setText("인테리어");
                break;
            case 7:
                categoryName.setText("도서");
                break;
            case 8:
                categoryName.setText("가전제품");
                break;
            case 9:
                categoryName.setText("유아용품");
                break;
            case 10:
                categoryName.setText("IT");
                break;
            case 11:
                categoryName.setText("반려용품");
                break;
            case 12:
                categoryName.setText("차량/오토바이");
                break;
            case 13:
                categoryName.setText("취미");
                break;
            case 14:
                categoryName.setText("스포츠/레저");
                break;
            case 15:
                categoryName.setText("악기");
                break;
            case 16:
                categoryName.setText("여행");
                break;
        }
    }
}

