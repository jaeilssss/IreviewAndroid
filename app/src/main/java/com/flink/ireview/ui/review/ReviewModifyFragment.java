package com.flink.ireview.ui.review;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.ReviewWriteImageAdapter;
import com.flink.ireview.http.board.reviewModifyHttp;
import com.flink.ireview.http.board.reviewWriteHttp;
import com.flink.ireview.ui.Category.fragment_category;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class ReviewModifyFragment extends Fragment {
    private Member member;
    ImageButton imgAdd;
    private ArrayList<String> imageAddress;
    private File tempFile;
    private FragmentTransaction fragmentTransaction ;

    Uri photoUri;
    private int newCount;
    private int arr[];
    private Boolean isPermission = true;
    private String fileSource;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    ReviewDto dto;
    int realCount;
    View view;
    ImageView imageView;
    Button modify_submit , modify_exit;
    EditText title , content , advantage , weakenss ;
    ReviewDao dao;
    Board board;
    ArrayList<String> image = new ArrayList<>();
    int count;
    boolean check;

    public ReviewModifyFragment(Member member, Board board) {
        this.member = member;
        this.board = board;
        check = false;
    }
    public ReviewModifyFragment(Member member, Board board,boolean check) {
        this.member = member;
        this.board = board;
       this.check = check;
    }

    public ReviewModifyFragment() {
        // Required empty public constructor
    }

    public ReviewModifyFragment(ReviewDto dto){
        this.dto = dto;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_review_modify, container, false);
        fragmentTransaction = getFragmentManager().beginTransaction();
        arr = new int[8];
        newCount = 0;

        count = 0;
        imgAdd = view.findViewById(R.id.review_image_insert);
        dao = new ReviewDao(getContext(),getFragmentManager().beginTransaction());
        title = view.findViewById(R.id.notice_modify_title);
        content = view.findViewById(R.id.notice_modify_content);
        modify_submit = view.findViewById(R.id.modify_submit);
        // 사진이 여러개 이므로 원래는 반복문으로 코드 작성 해줘야함 !!!
        title.setText(board.getTitle());
        content.setText(board.getContentString());
        imgAdd.setOnClickListener(onClickListener);
        // 장점 , 단점 도 반복문 으로 해줘야 함!!!
        modify_submit.setOnClickListener(onClickListener);
        imageAddress  = new ArrayList<>();
        if(!board.getImage1().equals("null")){
            image.add(board.getImage1());
            imageAddress.add(board.getImage1());
            count++;
        }else {
            imageAddress.add("null");
        }
        if(!board.getImage2().equals("null")){
            count++;
            image.add(board.getImage2());
            imageAddress.add(board.getImage2());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage3().equals("null")){
            count++;
            image.add(board.getImage3());
            imageAddress.add(board.getImage3());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage4().equals("null")){
            count++;
            image.add(board.getImage4());
            imageAddress.add(board.getImage4());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage5().equals("null")){
            count++;
            image.add(board.getImage5());
            imageAddress.add(board.getImage5());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage6().equals("null")){
            count++;
            image.add(board.getImage6());
            imageAddress.add(board.getImage6());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage7().equals("null")){
            count++;
            image.add(board.getImage7());
            imageAddress.add(board.getImage7());
        }
        else {
            imageAddress.add("null");
        }
        if(!board.getImage8().equals("null")){
            count++;
            image.add(board.getImage8());
            imageAddress.add(board.getImage8());
        }else {
            imageAddress.add("null");
        }
        realCount = count;
        System.out.println("사이즈!!!!!"+image.size());
        setRecyclerViewImage(image);

        return view;
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.review_image_insert :
                    if(count==8){
                        Toast.makeText(getContext(),"최대 8장까지 업로드 할 수 있습니다",Toast.LENGTH_SHORT).show();

                    }if (isPermission) {
                    goToAlbum();
                } else
                    Toast.makeText(getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                    break;
                case R.id.modify_submit :
                    if(check==true){
                        //이미지 가 추가 되거나 삭제 됬다는 것을 의미
                        uploadImage(0);
                    }else{
                        reviewModifyHttp http = new reviewModifyHttp();
                        http.setBodyContents(board.getId(),board.getUserId(),board.getTitle(),board.getContentString(),board.getCategoryId(),
                                board.getUserAccount(),board.getUserNickname(),imageAddress.get(0),imageAddress.get(1),imageAddress.get(2),
                                imageAddress.get(3),imageAddress.get(4), imageAddress.get(5),imageAddress.get(6),imageAddress.get(7),board.getCreatedDate(),board.getTotalView(),
                                board.getTotalComment(),board.getTotalRecommend(),board.getScrapCount());
                        board = http.send();
                        if(board!=null){
                            Toast.makeText(getContext(), "작성이 완료 되었습니다!", Toast.LENGTH_SHORT).show();
                            Member wmember = new Member();
                            wmember.setNickName(member.getNickName());
                            wmember.setId(member.getId());
                            wmember.setAccount(member.getAccount());
                            wmember.setSumNailImage(member.getSumNailImage());
                            Fragment fragment =new ReviewReadPageFragment(member,board,wmember,0);
                            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }

                    }
                    break;
            }
        }
    };
    public void setRecyclerViewImage(ArrayList<String> imageList){
        RecyclerView recyclerView = view.findViewById(R.id.rv_modify_image);
        ReviewWriteImageAdapter adapter = new ReviewWriteImageAdapter(getContext(),imageList,fragmentTransaction,board,member,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
    }
    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

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

    /**
     * tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private void setImage() {
//        ImageView imageView = root.findViewById(R.id.imageview);
        BitmapFactory.Options options = new BitmapFactory.Options();
        fileSource = tempFile.getAbsolutePath();
        check = true;
//        imageList.add(index++, fileSource);
        image.add(count++,fileSource);
        System.out.println("사이즈!sdfsdesdsdsd!"+image.size());
        arr[newCount++] = count-1;//        Bitmap originalBm = BitmapFactory.decodeFile(image.get(count-1), options);
        //  Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
//        imageView.setImageBitmap(originalBm);
        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */

        setRecyclerViewImage(image);
        count++;
        tempFile = null;

    }
    private String uploadImage(int i) {

        final int temp = i+1;

        try {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            Uri file = Uri.fromFile(new File(image.get(arr[i])));
            final StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
            InputStream stream = new FileInputStream(new File(image.get(arr[i])));
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
                    imageAddress.add(realCount++,task.getResult().toString());
                        if(temp==newCount){
                        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분");
                        reviewModifyHttp http = new reviewModifyHttp();
                        http.setBodyContents(board.getId(),board.getUserId(),board.getTitle(),board.getContentString(),board.getCategoryId(),
                                board.getUserAccount(),board.getUserNickname(),imageAddress.get(0),imageAddress.get(1),imageAddress.get(2),
                                imageAddress.get(3),imageAddress.get(4), imageAddress.get(5),imageAddress.get(6),imageAddress.get(7),board.getCreatedDate(),board.getTotalView()
                        ,board.getTotalComment(),board.getTotalRecommend(),board.getScrapCount());
                        board = http.send();
                            if(board!=null){
                                Toast.makeText(getContext(), "작성이 완료 되었습니다!", Toast.LENGTH_SHORT).show();
                            Member wmember = new Member();
                            wmember.setNickName(member.getNickName());
                            wmember.setId(member.getId());
                            wmember.setAccount(member.getAccount());
                            wmember.setSumNailImage(member.getSumNailImage());
                            Fragment fragment =new ReviewReadPageFragment(member,board,member,0);
                            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_frame,fragment).commit();
                        }
                    }else{
                        uploadImage(temp);
                    }
                }
            });
        } catch (Exception e) {
            return "error";
        }

        return "ok";
    }
}
