package com.flink.ireview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragEvent2 extends Fragment {
    private View view;

    public static FragEvent2 newinstance(){
        FragEvent2 fragEvent2 = new FragEvent2();
        return fragEvent2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_event2, container, false);

        return view;
    }
}