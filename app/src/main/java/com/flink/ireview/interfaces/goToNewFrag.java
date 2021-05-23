package com.flink.ireview.interfaces;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public interface goToNewFrag {
    public FragmentTransaction goToFrag(Fragment fragment);
}
