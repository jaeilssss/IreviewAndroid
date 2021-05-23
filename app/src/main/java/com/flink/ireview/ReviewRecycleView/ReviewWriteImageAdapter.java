package com.flink.ireview.ReviewRecycleView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.R;
import com.flink.ireview.ui.review.ReviewModifyFragment;
import com.flink.ireview.ui.review.reviewWriteFragment;

import java.util.ArrayList;

public class ReviewWriteImageAdapter extends RecyclerView.Adapter<ReviewWriteImageVIewHolder> {
    private Context context;
    private ArrayList<String> templist;
    private FragmentTransaction fragmentTransaction ;
    private Board board;
    private Member member;
    private int check;

    public ReviewWriteImageAdapter(Context context, ArrayList<String> templist, FragmentTransaction fragmentTransaction,Board board,Member member , int check) {
        this.context = context;
        this.templist = templist;
        this.fragmentTransaction = fragmentTransaction;
        this.board = board;
        this.member = member;
        this.check = check;
    }

    @NonNull
    @Override
    public ReviewWriteImageVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View baseView = View.inflate(context, R.layout.review_write_image_item, null);
        ReviewWriteImageVIewHolder reviewWriteImageVIewHolder = new ReviewWriteImageVIewHolder(baseView,this,context);
        return reviewWriteImageVIewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewWriteImageVIewHolder holder, int position) {

        // 원래 이미지가 여러개 이므로 for문을 돌려야한다!!!
        if(!templist.get(position).equals("null")){
            Glide.with(context).load(templist.get(position)).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return templist.size();
    }

    public void selectCancel(int position){
        templist.remove(position);
        board.setImage1("null");
        board.setImage2("null");
        board.setImage3("null");
        board.setImage4("null");
        board.setImage5("null");
        board.setImage6("null");
        board.setImage7("null");
        board.setImage8("null");
        setImage();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("AlertDialog Title");
        builder.setMessage("해당 이미지를 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        setImage();
                        if(check==1){
                            Toast.makeText(context,"이미지가 삭제되었습니다",Toast.LENGTH_SHORT).show();

                            Fragment fragment = new ReviewModifyFragment(member , board);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }else{
                            Toast.makeText(context,"이미지가 삭제되었습니다",Toast.LENGTH_SHORT).show();

                            Fragment fragment = new reviewWriteFragment(board ,member);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
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




    public void setImage(){
        for(int i =0 ; i<templist.size();i++){
            if(i==0){
                board.setImage1(templist.get(i));
            }else if(i==1){
                board.setImage2(templist.get(i));
            }else if(i==2){
                board.setImage3(templist.get(i));
            }else if(i==3){
                board.setImage4(templist.get(i));
            }else if(i==4){
                board.setImage5(templist.get(i));
            }else if(i==5){
                board.setImage6(templist.get(i));
            }else if(i==6){
                board.setImage7(templist.get(i));
            }else if(i==7){
                board.setImage8(templist.get(i));
            }
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
