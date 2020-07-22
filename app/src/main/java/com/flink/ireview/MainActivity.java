package com.flink.ireview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.flink.ireview.Dao.UsersDao;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.find_password.fragment_find_password;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.sign_up.fragment_signup;
import com.flink.ireview.ui.Category.fragment_category;
import com.flink.ireview.ui.FragmentSearchViewModel;
import com.flink.ireview.ui.Main.MainFragment;
import com.flink.ireview.ui.MyPage.MyPageFragment;
import com.flink.ireview.ui.MyPage.MyPageModifyFragment;
import com.flink.ireview.ui.login.LoginFragment;
import com.flink.ireview.ui.review.reviewWriteFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.flink.ireview.sign_up.*;

import io.opencensus.tags.Tag;

public class MainActivity extends AppCompatActivity implements transmissionListener {

    // private Event_ViewPagerAdapter event_viewPagerAdapter;
    public void onReceivedData(Object data){
        this.member  = (Member)data;
    }
    private Member member =null;
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private MainFragment frag3;
    private Frag4 frag4;
    private LoginFragment frag5;
    private Fragment fragment_clothes;
    private fragment_category fragment_category;
    private LoginFragment LoginFragment;
    private fragment_signup fragment_signup;
    private MyPageFragment MyPageFragment;

    private DrawerLayout drawerLayout;
    private View drawerView;

    private Button tap_clothes, tap_baby, tap_beauty, tap_pet, tap_daily, tap_hobby, tap_interior, tap_instrument, tap_appliances, tap_IT, tap_car, tap_sports, tap_travel;
    private Button tap_medical, tap_culture, tap_education, tap_book, drawer_login, drawer_join;

    int count =0;
    private LinearLayout botton_fashion;
    private Button botton_fashion_female_clothes, botton_fashion_kids_clothes, botton_fashion_male_clothes, botton_fashion_bag, botton_fashion_female_shoes,
            botton_fashion_watch, botton_fashion_male_shoes, botton_fashion_jewelry, botton_fashion_underwear, botton_fashion_wallet;


    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseUser user ;
    UsersDao dao;
    private String TAG;

    @Override
    protected void onCreate (Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    System.out.println("dd");
        setContentView(R.layout.activity_main);

        //뷰페이저 세팅 (메인프래그먼트로 옮김)

        //ViewPager viewpager = findViewById(R.id.main_event_viewPager);
        //event_viewPagerAdapter = new Event_ViewPagerAdapter(getSupportFragmentManager());

        //TabLayout tabLayout = findViewById(R.id.tabLayout_event);
        // viewpager.setAdapter(event_viewPagerAdapter);
        //  tabLayout.setupWithViewPager(viewpager);

        tap_clothes = findViewById(R.id.tap_clothes);
        tap_clothes.setOnClickListener(onClickListener);

        botton_fashion = findViewById(R.id.botton_fashion);

        botton_fashion_female_clothes = findViewById(R.id.botton_fashion_female_clothes);
        botton_fashion_female_clothes.setOnClickListener(onClickListener);

        botton_fashion_kids_clothes = findViewById(R.id.botton_fashion_kids_clothes);
        botton_fashion_kids_clothes.setOnClickListener(onClickListener);

        botton_fashion_male_clothes = findViewById(R.id.botton_fashion_male_clothes);
        botton_fashion_male_clothes.setOnClickListener(onClickListener);

        botton_fashion_bag = findViewById(R.id.botton_fashion_bag);
        botton_fashion_bag.setOnClickListener(onClickListener);

        botton_fashion_female_shoes = findViewById(R.id.botton_fashion_female_shoes);
        botton_fashion_female_shoes.setOnClickListener(onClickListener);

        botton_fashion_watch = findViewById(R.id.botton_fashion_watch);
        botton_fashion_watch.setOnClickListener(onClickListener);

        botton_fashion_male_shoes = findViewById(R.id.botton_fashion_male_shoes);
        botton_fashion_male_shoes.setOnClickListener(onClickListener);

        botton_fashion_jewelry = findViewById(R.id.botton_fashion_jewelry);
        botton_fashion_jewelry.setOnClickListener(onClickListener);

        botton_fashion_underwear = findViewById(R.id.botton_fashion_underwear);
        botton_fashion_underwear.setOnClickListener(onClickListener);

        botton_fashion_wallet = findViewById(R.id.botton_fashion_wallet);
        botton_fashion_wallet.setOnClickListener(onClickListener);

        tap_baby = findViewById(R.id.tap_baby);
        tap_baby.setOnClickListener(onClickListener);

        tap_beauty = findViewById(R.id.tap_beauty);
        tap_beauty.setOnClickListener(onClickListener);

        tap_pet = findViewById(R.id.tap_pet);
        tap_pet.setOnClickListener(onClickListener);

        tap_daily = findViewById(R.id.tap_daily);
        tap_daily.setOnClickListener(onClickListener);

        tap_hobby = findViewById(R.id.tap_hobby);
        tap_hobby.setOnClickListener(onClickListener);

        tap_interior = findViewById(R.id.tap_interior);
        tap_interior.setOnClickListener(onClickListener);

        tap_instrument = findViewById(R.id.tap_instrument);
        tap_instrument.setOnClickListener(onClickListener);

        tap_appliances = findViewById(R.id.tap_appliances);
        tap_appliances.setOnClickListener(onClickListener);

        tap_IT = findViewById(R.id.tap_IT);
        tap_IT.setOnClickListener(onClickListener);

        tap_car = findViewById(R.id.tap_car);
        tap_car.setOnClickListener(onClickListener);

        tap_sports = findViewById(R.id.tap_sports);
        tap_sports.setOnClickListener(onClickListener);

        tap_travel = findViewById(R.id.tap_travel);
        tap_travel.setOnClickListener(onClickListener);

        tap_medical = findViewById(R.id.tap_medical);
        tap_medical.setOnClickListener(onClickListener);

        tap_culture = findViewById(R.id.tap_culture);
        tap_culture.setOnClickListener(onClickListener);

        tap_education = findViewById(R.id.tap_education);
        tap_education.setOnClickListener(onClickListener);

        tap_book = findViewById(R.id.tap_book);
        tap_book.setOnClickListener(onClickListener);

        drawer_login = findViewById(R.id.drawer_login);
        drawer_login.setOnClickListener(onClickListener);

        drawer_join = findViewById(R.id.drawer_join);
        drawer_join.setOnClickListener(onClickListener);



        //여기
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_category:
                        drawerLayout.openDrawer(drawerView);
//                        setFragg(0);
                        break;
                    case R.id.action_ranking:
                        setFrag(1);
                        break;
                    case R.id.action_home:
                        setFrag(2);
                        break;
                    case R.id.action_alarm:
                        setFrag(3);
                        break;
                    case R.id.action_mypage:
                        if(member!=null){
                            MyPageFragment = new MyPageFragment(member);
                            setFrag(5);}
                        else{
                            Toast.makeText(getApplicationContext(),"로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            setFrag(4);}
                        break;
                }
                return true;
            }
        });
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new MainFragment();
        frag4 = new Frag4();
        frag5 = new LoginFragment();


        setFrag(2); // 첫프래그먼트 화면을 무엇으로 지정해줄 것인지 선택

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        /*Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            //열려라 참깨를 누르면 네비게이션이 열린다
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });*/

        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //여기까지

        final Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_login, R.id.nav_main, R.id.nav_category,
                R.id.nav_latest_viewd, R.id.nav_Recommended_viewd,
                R.id.nav_Event, R.id.nav_Rangking, R.id.nav_mypage, R.id.nav_option,
                R.id.nav_service_center)
                .setDrawerLayout(drawer)
                .build();

        //푸시알람.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Log", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Log", "FCM 토큰: " + token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });



        /*final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_login:
                        startActivity(intent);
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                       // menuItem.setTitle("로그아웃");
                        break;
                    case R.id.nav_main:
//                        Fragment fragment = new MainFragment();
//                        replaceFragment(fragment);
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_category:
                        startActivity(intent);
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_latest_viewd:
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_Recommended_viewd:
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_Event:
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_Rangking:
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;


                        case R.id.nav_mypage :
                       if(FirebaseAuth.getInstance().getCurrentUser()==null){
                           Toast.makeText(getApplicationContext(),"회원만 이용가능합니다", Toast.LENGTH_SHORT).show();
                       }else{
                           NavigationUI.onNavDestinationSelected(menuItem,navController);

                       }
                            break;
                    case R.id.nav_option:
                        Toast.makeText(getApplicationContext(), "옵션", Toast.LENGTH_SHORT).show();
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;

                    case R.id.nav_service_center:
                        Toast.makeText(getApplicationContext(), "서비스 센터", Toast.LENGTH_SHORT).show();
                        NavigationUI.onNavDestinationSelected(menuItem,navController);
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });*/

    }


    //여기부터
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5);
                ft.commit();
                break;
            case 5:
                ft.replace(R.id.main_frame, MyPageFragment);
                ft.commit();
                break;
        }

    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            //슬라이드 했을 때
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

            //open이 완료 됐을 때
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

            //슬라이드가 닫혔을 때
        }

        @Override
        public void onDrawerStateChanged(int newState) {

            //상태가 change가 됐을 때
        }
    };
    //여기까지

    //네비게이션

    private void setNavi(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragment_category = new fragment_category(member);
        LoginFragment = new LoginFragment();
        fragment_signup = new fragment_signup();

        switch (n){
            case 0:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 1:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 2:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 3:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 4:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 5:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 6:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 7:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 8:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 9:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 10:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 11:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 12:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 13:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 14:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 15:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 16:
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 17:
                //LOGIN
                ft.addToBackStack(null).replace(R.id.main_frame, LoginFragment);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 18:
                //JOIN
                ft.addToBackStack(null).replace(R.id.main_frame, fragment_signup);
                ft.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

    }
    // 클릭리스너
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            switch (v.getId()){
                case R.id.tap_clothes :
                    /*drawerLayout.openDrawer(drawerView);
                    setNavi(0);*/
                    count++;
                    if (count%2 == 1)
                        botton_fashion.setVisibility(View.VISIBLE);
                    else
                        botton_fashion.setVisibility(View.GONE);
                    break;
                case R.id.tap_baby :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(1);
                    break;
                case R.id.tap_beauty :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(2);
                    break;
                case R.id.tap_pet :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(3);
                    break;
                case R.id.tap_daily :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(4);
                    break;
                case R.id.tap_hobby :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(5);
                    break;
                case R.id.tap_interior :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(6);
                    break;
                case R.id.tap_instrument :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(7);
                    break;
                case R.id.tap_appliances :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(8);
                    break;
                case R.id.tap_IT :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(9);
                    break;
                case R.id.tap_car :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(10);
                    break;
                case R.id.tap_sports :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(11);
                    break;
                case R.id.tap_travel :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(12);
                    break;
                case R.id.tap_medical :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(13);
                    break;
                case R.id.tap_culture :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(14);
                    break;
                case R.id.tap_education :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(15);
                    break;
                case R.id.tap_book :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(16);
                    break;
                case R.id.drawer_login :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(17);
                    break;
                case R.id.drawer_join :
                    drawerLayout.openDrawer(drawerView);
                    setNavi(18);
                    break;
            }
        }
    };
    //클릭리스너

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_frame);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                default:
                    ft.addToBackStack(null).replace(R.id.main_frame, fragment_category);
                    ft.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }
        }
    }*/

}
