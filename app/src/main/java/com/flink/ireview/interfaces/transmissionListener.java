package com.flink.ireview.interfaces;

import android.view.View;

import com.flink.ireview.Dto.Member;

public interface transmissionListener {
    void onReceivedData(Object data);
//    Member member;
    Member getData();
}
