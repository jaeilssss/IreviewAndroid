package com.flink.ireview;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.Dto.Member;

public class Event_ViewPagerAdapter extends FragmentPagerAdapter {
    private Member member;
    private FragmentManager fm ;
    FragmentTransaction fragmentTransaction;
    public Event_ViewPagerAdapter(FragmentManager fm, FragmentTransaction fragmentTransaction) {
        super(fm);
        this.fm = fm;
        this.fragmentTransaction = fragmentTransaction;
    }

    //프래그먼트 교체를 보여주는 처리를 구현한 곳
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragEvent1.newinstance(fragmentTransaction);
            case 1:
                return Frag2.newinstance();
            case 2:
                return FragEvent3.newinstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
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
            default:
                return null;
        }
    }

}