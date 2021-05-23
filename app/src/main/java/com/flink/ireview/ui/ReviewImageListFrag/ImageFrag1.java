package com.flink.ireview.ui.ReviewImageListFrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ViewTarget;
import com.flink.ireview.R;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFrag1 extends Fragment {
    private  String uri;
    private Context context;
    View  view;
    Integer i;
    Bitmap bitmap;
   private Drawable data;
    BitmapDrawable drawable;

    public ImageFrag1(Context context, Drawable imageView) {
        this.context = context;
        this.data = imageView;
    }

    public ImageFrag1(String uri, Context context) {
        this.uri = uri;
        this.context = context;
        i=0;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_image_frag1, container, false);

        ImageView imageView1 = view.findViewById(R.id.review_image1);
        Glide.with(getContext()).load(uri).into(imageView1);



//        imageView.setImageBitmap(bitmap.);
        return view;

    }
}
