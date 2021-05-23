package com.flink.ireview.http.Comment;

import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.flink.ireview.Dto.Comment;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CommentSetListHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;

    protected RequestBody body;
    private String data;
    private ArrayList<Comment> list;
    private Comment comment;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("reviewId", String.valueOf(params[0]))
                .add("start",String.valueOf(params[1]))
                .build();
    }

    public ArrayList<Comment> send() {
        try {
            AsyncTask<ArrayList<Comment>, Void, ArrayList<Comment>> asyncTask = new AsyncTask<ArrayList<Comment>, Void, ArrayList<Comment>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                protected ArrayList<Comment> doInBackground(ArrayList<Comment>... boards) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/comment/get";
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
                                comment=new Comment(Long.parseLong(String.valueOf(jsonObject1.get("id"))),"null"
                                ,String.valueOf(jsonObject1.get("comment_user_nickname")),Long.parseLong(String.valueOf(jsonObject1.get("review_id"))),
                                        Long.parseLong(String.valueOf(jsonObject1.get("user_id"))),String.valueOf(jsonObject1.get("content_string")),
                                        String.valueOf(jsonObject1.get("created_at")),String.valueOf(jsonObject1.get("created_by"))
                                        , String.valueOf(jsonObject1.get("updated_at")),String.valueOf(jsonObject1.get("updated_by")),
                                        Integer.parseInt(String.valueOf(jsonObject1.get("reply_count")))
                                        );
                                // 다음 부턴 생성자에 넣을겄!!!

                                comment.setLikeCount(Integer.parseInt(String.valueOf(jsonObject1.get("like_count"))));
                                list.add(comment);
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