package com.flink.ireview.http.Reply;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.flink.ireview.Dto.Comment;
import com.flink.ireview.Dto.Recomment;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ReplyListHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;
    protected RequestBody body;
    private String data;
    private ArrayList<Recomment> list;
    private Recomment recomment;
    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("commentId", String.valueOf(params[0]))
                .add("start", String.valueOf(params[1]))
                .build();
    }
    public ArrayList<Recomment> send() {
        try {
            AsyncTask<ArrayList<Recomment>, Void, ArrayList<Recomment>> asyncTask = new AsyncTask<ArrayList<Recomment>, Void, ArrayList<Recomment>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                protected ArrayList<Recomment> doInBackground(ArrayList<Recomment>... boards) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/reply/get";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
                    System.out.println(Url + apiName);
                    try {
                        try{
                            list = new ArrayList<>();
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            System.out.println(data);
                            JSONArray jsonArray = new JSONArray(data);

                            System.out.println("length : " + jsonArray.length());
                            if(jsonArray.length()==0){
                                return new ArrayList<>();
                            }
                            for(int i = 0 ; i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                               recomment = new Recomment(Long.parseLong(String.valueOf(jsonObject1.get("id"))),Long.parseLong(String.valueOf(jsonObject1.get("user_id"))),
                                       Long.parseLong(String.valueOf(jsonObject1.get("comment_id"))), Long.parseLong(String.valueOf(jsonObject1.get("board_id"))),
                                               Integer.parseInt(String.valueOf(jsonObject1.get("like_count"))),String.valueOf(jsonObject1.get("reply_content")),
                                       String.valueOf(jsonObject1.get("created_at"))
                                       );
                                // 다음 부턴 생성자에 넣을겄!!!


                                list.add(recomment);
                            }
                            Log.e(Tag, "result : 성공" );
                            return list;
                        }catch(JSONException e){
                            Log.e(Tag, "result : JSONerror");
                            return null;
                        }
                    } catch (IOException e) {
                        Log.e(Tag, "result : error");
                    }
                    return list;
                }
            };

            return asyncTask.execute().get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
