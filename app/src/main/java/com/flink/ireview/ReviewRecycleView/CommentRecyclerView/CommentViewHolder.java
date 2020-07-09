package com.flink.ireview.ReviewRecycleView.CommentRecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    TextView nickname , content , date;

    ImageView photo;

    Button delete , write , read;

    CommentAdapter madapter;

    public CommentViewHolder(@NonNull View itemView, CommentAdapter madapter) {

        super(itemView);
        this.madapter = madapter;

        nickname = itemView.findViewById(R.id.comment_nickname);

        content = itemView.findViewById(R.id.comment_content);

        date = itemView.findViewById(R.id.comment_date);

        photo = itemView.findViewById(R.id.comment_photo);


        delete = itemView.findViewById(R.id.comment_delete_button);
        delete.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.comment_delete_button :
                madapter.commentDelete(getAdapterPosition());
                    break;
              }
        }
    };
}
