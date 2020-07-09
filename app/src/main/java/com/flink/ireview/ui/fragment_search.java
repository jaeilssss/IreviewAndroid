package com.flink.ireview.ui;

import androidx.lifecycle.ViewModelProviders;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.flink.ireview.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.logging.type.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class fragment_search extends Fragment {

    private String FIREBASE_CLOUD_FUNCTION_HELLOWORLD = "https://us-central1-ireview-c050e.cloudfunctions.net/helloWorld";
    private String FIREBASE_CLOUD_FUNCTION_GetSearchKey = "https://us-central1-ireview-c050e.cloudfunctions.net/getSearchKey";

    private FirebaseFunctions mFunctions;


    private FragmentSearchViewModel mViewModel;

    private TextView textView;

    public static fragment_search newInstance() {
        return new fragment_search();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        textView=(TextView)view.findViewById(R.id.temp_search);



        TabHost tabHost1 = (TabHost) view.findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("추천 검색어");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("최근 검색어");
        tabHost1.addTab(ts2);

        final EditText editText = view.findViewById(R.id.edit_text);
        final ListView listView = view.findViewById(R.id.list_view);
        Client client = new Client("TX8PW7IG25", "a58d17086d69b31454228165709706dd");
        final Index index = client.getIndex("IREVIEW");



        final EditText search_text = (EditText) view.findViewById(R.id.search_page_searchText);

        Button search_button = view.findViewById(R.id.search_page_searchButton);
        search_button.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //검색기능 구현 ,검색 대상:Category/제품명/서비스명/작성자명/리뷰 제목 등

                String response1;


                try {
                    final Task<String> taskData=run();
                    taskData.addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            textView.setText(String.format(Locale.getDefault(), "%s", taskData.getResult()));
                            Toast.makeText(getContext(), taskData.getResult(), LENGTH_SHORT).show();
                            //중요한건 Data를 받아올때 addOnCompleteListener을 사용한다는 것이다.

                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //인강 따라하기 시작

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference Test = rootRef.collection("Test");

               /* Map<String,Object> mapOne = new HashMap<>();
                mapOne.put("category_Goods","chopstics");
                Map<String,Object> mapTwo = new HashMap<>();
                mapTwo.put("category_Goods","soy chopstics");
                Map<String,Object> mapThree = new HashMap<>();
                mapThree.put("category_Goods","good chopstics");

                WriteBatch writeBatch = rootRef.batch();
                writeBatch.set(Test.document(),mapOne);
                writeBatch.set(Test.document(),mapTwo);
                writeBatch.set(Test.document(),mapThree);
                writeBatch.commit();

                Client client = new Client("TX8PW7IG25", "a58d17086d69b31454228165709706dd");
                Index index = client.getIndex("IREVIEW");

                List<JSONObject> categoryList = new ArrayList<JSONObject>();
                categoryList.add(new JSONObject(mapOne));
                categoryList.add(new JSONObject(mapTwo));
                categoryList.add(new JSONObject(mapThree));

               index.addObjectsAsync(new JSONArray(categoryList), null);*/
        //위 commit()으로 파이어스토어에 업데이트 완료 하였따
        //But 왜 하나만 올라가고 나머지는 안올라갈까???
        Test.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<String> list = new ArrayList<>();
                            for(DocumentSnapshot documentSnapshot : task.getResult()){
                                list.add(documentSnapshot.getString("category_Goods"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
                            listView.setAdapter(arrayAdapter);
                        }
                        else{
                            Toast.makeText(getContext(), "어라라?", LENGTH_SHORT).show();
                        }
                    }
                });


       editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                Query query = new Query(editable.toString())
                        .setAttributesToRetrieve("category_Goods")
                        .setHitsPerPage(50);
                index.searchAsync(query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(@Nullable JSONObject content, @Nullable AlgoliaException e) {
                        try {
                            JSONArray hits = content.getJSONArray("hits");
                            List<String> list = new ArrayList<>();
                            for(int i=0;i<hits.length();i++){
                                JSONObject jsonObject = hits.getJSONObject(i);
                                String category_Goods = jsonObject.getString("category_Goods");
                                list.add(category_Goods);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
                            listView.setAdapter(arrayAdapter);
                            //검색까지 되는데 지워지지가 않네?
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }
                });
            }
        });


        Button search_button2 = view.findViewById(R.id.search_page_searchButton2);
        search_button2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentSearchViewModel.class);
        // TODO: Use the ViewModel
    }

    private Runnable responseRunnable(final String responseStr){
        Runnable resRunnable = new Runnable(){
            public void run(){
                Toast.makeText(getContext(),responseStr, LENGTH_SHORT).show();
            }
        };
        return resRunnable;
    }

    public Task<String> run() throws IOException {

          Map<String, Object> data = new HashMap<>();
          data.put("push", true);
          mFunctions = FirebaseFunctions.getInstance();
          HttpRequest httpRequest;

          return mFunctions
                  .getHttpsCallable("helloWorldCloudFunction")
                  .call(data)
                  .continueWith(new Continuation<HttpsCallableResult, String>() {
                      @Override
                      public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                          String result = (String) task.getResult().getData();
                          return result;
                      }
                  });


    }

    }



