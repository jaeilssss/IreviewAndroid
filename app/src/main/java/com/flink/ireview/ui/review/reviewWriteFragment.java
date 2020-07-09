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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.flink.ireview.Dao.ReviewDao;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.R;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class reviewWriteFragment extends Fragment {

    private reviewWriteViewModel reviewWriteViewModel;
    private Boolean isPermission = true;
    private UsersDto dto;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
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

    public reviewWriteFragment(UsersDto dto) {
        this.dto = dto;
    }

    public reviewWriteFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_review_write_page, container, false);
        reviewWriteViewModel =
                ViewModelProviders.of(this).get(reviewWriteViewModel.class);
        storage = FirebaseStorage.getInstance();
        tedPermission();
        final TextView textView = root.findViewById(R.id.text_review_write);
        reviewWriteViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        imageButton = root.findViewById(R.id.review_image_insert);
        imageButton.setOnClickListener(onClickListener);
        submit = root.findViewById(R.id.write_submit);
        submit.setOnClickListener(onClickListener);
        notice_write_title = root.findViewById(R.id.notice_write_title);
        notice_write_content = root.findViewById(R.id.notice_write_content);
        advantage_point = root.findViewById(R.id.advantage_point);
        weakness_point = root.findViewById(R.id.weakness_point);

        imageCount = 0;
        return  root;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.review_image_insert:
                    if (isPermission) {
                        goToAlbum();
                    } else
                        Toast.makeText(getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
                    imageCount = +1;
                    break;
                case R.id.write_submit:
                    title = notice_write_title.getText().toString();
                    content = notice_write_content.getText().toString();
                    advantage = advantage_point.getText().toString();
                    weakness = weakness_point.getText().toString();
                    if (title.length() == 0) {
                        Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else if (content.length() == 0) {
                        Toast.makeText(getContext(), "본문을 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else if (imageCount == 0) {
                        Toast.makeText(getContext(), "사진을 최소 한장 이상 올려주세요", Toast.LENGTH_SHORT).show();
                    } else if (advantage.length() == 0) {
                        Toast.makeText(getContext(), "장점 하나 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else if (weakness.length() == 0) {
                        Toast.makeText(getContext(), "단점 하나 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                      StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        Uri file = Uri.fromFile(new File(fileSource));
                        final StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
//                        final UploadTask uploadTask = riversRef.putFile(file);
//                        System.out.println(riversRef.getDownloadUrl());
                        try{
                            InputStream stream = new FileInputStream(new File(fileSource));
                            UploadTask uploadTask = riversRef.putStream(stream);
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
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
                                    Date time = new Date();
                                    String date = format.format(time);
                                    Uri downloadUri = task.getResult();
                                    String uri = downloadUri.toString();
                                    ArrayList<String> list = new ArrayList<>();
                                    list.add(uri);
                                    ArrayList<String> advantageList = new ArrayList<>();
                                    advantageList.add(advantage);
                                    ArrayList<String> weaknessList = new ArrayList<>();
                                    weaknessList.add(weakness);
                                    ReviewDto rdto = new ReviewDto(dto.getUsers_nickname(),user.getUid(), date, "test", title, content, list, advantageList, weaknessList, new ArrayList<String>()
                                            , 0, 0, new ArrayList<String>(),new ArrayList<String>());
                                    ReviewDao dao = new ReviewDao(getContext() , getFragmentManager().beginTransaction());
                                    dao.write(rdto,"test",dto);
                                }
                            });
                        }catch (Exception e){

                        }
// Register observers to listen for when the download is done or if it fails
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

        ImageView imageView = root.findViewById(R.id.imageview);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        //  Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());


        imageView.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        System.out.println("setImage : " + tempFile.getAbsolutePath());
        fileSource = tempFile.getAbsolutePath();
        tempFile = null;

    }
}

