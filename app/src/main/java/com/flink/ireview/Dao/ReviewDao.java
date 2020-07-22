package com.flink.ireview.Dao;

import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.Dto.CommentDto;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.ui.Category.fragment_category;
import com.flink.ireview.ui.review.ReviewReadPageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewDao {
    Context mcontext ;
    private Member member;
    FragmentTransaction fragmentTransaction;
    Map<String, ArrayList<ReviewDto>> allReview ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ReviewDto dto;
    UsersDto udto;
    boolean click;
    String category;
    int i ;

    public ReviewDto getDto() {
        return dto;
    }

    public void setDto(ReviewDto dto) {
        this.dto = dto;
    }

    public UsersDto getUdto() {
        return udto;
    }

    public void setUdto(UsersDto udto) {
        this.udto = udto;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    ArrayList<ReviewDto> list;

    public ReviewDao(Context mcontext, FragmentTransaction fragmentTransaction) {
        this.mcontext = mcontext;
        this.fragmentTransaction = fragmentTransaction;
    }

    public void write(final ReviewDto rdto, String cagtegory, final UsersDto udto){
        db.collection("category")
                .document(cagtegory)
                .collection("review")
                .add(rdto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(mcontext,"작성을 완료 되었습니다", Toast.LENGTH_SHORT).show();
                        System.out.println(documentReference.getId());
                        rdto.setReview_UID(documentReference.getId());  // <- 해당 게시글의 아이디를 dto에 저장함 실제 디비에는 저장되지 않음 이거 정말 중요함
                        Fragment fragment = new ReviewReadPageFragment(rdto,udto,new ArrayList<CommentDto>());
                        fragmentTransaction.replace(R.id.main_frame,fragment).addToBackStack(null).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"작성을 실패 했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void modify(ReviewDto rdto){
        db.collection("category")
                .document("test")
                .collection("review")
                .document(rdto.getReview_UID())
                .update(
                        "review_main_string",rdto.getReview_main_string(),
                        "review_main_title" , rdto.getReview_main_title(),
                        "review_main_advantage",rdto.getReview_main_advantage(),
                        "review_main_weakness",rdto.getReview_main_weakness()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mcontext,"수정을 완료 되었습니다", Toast.LENGTH_SHORT).show();
                Fragment fragment = new fragment_category(member);
                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                // 다음은 일단 보류
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"수정을 살패 했습니다 ", Toast.LENGTH_SHORT).show();
                // 일단 보류
            }
        });
    }
    public void delete(ReviewDto rdto){
        db.collection("category")
                .document("test")
                .collection("review")
                .document(rdto.getReview_UID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mcontext,"삭제가 완료 되었습니다", Toast.LENGTH_SHORT).show();
                Fragment fragment = new fragment_category(member);
                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                // 삭제 후 리뷰 리스트 로 이동!!
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"삭제를 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void increaseViewCount(ReviewDto rdto){
        db .collection("review").document()
                .update(

                        "review_view_number",rdto.getReview_view_number()+1
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //READ 페이지로 넘어가는 걸로
                //
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"예상치 못한 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAllReviewList(String category){
        // 카테고리를 찾아서 저장함 그래서 카테고리를 파라미터로 받아야함
        db.collection("collection")
                .document(category)
                .collection("review")
                .orderBy("recommend_count")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            list = new ArrayList<>();
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                Map<String, Object> map = document.getData();

                            }
                        }else{
                            //에러 처리 부분
                        }
                    }
                });
    }
    public void recommendListUpdate(){
        // 좋아요 증가!!
        // click 에 따라서 좋아요 를 하는 것인지 좋아요 취소를 하는 것인지 알 수 있다

        db.collection("category").
                document(category)
                .collection("review")
                .document(dto.getReview_UID())
                .update("recommend_list",dto.getRecommend_list())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    if(click==true){
                        Map<String,String> map =  new HashMap<>();
                        map.put(category,dto.getReview_UID());
                        udto.getMy_recommend_list().add(map);
                        UsersDao dao = new UsersDao(mcontext,fragmentTransaction);
                        dao.setMyRecommendList(udto);

                    }else{
//                        udto.getMy_recommend_list().remove(i);
                        UsersDao dao = new UsersDao(mcontext,fragmentTransaction);
                        dao.setMyRecommendList(udto);
                    }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"예상치 못한 오류가 발생했습니다", Toast.LENGTH_SHORT).show();
                UsersDao dao  = new UsersDao(mcontext,fragmentTransaction);
              //  dao.setMyRecommendList(udto,);
            }
        });
    }
    public void GotoReview(){

    }


}
