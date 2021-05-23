package com.flink.ireview.viewPager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MainReviewSetViewPager  extends FragmentStatePagerAdapter {
    ArrayList<Fragment> frags = new ArrayList<>();
    private Context context;

    public MainReviewSetViewPager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    public  void addItem(Fragment item){
        frags.add(item);
    }
    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }
    @Override
    public int getCount() {
        return frags.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "홈";
            case 1:
                return "랭킹";
            case 2:

                return "이벤트";
            case 3:
                return  "•";
            case 4:
                return "•";
            case 5:
                return "•";
            case 6:
                return "•";
            case 7:
                return "•";
            default:
                return null;
        }
    }
}
