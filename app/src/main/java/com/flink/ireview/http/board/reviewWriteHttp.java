package com.flink.ireview.http.board;

import android.os.AsyncTask;
import android.util.Log;

import com.flink.ireview.Dto.Board;
import com.flink.ireview.Dto.Member;
import com.google.gson.JsonArray;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class reviewWriteHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://172.30.1.10:8080/";

    protected String apiName;

    protected RequestBody body;
    Board board;
    String data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("id",String.valueOf(params[0]))
                .add( "account", String.valueOf(params[1]))
                .add("password", String.valueOf(params[2]))
                .add("email", String.valueOf(params[3]))
                .add("name",String.valueOf(params[4]))
                .add("nickName",String.valueOf(params[5]))
                .add("phoneNumber", String.valueOf(params[6]))
                .add("loginIp",String.valueOf(params[7]))
                .add("birthYy",String.valueOf(params[8]))
                .add("birthMm",String.valueOf(params[9]))
                .add("birthDd",String.valueOf(params[10]))
                .add("gender",String.valueOf(params[11]))
                .add("status",String.valueOf(params[12]))
                .add("interest1",String.valueOf(params[13]))
                .add("interest2",String.valueOf(params[14]))
                .add("interest3",String.valueOf(params[15]))
                .add("interest4",String.valueOf(params[16]))
                .add("interest5",String.valueOf(params[17]))
                .build();
    }
    public Board send(){
        try {
            AsyncTask<Board, Void, Board> asyncTask = new AsyncTask<Board, Void, Board>() {
                @Override
                protected Board doInBackground(Board... boards) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/board/write";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
                    System.out.println(Url + apiName);
                    try {
                        try{
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            System.out.println(data);
                            JSONArray jsonArray = new JSONArray(data);
//                        System.out.println("length : " + jsonArray.length());
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                board = new Board(jsonObject1.getLong("id"),Integer.parseInt(String.valueOf(jsonObject1.get("categoryId")))
                                ,String.valueOf(jsonObject1.get("title")),String.valueOf(jsonObject1.get("contentString")),String.valueOf(jsonObject1.get("userAccount")),
                                        String.valueOf(jsonObject1.get("userNickname")),Integer.parseInt(String.valueOf(jsonObject1.get("totalView"))),
                                        Integer.parseInt(String.valueOf(jsonObject1.get("totalRecommend"))),Integer.parseInt(String.valueOf(jsonObject1.get("totalComment"))),
                                        Boolean.getBoolean(String.valueOf(jsonObject1.get("manageBoard"))),String.valueOf(jsonObject1.get("image1")),
                                        String.valueOf(jsonObject1.get("image2")),String.valueOf(jsonObject1.get("image3")),String.valueOf(jsonObject1.get("image4")),
                                        String.valueOf(jsonObject1.get("image5")),String.valueOf(jsonObject1.get("image6")),String.valueOf(jsonObject1.get("image7"))
                                ,String.valueOf(jsonObject1.get("image8")),jsonObject1.getLong("scrapCount"));
                            JSONArray array = jsonObject1.getJSONArray("goodness");
                                for(int i=0; i<array.length();i++){

                                }
                            Log.e(Tag, "result : 성공" );
                            return board;


                        }catch(JSONException e){
                            Log.e(Tag, "result : JSONerror");
                            return null;
                        }
                    } catch (IOException e) {
                        Log.e(Tag, "result : error");
                    }
                    return board;
                }
            };

            return asyncTask.execute().get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return board;
    }
}