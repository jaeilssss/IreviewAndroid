package com.flink.ireview.ui.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.ui.review.reviewWriteFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class  selectCategoryFragment extends Fragment {
View view;
private Member member;
    public selectCategoryFragment(Member member) {
        this.member = member;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_select_category, container, false);
        LinearLayout fashion,health,beauty, culture , lifestyle, education , interior , book , application , kids, it, pet , vehicle , hobby,sports,music,travel;
        culture = view.findViewById(R.id.culture_category);
        fashion  = view.findViewById(R.id.fashion_category);
        health = view.findViewById(R.id.health_category);
        beauty = view.findViewById(R.id.beauty_category);
        lifestyle = view.findViewById(R.id.lifestyle_category);
        education = view.findViewById(R.id.education_category);
        interior = view.findViewById(R.id.interior_category);
        book = view.findViewById(R.id.book_category);
        application = view.findViewById(R.id.application_category);
        kids = view.findViewById(R.id.kids_category);
        it = view.findViewById(R.id.it_category);
        pet = view.findViewById(R.id.pet_category);
        vehicle = view.findViewById(R.id.vehicle_category);
        hobby = view.findViewById(R.id.hobby_category);
        sports = view.findViewById(R.id.sport_category);
        music = view.findViewById(R.id.music_category);
        travel = view.findViewById(R.id.travel_category);
        fashion.setOnClickListener(onClickListener);
        health.setOnClickListener(onClickListener);
        beauty.setOnClickListener(onClickListener);
        lifestyle.setOnClickListener(onClickListener);
        education.setOnClickListener(onClickListener);
        interior.setOnClickListener(onClickListener);
        book.setOnClickListener(onClickListener);
        application.setOnClickListener(onClickListener);
        kids.setOnClickListener(onClickListener);
        it.setOnClickListener(onClickListener);
        pet.setOnClickListener(onClickListener);
        vehicle.setOnClickListener(onClickListener);
        hobby.setOnClickListener(onClickListener);
        sports.setOnClickListener(onClickListener);
        music.setOnClickListener(onClickListener);
        travel.setOnClickListener(onClickListener);
        culture.setOnClickListener(onClickListener);
        return view;
    }
    public void gotoCategory(Member member , int categoryId){
        Fragment fragment = new fragment_category(member,categoryId);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
            case R.id.fashion_category :
                gotoCategory(member , 0);
                break;
                case R.id.health_category :
                    gotoCategory(member , 1);
                     break;
                case R.id.beauty_category :
                    gotoCategory(member,2);
                    break;
                case R.id.culture_category :
                    gotoCategory(member,3);
                    break;
                case R.id.lifestyle_category :
                    gotoCategory(member , 4);
                    break;
                case R.id.education_category :
                    gotoCategory(member , 5);
                    break;
                case R.id.interior_category :
                    gotoCategory(member,6);
                    break;
                case R.id.book_category :
                    gotoCategory(member, 7);
                    break;
                case R.id.application_category :
                    gotoCategory(member,8);
                    break;
                case R.id.kids_category :
                    gotoCategory(member,9);
                    break;
                case R.id.it_category :
                    gotoCategory(member,10);
                    break;
                case R.id.pet_category :
                    gotoCategory(member,11);
                    break;
                case R.id.vehicle_category :
                    gotoCategory(member,12);
                    break;
                case R.id.hobby_category :
                    gotoCategory(member,13);
                    break;
                case R.id.sport_category :
                    gotoCategory(member,14);
                    break;
                case R.id.music_category :
                    gotoCategory(member,15);
                    break;
                case R.id.travel_category :
                    gotoCategory(member,16);
                     break;

            }
        }
    };
}
