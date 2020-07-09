package com.flink.ireview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Recycler_alarm.AlarmAdapter;
import com.flink.ireview.Recycler_alarm.AlarmData;

import java.util.ArrayList;

public class Frag4 extends Fragment {

    private ArrayList<AlarmData> arrayList;
    private AlarmAdapter alarmAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private View view;
    private Object Button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag4, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.rv_alarm);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        alarmAdapter = new AlarmAdapter(arrayList);
        recyclerView.setAdapter(alarmAdapter);

        AlarmData alarmData = new AlarmData(R.drawable.profile_human,"id","님이 회원님의 게시글을 좋아합니다.", "5일 전", R.drawable.tap_icon_appliances);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);
        arrayList.add(alarmData);

        Button btn_add = (Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmData alarmData = new AlarmData(R.drawable.profile_human,"id","님이 회원님의 게시글을 좋아합니다.", "5일 전", R.drawable.tap_icon_appliances);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                arrayList.add(alarmData);
                alarmAdapter.notifyDataSetChanged();
            }
        });


        return view;


    }
}
