package com.flink.ireview.ui.review;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.R;
import com.flink.ireview.ui.Category.fragment_category;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewModifyFragment extends Fragment {
    private Member member;
    ReviewDto dto;
    View view;
    ImageView imageView;
    Button modify_submit , modify_exit;
    EditText title , content , advantage , weakenss ;
    ReviewDao dao;
    public ReviewModifyFragment() {
        // Required empty public constructor
    }

    public ReviewModifyFragment(ReviewDto dto){
        this.dto = dto;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_review_modify, container, false);
        dao = new ReviewDao(getContext(),getFragmentManager().beginTransaction());
        imageView = view.findViewById(R.id.review_modify_image);
        title = view.findViewById(R.id.review_modify_title);
        content = view.findViewById(R.id.review_modify_content);
        advantage = view.findViewById(R.id.review_modify_advantage);
        weakenss = view.findViewById(R.id.review_modify_weakness);
        modify_submit = view.findViewById(R.id.review_modify_submit);
        modify_exit = view.findViewById(R.id.review_modify_exit);
        // 사진이 여러개 이므로 원래는 반복문으로 코드 작성 해줘야함 !!!
        Glide.with(this).load(dto.getReview_main_image().get(0)).into(imageView);
        title.setText(dto.getReview_main_title());
        content.setText(dto.getReview_main_string());
        // 장점 , 단점 도 반복문 으로 해줘야 함!!!
        advantage.setText(dto.getReview_main_advantage().get(0));
        weakenss .setText(dto.getReview_main_weakness().get(0));
        modify_exit.setOnClickListener(onClickListener);
        modify_submit.setOnClickListener(onClickListener);
        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.review_modify_submit :
                    dto.setReview_main_title(title.getText().toString());
                    dto.setReview_main_string(content.getText().toString());
                    ArrayList<String> advantageList = new ArrayList<>();
                    ArrayList<String> weaknessList = new ArrayList<>();
                    // 원래는 반복문으로 장점, 단점 처리 해야함!!!!!!
                    advantageList.add(advantage.getText().toString());
                    weaknessList.add(weakenss.getText().toString());
                    dto.setReview_main_advantage(advantageList);
                    dto.setReview_main_weakness(weaknessList);
                    dao.modify(dto);
                    break;
                case R.id.review_modify_exit :
                    Fragment fragment = new fragment_category(member);
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                    break;
            }
        }
    };

}
