package com.flink.ireview.viewPager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ReviewImageViewPager extends FragmentStatePagerAdapter  {
    ArrayList<Fragment> frags = new ArrayList<>();
    private Context context;
    public ReviewImageViewPager(FragmentManager fm, Context context) {
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
                return "•";
            case 1:
                return "•";
            case 2:

                return "•";
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
