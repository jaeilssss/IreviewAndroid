package com.flink.ireview.ui.ReviewImageListFrag;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flink.ireview.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFrag4 extends Fragment {
        private String uri;
        private Context context;
        private View view;

    public ImageFrag4(String uri, Context context) {
        this.uri = uri;
        this.context = context;
    }

    public ImageFrag4(String uri) {
        this.uri = uri;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_image_frag4, container, false);

        ImageView imageView = view.findViewById(R.id.review_image4);
        Glide.with(this).load(uri).into(imageView);
        return view;
    }
}
