package com.flink.ireview.ui.review;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.ReviewWriteImageAdapter;
import com.flink.ireview.http.board.reviewWriteHttp;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class reviewWriteFragment extends Fragment {

    private reviewWriteViewModel reviewWriteViewModel;
    private Boolean isPermission = true;
    private UsersDto dto;
    private FragmentTransaction fragmentTransaction ;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private EditText review_write_produce_name;
    private ArrayList<String> imageAddress;
    private String productName;
    private File tempFile;
   private ArrayList<String> pictureAddress;
    private String fileSource;
    private ImageButton imageButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    private String title, content, advantage, weakness;
    private int imageCount;
    private Button submit;
    private EditText notice_write_title, notice_write_content, advantage_point, weakness_point;
    Uri photoUri;
    View root;
    private Board board;
    private Member member;
    private int categoryId;
    private int index;
    private int modify;
    private ArrayList<String> imageList;
    private Context context;
    private int count;
    private ArrayList<Integer> num;
    Spinner categorySpiner;
    String categoryItem;

    public reviewWriteFragment(Member member, int categoryId) {
        this.member = member;
        this.categoryId = categoryId;
        board = null;
    }

    public reviewWriteFragment(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

    public reviewWriteFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        modify = 0;
        root = inflater.inflate(R.layout.fragment_review_write_page, container, false);
        review_write_produce_name = root.findViewById(R.id.review_write_produce_name);
                pictureAddress  = new ArrayList<>();
        imageList= new ArrayList<>();
        categorySpiner = root.findViewById(R.id.Spinner_review_write_category);
        reviewWriteViewModel =
                ViewModelProviders.of(this).get(reviewWriteViewModel.class);
        imageAddress = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            imageAddress.add(i, "null");
        }
        context = getContext();
//        imageList = setImageList();
        fragmentTransaction = getFragmentManager().beginTransaction();
                index = 0;
        storage = FirebaseStorage.getInstance();
        tedPermission();
        final TextView textView = root.findViewById(R.id.text_review_write);
        imageButton = root.findViewById(R.id.review_image_insert);
        imageButton.setOnClickListener(onClickListener);
        submit = root.findViewById(R.id.write_submit);
        submit.setOnClickListener(onClickListener);
        notice_write_title = root.findViewById(R.id.notice_write_title);
        notice_write_content = root.findViewById(R.id.notice_write_content);
//        advantage_point = root.findViewById(R.id.advantage_point);
//        weakness_point = root.findViewById(R.id.weakness_point);

        categorySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imageCount = 0;
        if(board!=null){
            modify=1;
            notice_write_content.setText(board.getContentString());
            notice_write_title.setText(board.getTitle());
            if(!board.getImage1().equals("null")){
                count++;
                imageList.add(0,board.getImage1());
            }
            if(!board.getImage2().equals("null")){
                count++;

                imageList.add(1,board.getImage2());
            }
            if(!board.getImage3().equals("null")){
                count++;

                imageList.add(2,board.getImage3());
            }
            if(!board.getImage4().equals("null")){
                count++;

                imageList.add(3,board.getImage4());
            }
            if(!board.getImage5().equals("null")){
                count++;

                imageList.add(4,board.getImage5());
            }
            if(!board.getImage6().equals("null")){
                count++;

                imageList.add(5,board.getImage6());
            }
            if(!board.getImage7().equals("null")){
                count++;

                imageList.add(6,board.getImage7());
            }
            if(!board.getImage8().equals("null")){
                count++;
                imageList.add(7,board.getImage8());
            }
            setRecyclerViewImage(imageList);
        }
        return root;
    }

    public ArrayList<String> setImageList() {
        ArrayList<String> list = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            list.add(i, "null");
        }

        setRecyclerViewImage(list);
        count = 0;
        return list;

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.review_image_insert:
                    if(count==8){
                        Toast.makeText(getContext(),"더 이상 사진을 업로드 할 수 없습니다 (최대 8개)!!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (isPermission) {
                            goToAlbum();
                    } else
                        Toast.makeText(getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                    imageCount = +1;
                    break;
                case R.id.write_submit:
                    title = notice_write_title.getText().toString();
                    content = notice_write_content.getText().toString();
//                    advantage = advantage_point.getText().toString();
//                    weakness = weakness_point.getText().toString();
                    categoryItem = categorySpiner.getSelectedItem().toString();
                    productName = review_write_produce_name.getText().toString();
                    if (title.length() == 0) {
                        Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else if (content.length() == 0) {
                        Toast.makeText(getContext(), "본문을 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else if (imageCount == 0) {
                        Toast.makeText(getContext(), "사진을 최소 한장 이상 올려주세요", Toast.LENGTH_SHORT).show();
//                    } else if (advantage.length() == 0) {
//                        Toast.makeText(getContext(), "장점 하나 이상 입력해주세요", Toast.LENGTH_SHORT).show();
//                    } else if (weakness.length() == 0) {
//                        Toast.makeText(getContext(), "단점 하나 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    }else if(productName.length()==0){
                        Toast.makeText(getContext(), "제품 혹은 리뷰 대상 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }else if(categoryItem.equals("category")){
                        Toast.makeText(getContext(), "해당 리뷰의 카테고리를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (uploadImage(0).equals("ok")) {
                            System.out.println("ok");
                        }
                        ArrayList<String> advantageList = new ArrayList<>();
                        advantageList.add(advantage);
                        ArrayList<String> weaknessList = new ArrayList<>();
                        weaknessList.add(weakness);
                        break;
                    }
            }

        }
    };

    /**
     * 폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 파일 주소 ( blackJin )
        File storageDir = new File(getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
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
index = count;
//        ImageView imageView = root.findViewById(R.id.imageview);
        BitmapFactory.Options options = new BitmapFactory.Options();
        fileSource = tempFile.getAbsolutePath();
//        imageList.add(index++, fileSource);
        imageList.add(index,fileSource);
        index++;
        Bitmap originalBm = BitmapFactory.decodeFile(imageList.get(index-1), options);
        //  Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
//        imageView.setImageBitmap(originalBm);
        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */

        System.out.println("일단 이미지 업로드 하기전 !"+imageList.get(0));
        setRecyclerViewImage(imageList);
        count++;
        tempFile = null;

    }

    private String uploadImage(int i) {


         final int temp = i+1;
         System.out.println(imageList.get(0));

            try {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                Uri file = Uri.fromFile(new File(imageList.get(i)));
                final StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
                InputStream stream = new FileInputStream(new File(imageList.get(i)));
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

                        imageAddress.add(temp-1,task.getResult().toString());
                        System.out.println("업로드 후 :!!!"+ imageAddress.get(temp-1));

                        if(imageList.size()==temp){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분");
                            Date time = new Date();
                            String date = format.format(time);
                            reviewWriteHttp http = new reviewWriteHttp();
                            categoryId = getCategoryNumber();
                            http.setBodyContents(member.getId(), title, content, categoryId, member.getAccount(), member.getNickName(), imageAddress.get(0), imageAddress.get(1), imageAddress.get(2), imageAddress.get(3),
                                    imageAddress.get(4),imageAddress.get(5), imageAddress.get(6), imageAddress.get(7), date,productName);
                            Board board = http.send();
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
/*
* 밑에 여기 제품명이랑 장단점도 추가해야함!!!
* */
public void setRecyclerViewImage(ArrayList<String> imageList){
        System.out.println(imageList.size());
    RecyclerView recyclerView = root.findViewById(R.id.rv_insert_image);
    title = notice_write_title.getText().toString();
    content = notice_write_content.getText().toString();
    board=new Board();
    board.setTitle(title);
    board.setContentString(content);
    ReviewWriteImageAdapter adapter = new ReviewWriteImageAdapter(context,imageList,fragmentTransaction,board,member,0);
    recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
    recyclerView.setAdapter(adapter);
}

public int getCategoryNumber(){
    int num = 0;
    switch(categoryItem){
        case "패션" :
            num = 0;
            break;
        case "의료" :
            num=1;
            break;
        case "뷰티" :
            num =2;
            break;
        case "문화" :
            num = 3;
            break;
        case "생활용품" :
            num=4;
            break;
        case "교육" :
            num=5;
            break;
        case "인테리어" :
            num=6;
            break;
        case "도서" :
            num = 7;
            break;
        case "가전제품" :
            num = 8;
            break;
        case "유아용품" :
            num=9;
            break;
        case "IT" :
            num=10;
            break;
        case "반려용품" :
            num=11;
            break;
        case "차량/오토바이" :
            num=12;
            break;
        case "취미" :
             num = 13;
             break;
        case "스포츠/레저" :
            num = 14;
            break;
        case "악기" :
            num=15;
            break;
        case "여행" :
            num = 16;
            break;

    }
    return num;
}

}

