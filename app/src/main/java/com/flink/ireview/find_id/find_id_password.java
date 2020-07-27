package com.flink.ireview.find_id;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flink.ireview.Find_ViewPagerAdapter;
import com.flink.ireview.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class find_id_password extends Fragment {

    private FragmentPagerAdapter fragmentPagerAdapter;


    public find_id_password() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_find_id_password, container, false);

        //뷰페이저 세팅
        ViewPager viewPager = view.findViewById(R.id.find_vp);
        fragmentPagerAdapter = new Find_ViewPagerAdapter(getChildFragmentManager());

        TabLayout tabLayout = view.findViewById(R.id.tab_find);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;

    }


}
