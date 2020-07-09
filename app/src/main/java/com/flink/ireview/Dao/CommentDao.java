package com.flink.ireview.Dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.ui.review.ReviewReadPageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CommentDao {
    private FragmentTransaction fragmentTransaction ;
    private Context mcontext;
    private FirebaseUser user;
    private FirebaseFirestore db ;
    private Fragment current;

    public Fragment getCurrent() {
        return current;
    }

    public void setCurrent(Fragment current) {
        this.current = current;
    }

    public CommentDao(Context mcontext, FragmentTransaction fragmentTransaction) {

        this.mcontext = mcontext;
        this.fragmentTransaction = fragmentTransaction;
    }
    public void write(CommentDto cdto, final ReviewDto dto, final UsersDto udto){
        db = FirebaseFirestore.getInstance();
        db.collection("category")
                .document("test")
                .collection("review")
                .document(dto.getReview_UID())
                .collection("comment")
                .add(cdto).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(mcontext,"댓글 작성이 완료 되었습니다", Toast.LENGTH_SHORT).show();
                    // 마지막 카테고리 파라미터 수정 할것!!!!!!!!!!!!!!!!!!!
                   goToReview(dto,udto,"test");

                }
            }
        });
    }

    public void goToReview(final ReviewDto rdto , final UsersDto udto, String category){

        db = FirebaseFirestore.getInstance();
        db.collection("category")
                .document(category)
                .collection("review")
                .document(rdto.getReview_UID())
                .collection("comment")
             //   .whereEqualTo("review_uid",rdto.getReview_UID())
                .orderBy("create_time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<CommentDto> list = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Map<String, Object> map = documentSnapshot.getData();
                                CommentDto dto = new CommentDto(String.valueOf(map.get("review_uid")), String.valueOf(map.get("comment_nickname")), String.valueOf(map.get("comment"))
                                , (ArrayList<String>)map.get("recommendList"), String.valueOf(map.get("create_time")));
                                dto.setComment_uid(documentSnapshot.getId());
                                list.add(dto);
                            }
                            Fragment fragment =  new ReviewReadPageFragment(rdto,udto,list);
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }
                });

    }

    public void delete(final CommentDto cdto , final ReviewDto rdto , final UsersDto udto, final String category){
        db = FirebaseFirestore.getInstance();
        db.collection("category")
                .document(category)
                .collection("review")
                .document(rdto.getReview_UID())
                .collection("comment")
                .document(cdto.getComment_uid())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(mcontext,"댓글 삭제가 완료 되었습니다!", Toast.LENGTH_SHORT).show();
                    goToReview(rdto,udto,category);
                }
            }
        });

    }
    public void setMyCommentList(){

    }
}
