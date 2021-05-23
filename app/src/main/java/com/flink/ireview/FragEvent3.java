package com.flink.ireview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.flink.ireview.RecyclerView.Recycler_evnet.EventAdapter;
import com.flink.ireview.RecyclerView.Recycler_evnet.EventData;

import java.util.ArrayList;

public class FragEvent3 extends Fragment {
    private View view;

    private ArrayList<EventData> arrayList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public static FragEvent3 newinstance(){
        FragEvent3 fragEvent3 = new FragEvent3();
        return fragEvent3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_event3, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_event);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        eventAdapter = new EventAdapter(arrayList);
        recyclerView.setAdapter(eventAdapter);

        Button event_add = (Button)view.findViewById(R.id.event_add);
        event_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventData eventData = new EventData(0, "이벤트 제목", "2020.10.13");
                arrayList.add(eventData);
                eventAdapter.notifyDataSetChanged();
            }
        });



        return view;
    }
}