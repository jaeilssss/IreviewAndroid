package com.flink.ireview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.flink.ireview.ReviewRecycleView.main.MainReviewAdapter;
import com.flink.ireview.http.board.getBestBoardHttp;
import com.flink.ireview.interfaces.goToNewFrag;
import com.flink.ireview.interfaces.transmissionListener;
import com.flink.ireview.ui.Main.MainFragment;

import java.util.ArrayList;

public class FragEvent1 extends Fragment {
    private View view;
    private TextView txt[];
    private int no1, no2, no3, no4, no5;
    private transmissionListener onMyListener;
    private goToNewFrag newFrag;
    private FragmentTransaction fragmentTransaction;
    public FragEvent1(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }

    public FragEvent1(int no1, int no2, int no3, int no4, int no5, FragmentTransaction fragmentTransaction) {
        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
        this.no4 = no4;
        this.no5 = no5;
        this.fragmentTransaction  = fragmentTransaction;
    }
    public static FragEvent1 newinstance(FragmentTransaction fragmentTransaction) {
        FragEvent1 fragEvent1 = new FragEvent1(fragmentTransaction);
        return fragEvent1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof transmissionListener) {
            onMyListener = (transmissionListener) getActivity();
        }
        if (getActivity() != null && getActivity() instanceof goToNewFrag) {
            newFrag = (goToNewFrag) getActivity();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_event1, container, false);
        txt = new TextView[5];
        txt[0] = view.findViewById(R.id.home_category_name1);
        txt[1] = view.findViewById(R.id.home_category_name2);
        txt[2]= view.findViewById(R.id.home_category_name3);
        txt[3] = view.findViewById(R.id.home_category_name4);
        txt[4]= view.findViewById(R.id.home_category_name5);
        Member member = onMyListener.getData();
        int arr[] = new int[5];
        if (member == null) {
            int i = 0;
            int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            while (true) {
                if (i == 5) {
                    break;
                }
                int ran = (int) (Math.random() * 17);
                System.out.println(i + "번째 " + ran);
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
        } else {
            arr[0] = member.getInterest1();
            arr[1] = member.getInterest2();
            arr[2] = member.getInterest3();
            arr[3] = member.getInterest4();
            arr[4] = member.getInterest5();
        }


        getBestBoardHttp http = new getBestBoardHttp();
        http.setBodyContents(arr[0]);
        ArrayList<Board> list1 = http.send();
        http.setBodyContents(arr[1]);
        ArrayList<Board> list2 = http.send();
        http.setBodyContents(arr[2]);
        ArrayList<Board> list3 = http.send();
        http.setBodyContents(arr[3]);
        ArrayList<Board> list4 = http.send();
        http.setBodyContents(arr[4]);
        ArrayList<Board> list5 = http.send();

        setName(txt[0],arr[0]);
        setName(txt[1],arr[1]);

        setName(txt[2],arr[2]);

        setName(txt[3],arr[3]);

        setName(txt[4],arr[4]);
        MainReviewAdapter adapter1 = new MainReviewAdapter(getContext(),member,fragmentTransaction,list1,newFrag);
        MainReviewAdapter adapter2 = new MainReviewAdapter(getContext(),member,fragmentTransaction,list2,newFrag);
        MainReviewAdapter adapter3 = new MainReviewAdapter(getContext(),member,fragmentTransaction,list3,newFrag);
        MainReviewAdapter adapter4 = new MainReviewAdapter(getContext(),member,fragmentTransaction,list4,newFrag);
        MainReviewAdapter adapter5 = new MainReviewAdapter(getContext(),member,fragmentTransaction,list5,newFrag);
        RecyclerView rcv1 = view.findViewById(R.id.rv_list);
        RecyclerView rcv2 = view.findViewById(R.id.rv_list2);
        RecyclerView rcv3 = view.findViewById(R.id.rv_list3);
        RecyclerView rcv4 = view.findViewById(R.id.rv_list4);
        RecyclerView rcv5 = view.findViewById(R.id.rv_list5);
        rcv1.setAdapter(adapter1);
        rcv1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rcv2.setAdapter(adapter2);
        rcv2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rcv3.setAdapter(adapter3);
        rcv3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rcv4.setAdapter(adapter4);
        rcv4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rcv5.setAdapter(adapter5);
        rcv5.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        return view;
    }

    @Override
    public void onPause() {
        System.out.println("정지!!");
        super.onPause();
    }
    public void setName(TextView textView , int categoryId){
        System.out.println("신기하네");
        if(categoryId==0){
            textView.setText("패션");
        }else if(categoryId==1){
            textView.setText("의료");
        }else if(categoryId==2){
            textView.setText("뷰티");
        }else if (categoryId==3){
            textView.setText("문화");

        }else if(categoryId==4){
            textView.setText("생활용품");

        }else if(categoryId==5){
            textView.setText("교육");

        }else if(categoryId==6){
            textView.setText("인테리어");

        }else if(categoryId==7){
            textView.setText("도서");

        }else if(categoryId==8){
            textView.setText("가전제품");

        }else if(categoryId==9){
            textView.setText("유아용품");

        }else if(categoryId==10){
            textView.setText("IT");

        }else if(categoryId==11){
            textView.setText("애완용품");

        }else if(categoryId==12){
            textView.setText("차량/오토바이");

        }else if(categoryId==13){
            textView.setText("취미");

        }else if(categoryId==14){
            textView.setText("스포츠");

        }else if(categoryId==15){
            textView.setText("악기");

        }else if(categoryId==16){
            textView.setText("여행");
        }
    }
}