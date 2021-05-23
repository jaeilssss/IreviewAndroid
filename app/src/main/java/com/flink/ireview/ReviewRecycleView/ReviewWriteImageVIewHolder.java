package com.flink.ireview.ReviewRecycleView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;
import com.flink.ireview.http.board.reviewDeleteHttp;
import com.flink.ireview.ui.Category.fragment_category;

public class ReviewWriteImageVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView ;
    Button button ;
    ReviewWriteImageAdapter adapter ;
    private Context context;

    public ReviewWriteImageVIewHolder(@NonNull View itemView, ReviewWriteImageAdapter adapter, Context context) {
        super(itemView);
        imageView  = itemView.findViewById(R.id.imageviewItem);
        button = itemView.findViewById(R.id.profile_image_cancel);
        button.setOnClickListener(this);
        this.adapter = adapter;
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
                switch (view.getId()){
                    case R.id.profile_image_cancel :
                        adapter.selectCancel(getAdapterPosition());

                        break;
                }
    }
    void show()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 이미지를 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            adapter.selectCancel(getAdapterPosition());
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}
