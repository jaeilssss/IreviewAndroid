package com.flink.ireview;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.find_id.fragment_find_id;
import com.flink.ireview.find_password.fragment_find_password;

public class Find_ViewPagerAdapter extends FragmentPagerAdapter{
    private FragmentTransaction fragmentTransaction;
    public Find_ViewPagerAdapter(FragmentManager fm, FragmentTransaction fragmentTransaction) {
        super(fm);
        this.fragmentTransaction = fragmentTransaction;
    }
    //프래그먼트 교체를 보여주는 처리를 구현한 곳
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fragment_find_id.newInstance(fragmentTransaction);
            case 1:
                return fragment_find_password.newInstance(fragmentTransaction);

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    //상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "id";
            case 1:
                return "password";
            default:
                return null;
        }
    }
}
