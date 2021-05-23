package com.flink.ireview.ui.MyPage;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.http.User.MyInfoModifyHttp;
import com.flink.ireview.http.board.reviewWriteHttp;
import com.flink.ireview.sign_up.fragment_signup_category;
import com.flink.ireview.ui.review.ReviewReadPageFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageModifyFragment extends Fragment {
    View view;
    Uri photoUri;
    private int check;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private String myImageSource;
    private File tempFile;
    EditText modify_name, modify_nickname, modify_phone, check_password;
    Button modify, exit;
    UsersDto current_dto;
    private String fileSource;
    private String myImageAddress;
    private Boolean isPermission = true;
    Member member;
    private ImageView myImage;

    Button prifile_image_button ,interestModify;

    public MyPageModifyFragment(Member member) {
        this.member = member;
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MyPageModifyFragment() {
        // Required empty public constructor
    }

    public MyPageModifyFragment(UsersDto current_dto) {
        this.current_dto = current_dto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage_modify, container, false);
        interestModify  = view.findViewById(R.id.mypage_modify_interest_modify_button);
        check = 0;
        myImage = view.findViewById(R.id.my_image);
        modify_name = view.findViewById(R.id.mypage_modify_my_name);
        modify_name.setText(member.getName());
        modify_nickname = view.findViewById(R.id.mypage_modify_my_nickname);
        modify_nickname.setText(member.getNickName());
        modify_phone = view.findViewById(R.id.mypage_modify_my_phonenumber);
        modify_phone.setText(member.getPhoneNumber());
        modify = view.findViewById(R.id.mypage_modify_button);
        modify.setOnClickListener(onClickListener);
        exit = view.findViewById(R.id.mypage_modify_exit);
        prifile_image_button = view.findViewById(R.id.profile_image_modify_button);
        prifile_image_button.setOnClickListener(onClickListener);

        ImageView [] imageViews = new ImageView[5];
        imageViews[0] = view.findViewById(R.id.mypage_modify_interest1);
        imageViews[1] = view.findViewById(R.id.mypage_modify_interest2);
        imageViews[2] = view.findViewById(R.id.mypage_modify_interest3);
        imageViews[3] = view.findViewById(R.id.mypage_modify_interest4);
        imageViews[4] = view.findViewById(R.id.mypage_modify_interest5);
        setInterestIcon(imageViews);
        if(!member.getSumNailImage().equals("null")){
            Glide.with(getContext()).load(member.getSumNailImage()).into(myImage);
        }
        interestModify.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mypage_modify_button:
                    if(check==1) {
                        String resultCode = uploadImage();
                        if (resultCode.equals("error")) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        } else {
                            member.setSumNailImage(myImageAddress);
                        }

                    }else{
                        modify();
                    }
                    break;
                case R.id.mypage_modify_exit:
                    Fragment fragment = new MyPageFragment(member);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame, fragment).commit();
                    break;
                case R.id.profile_image_modify_button :
                    tedPermission();
                    if (isPermission) {
                        goToAlbum();
                    } else
                        Toast.makeText(getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                    break;
                case R.id.mypage_modify_interest_modify_button:
                    Fragment fragment1 = new fragment_signup_category(member,1);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment1).commit();
                    break;
            }
        }
    };

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        //  Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            photoUri = data.getData();
            //Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                if (getActivity().getContentResolver() == null) {
                    System.out.println("");
                }
                cursor = getActivity().getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
                System.out.println("tempFile Uri : " + Uri.fromFile(tempFile));


            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }
    }
    private void setImage() {

//        ImageView imageView = root.findViewById(R.id.imageview);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        //  Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        myImage.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        System.out.println("setImage : " + tempFile.getAbsolutePath());
        fileSource = tempFile.getAbsolutePath();
        myImageSource = fileSource;
        check++;
        tempFile = null;

    }
    private String uploadImage() {


        try {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            Uri file = Uri.fromFile(new File(myImageSource));
            final StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
            InputStream stream = new FileInputStream(new File(myImageSource));
            final UploadTask uploadTask = riversRef.putStream(stream);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    System.out.println("리턴" + riversRef.getDownloadUrl());
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                         myImageAddress = task.getResult().toString();
                         System.out.println(myImageAddress);
                         member.setSumNailImage(myImageAddress);
                         modify();
                }
            });
        } catch (Exception e) {
            return "error";
        }

        return "ok";
    }
    public void modify(){
        String name = modify_name.getText().toString();
//                    String password = check_password.getText().toString();
        String nickname = modify_nickname.getText().toString();
        String phone = modify_phone.getText().toString();
        MyInfoModifyHttp http = new MyInfoModifyHttp();
        http.setBodyContents(member.getId(), member.getAccount(), member.getPassword(), member.getEmail(), name, nickname, phone
                , "11022-222-33", member.getYear(), member.getMonth(), member.getDate(), member.getGender(), member.getStatus(), String.valueOf(member.getInterest1()), String.valueOf(member.getInterest2())
                , String.valueOf(member.getInterest3()), String.valueOf(member.getInterest4()), String.valueOf(member.getInterest5()),member.getSumNailImage());
        Member newMember = http.send();
        if (newMember != null) {
            Toast.makeText(getContext(), "회원정보 수정이 완료 되었습니다", Toast.LENGTH_SHORT).show();
            Fragment fragment = new MyPageFragment(newMember);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame, fragment).commit();
        }
    }

    public void setInterestIcon(ImageView[] imageViews){
        int [] array = new int[5];
        array[0]= member.getInterest1();
        array[1] = member.getInterest2();
        array[2] = member.getInterest3();
        array[3] = member.getInterest4();
        array[4] = member.getInterest5();
        for(int i=0 ; i<5; i++){
            if(array[i]==0){
                imageViews[i].setImageResource(R.drawable.c_fashion);
            }else if(array[i]==1){
                imageViews[i].setImageResource(R.drawable.c_health);
            }else if(array[i]==2){
                imageViews[i].setImageResource(R.drawable.c_beauty);
            }else if(array[i]==3){
                imageViews[i].setImageResource(R.drawable.c_culture);
            }else if(array[i]==4){
                imageViews[i].setImageResource(R.drawable.c_lifestyle);
            }else if(array[i]==5){
                imageViews[i].setImageResource(R.drawable.c_education);
            }else if(array[i]==6){
                imageViews[i].setImageResource(R.drawable.c_interial);
            }else if(array[i]==7){
                imageViews[i].setImageResource(R.drawable.c_book);
            }else if(array[i]==8){
                imageViews[i].setImageResource(R.drawable.c_appliances);
            }else if(array[i]==9){
                imageViews[i].setImageResource(R.drawable.c_kids);
            }else if(array[i]==10){
                imageViews[i].setImageResource(R.drawable.c_it);
            }else if(array[i]==11){
                imageViews[i].setImageResource(R.drawable.c_pet);
            }else if(array[i]==12){
                imageViews[i].setImageResource(R.drawable.c_vehicle);
            }else if(array[i]==13){
                imageViews[i].setImageResource(R.drawable.c_hobby);
            }else if(array[i]==14){
                imageViews[i].setImageResource(R.drawable.c_sport);
            }else if(array[i]==15){
                imageViews[i].setImageResource(R.drawable.c_music);
            }else if(array[i]==16){
                imageViews[i].setImageResource(R.drawable.c_travel);
            }
        }
    }
}