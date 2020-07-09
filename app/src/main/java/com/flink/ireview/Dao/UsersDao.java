package com.flink.ireview.Dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.ui.Main.MainFragment;
import com.flink.ireview.ui.MyPage.MyPageFragment;
import com.flink.ireview.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UsersDao {

    private Context mcontext;

    private  FragmentTransaction fragmentTransaction ;

    private UsersDto usersDto ;

    private FirebaseUser user ;

    private FirebaseAuth mauth ;



    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    public UsersDao(Context mcontext, FragmentTransaction fragmentTransaction) {
        this.mcontext = mcontext;
        this.fragmentTransaction = fragmentTransaction;
    }

    public UsersDto getUsersDto() {
        return usersDto;
    }

    public void setUsersDto(UsersDto usersDto) {
        this.usersDto = usersDto;
    }

    public void login(final String email , String password){
        mauth = FirebaseAuth.getInstance();
        mauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mauth.getCurrentUser();

                            Toast.makeText(mcontext, "로그인 완료", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new MainFragment();

                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();

                        } else {
                            Toast.makeText(mcontext, "아이디 또는 비밀번호가 맞지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void join(String email , final String password){
        // activity 에서 password 와 repassword가 같은지 다른지 먼저 확인하고 다를 시 다시 입력하도록 함
        mauth = FirebaseAuth.getInstance();
        mauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            JoinInfo(usersDto);
                        }else{
                            Toast.makeText(mcontext,"실패함 가입", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void JoinInfo(UsersDto usersDto){

        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users")
                .document(usersDto.getUsers_email()).set(usersDto)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(mcontext,"회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new LoginFragment();
                            fragmentTransaction.addToBackStack(null);
                            //fragmentTransaction.
                            fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }else{
                            Toast.makeText(mcontext,"회원가입이 실패했습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void ReadInfo(String email){
        db.collection("users").document(email)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usersDto = documentSnapshot.toObject(UsersDto.class);
                Fragment fragment = new MyPageFragment(usersDto);  // 여기서 유저디티오 를 파라미터로 전송해줘야함!!!!!!!!\
               // Fragment fragment = new MyPageFragment();
                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                //fragmentTransaction.setPrimaryNavigationFragment(fragment).commit();
             //  fragmentTransaction.replace(R.id.nav_host_fragment,fragment).commit();
            }

        });
        // 마이페이지로 가는 함수!!
    }

    public void passwordEmailSend(String email){
        // 인증 후 인증이 완료 됬을 시 해당 입력한 이메일을 보내 거기서 비밀번호 수정 할 수 있도록 함
        if(email.length()>0){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            Task<Void> voidTask = auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mcontext,"메일을 보냈습니다 수정후 로그인 해주세요" , Toast.LENGTH_SHORT).show();
                                Fragment fragment = new LoginFragment();
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                            }else{
                                Toast.makeText(mcontext,"가입되어있지않은 이메일 입니다" , Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }else{
            Toast.makeText(mcontext,"이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }
    public void modifyMyInfo(UsersDto dto){
        // control -> 0은 마이페이지 , 1은 화면전환x
        usersDto =dto;
        String name = dto.getUsers_name();
        String nickname = dto.getUsers_nickname();
        String phone = dto.getUsers_phone_number();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users")
                .document(user.getEmail()).update(
                "name",name ,
                "users_phone_number",phone,
                "users_nickname",nickname
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("수정");
                Toast.makeText(mcontext,"수정이 완료되었습니다", Toast.LENGTH_SHORT).show();
                fragmentTransaction.addToBackStack(null);
                Fragment fragment = new MyPageFragment(usersDto); // 파라미터 있을 수 있음 수정 요망
                fragmentTransaction.addToBackStack(null).replace(R.id.main_frame,fragment).commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mcontext,"예상치 못한 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void increaseReliability(UsersDto dto){
        db.collection("users")
                .document(user.getEmail())
                .update(
                        "users_reliability",dto.getUsers_reliability()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 일단 보류
            }
        });
    }
    public void myRecommendReviewUpdate(ReviewDto dto){
        // 사용자가 어떤 리뷰를 추천을 눌렀을 경우 실행!!!!
        db.collection("users")
                .document(user.getEmail()).collection("myRecommendReview")
                .add(dto).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){

                    Toast.makeText(mcontext,"추천하였습니다", Toast.LENGTH_SHORT).show();
                    // 보류
                }
            }
        });
    }

    public void myRecommendReviewList(){
        // 내가 추천한 리뷰 리스트를 가지고 오는 매소드

        db.collection("users").document(user.getEmail())
                .collection("myRecommendReview")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<ReviewDto> list  = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Map<String, Object> map = documentSnapshot.getData();
                    //일단 보류 !! 리뷰 디티오 생성자 값 수정 하고 나서 진행
                    //ReviewDto dto = new ReviewDto(map.get);
                    // list.add(dto);
                    //

                }
            }
        });
    }
    public void myReviewList(){
        // 애초에 리뷰를 어떤 형식으로 저장할지 정하고 수정사항 생길 수 있음
        db.collection("review").document(user.getEmail())
                .collection("myReview").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot  : task.getResult()){
                                Map<String, Object> map =  queryDocumentSnapshot.getData();
                                //일단 보류 !! 리뷰 디티오 생성자 값 수정 하고 나서 진행
                                //ReviewDto dto = new ReviewDto(map.get);
                                // list.add(dto);
                            }
                        }
                    }
                });
    }
    public void setReviewList(){

    }
    public void logOut(){

    }

    //푸시알람을 위해 본인의 토큰을 같이 저장해줌!
    private void sendPushTokenToDB(final UsersDto usersDto2) {
        //파이어베이스
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Map<String, Object> map = new HashMap<>();
                        map.put("fcmToken", token);
                        //FirebaseHelper.db.collection("Users").document(FirebaseHelper.mUid).collection("FcmToken").document(FirebaseHelper.mUid).set(map);
                        db.collection("users")
                                .document(usersDto2.getUsers_email()).set(map);
                    }
                });
    }
///////수정////////////////////////////////////////////////////
public void setMyRecommendList(UsersDto udto){
        user = FirebaseAuth.getInstance().getCurrentUser();
  db.collection("users")
  .document(user.getEmail())
  .update(
          "my_recommend_list",udto.getMy_recommend_list()

  )
  .addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
      }
  });
}

}
