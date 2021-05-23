package com.flink.ireview.ui.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flink.ireview.Dto.Member;
import com.flink.ireview.Dto.ReviewDto;
import com.flink.ireview.Dto.UsersDto;
import com.flink.ireview.Event_ViewPagerAdapter;
import com.flink.ireview.Frag2;
import com.flink.ireview.FragEvent1;
import com.flink.ireview.FragEvent2;
import com.flink.ireview.FragEvent3;
import com.flink.ireview.RecyclerView.CustomAdapter;
import com.flink.ireview.RecyclerView.PostItem;
import com.flink.ireview.R;
import com.flink.ireview.ReviewRecycleView.ReviewAdapter;
import com.flink.ireview.interfaces.goToNewFrag;
import com.flink.ireview.ui.LatestViewd.fragment_latest_viewd;
import com.flink.ireview.ui.fragment_search;
import com.flink.ireview.viewPager.MainReviewSetViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainFragment extends Fragment {

    private Event_ViewPagerAdapter event_viewPagerAdapter;

    private MainViewModel mainViewModel;
    private TextView search_text;
    private Button search_button;
    RecyclerView rvList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ReviewDto> listItem = new ArrayList<>();
    Member member;

    public MainFragment() {
        System.out.println("여기들어옴!!");
        member = null;
    }

    public MainFragment(Member member) {
        this.member = member;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FragmentTransaction fragmentTransaction;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    UsersDto dto = null;
    private
    TabLayout tabLayout;
    int count = 0;
    View view;
    int x = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        System.out.println(x++ + "///");
//뷰페이저
        ViewPager viewpager = view.findViewById(R.id.main_event_viewPager);
        fragmentTransaction = getFragmentManager().beginTransaction();
        event_viewPagerAdapter = new Event_ViewPagerAdapter(getChildFragmentManager(),fragmentTransaction);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout_event);

        final LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.getChildAt(1).setEnabled(false);
        tabStrip.getChildAt(2).setEnabled(false);

//        tabStrip.getChildAt(1).setClickable(false);

        viewpager.setAdapter(event_viewPagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
//뷰페이저

//        MainReviewSetViewPager viewPager = new MainReviewSetViewPager(getFragmentManager(),getContext());
//        ViewPager viewPageradapter = view.findViewById(R.id.main_event_viewPager);
//        tabLayout = view.findViewById(R.id.tabLayout_event);
//      viewPager= setHomeView(viewPager);
//        Frag2 frag2 = new Frag2();
//        viewPager.addItem(frag2);
//        FragEvent3 fragEvent3 = new FragEvent3();
//        viewPager.addItem(fragEvent3);
//        viewPageradapter.setAdapter(viewPager);
//        tabLayout.setupWithViewPager(viewPageradapter);


        search_text = view.findViewById(R.id.main_search_text);
        search_button = view.findViewById(R.id.mainpage_search_button);
        fragmentTransaction = getFragmentManager().beginTransaction();
        search_button.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //검색기능 구현 ,검색 대상:Category/제품명/서비스명/작성자명/리뷰 제목 등


            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            listItem.clear();
            setMyInfo();
        } else {
            listItem.clear();
            setReviewList();
        }
        dto = new UsersDto();
        //search_text.getText().toString(); 이거해주면됨.


//각 텍스트필드별로 화면 페이지 전환
        TextView textView1 = (TextView) view.findViewById(R.id.main_search_text);
        View view1 = view.findViewById(R.id.search_page);
        final Fragment fragment = new fragment_search();
        textView1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //stack에 저장해두면 back버튼을 누를때마다 이전으로 돌아갈 수 있따.
                //또한 그 내용들이 안에 저장되어 사라지지 않는다.
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.main_frame, fragment).commit();

            }
        });
        return view;
    }

    public void setMyInfo() {
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                dto = documentSnapshot.toObject(UsersDto.class);
                setReviewList();
            }
        });
    }

    public void setReviewList() {
        db = FirebaseFirestore.getInstance();
        db.collection("category").document("test")
                .collection("review")
                .orderBy("recommend_count", Query.Direction.DESCENDING)
                .limit(3)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        ReviewDto dto = new ReviewDto(
                                String.valueOf(map.get("reviewer_nickname")), String.valueOf(map.get("reviewer_uid")),
                                String.valueOf(map.get("review_create_time")), String.valueOf(map.get("review_category_UID")),
                                String.valueOf(map.get("review_main_title")), String.valueOf(map.get("review_main_string")),
                                (ArrayList<String>) map.get("review_main_image"), (ArrayList<String>) map.get("review_main_advantage"),
                                (ArrayList<String>) map.get("review_main_weakness"), (ArrayList<String>) map.get("recommend_list"),
                                Integer.parseInt(String.valueOf(map.get("recommend_count"))), Integer.parseInt(String.valueOf(map.get("review_view_number"))),
                                (ArrayList<String>) map.get("comment_list"), (ArrayList<String>) map.get("scrap_list"));
                        System.out.println(documentSnapshot.getId());
                        dto.setReview_UID(documentSnapshot.getId());
                        listItem.add(dto);
                    }

                    count = listItem.size();
                }
            }
        });
    }

    public MainReviewSetViewPager setHomeView(MainReviewSetViewPager viewPager) {
        if (member == null) {
            int arr[] = new int[5];
            int i = 0;
            int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            while (true) {
                if (i == 5) {
                    break;
                }
                int ran = (int) (Math.random() * 17);
                System.out.println(i + "번" + ran);
                switch (ran) {
                    case 0:
                        if (check[0] == 0) {
                            check[0] = 1;
                            arr[i] = 0;
                            i++;
                        }
                        break;
                    case 1:
                        if (check[1] == 0) {
                            check[1] = 1;
                            arr[i] = 1;
                            i++;
                        }
                        break;
                    case 2:
                        if (check[2] == 0) {
                            check[2] = 1;
                            arr[i] = 2;
                            i++;
                        }
                        break;
                    case 3:
                        if (check[3] == 0) {
                            check[3] = 1;
                            arr[i] = 3;
                            i++;
                        }
                        break;
                    case 4:
                        if (check[4] == 0) {
                            check[4] = 1;
                            arr[i] = 4;
                            i++;
                        }
                        break;
                    case 5:
                        if (check[5] == 0) {
                            check[5] = 1;
                            arr[i] = 5;
                            i++;
                        }
                        break;
                    case 6:
                        if (check[6] == 0) {
                            check[6] = 1;
                            arr[i] = 6;
                            i++;
                        }
                        break;
                    case 7:
                        if (check[7] == 0) {
                            check[7] = 1;
                            arr[i] = 7;
                            i++;
                        }
                        break;
                    case 8:
                        if (check[8] == 0) {
                            check[8] = 1;
                            arr[i] = 8;
                            i++;
                        }
                        break;
                    case 9:
                        if (check[9] == 0) {
                            check[9] = 1;
                            arr[i] = 9;
                            i++;
                        }
                        break;
                    case 10:
                        if (check[10] == 0) {
                            check[10] = 1;
                            arr[i] = 10;
                            i++;
                        }
                        break;
                    case 11:
                        if (check[11] == 0) {
                            check[11] = 1;
                            arr[i] = 11;
                            i++;
                        }
                        break;
                    case 12:
                        if (check[12] == 0) {
                            check[12] = 1;
                            arr[i] = 12;
                            i++;
                        }
                        break;
                    case 13:
                        if (check[13] == 0) {
                            check[13] = 1;
                            arr[i] = 13;
                            i++;
                        }
                        break;
                    case 14:
                        if (check[14] == 0) {
                            check[14] = 1;
                            arr[i] = 14;
                            i++;
                        }
                        break;
                    case 15:
                        if (check[15] == 0) {
                            check[15] = 1;
                            arr[i] = 15;
                            i++;
                        }
                        break;
                    case 16:
                        if (check[16] == 0) {
                            check[16] = 1;
                            arr[i] = 16;
                            i++;
                        }
                        break;
                }
            }
//            FragEvent1 fragEvent1 = new FragEvent1(arr[0], arr[1], arr[2], arr[3], arr[4],this);
//            viewPager.addItem(fragEvent1);
        } else {
//            FragEvent1 fragEvent1 = new FragEvent1(member.getInterest1(), member.getInterest2(), member.getInterest3(), member.getInterest4(), member.getInterest5(),this);
//            viewPager.addItem(fragEvent1);
        }
        return viewPager;
    }


}

