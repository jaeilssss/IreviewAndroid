package com.flink.ireview.http.board;

import android.os.AsyncTask;
import android.util.Log;

import com.flink.ireview.Dto.Board;
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

public class reviewModifyHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";

    protected String apiName;

    protected RequestBody body;
    Board board;
    String data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder()
                .add("id",String.valueOf(params[0]))
                .add("userId",String.valueOf(params[1]))
                .add( "title", String.valueOf(params[2]))
                .add("content", String.valueOf(params[3]))
                .add("categoryId", String.valueOf(params[4]))
                .add("userAccount",String.valueOf(params[5]))
                .add("userNickName",String.valueOf(params[6]))
                .add("reviewImage1", String.valueOf(params[7]))
                .add("reviewImage2",String.valueOf(params[8]))
                .add("reviewImage3",String.valueOf(params[9]))
                .add("reviewImage4",String.valueOf(params[10]))
                .add("reviewImage5",String.valueOf(params[11]))
                .add("reviewImage6",String.valueOf(params[12]))
                .add("reviewImage7",String.valueOf(params[13]))
                .add("reviewImage8",String.valueOf(params[14]))
                .add("createdDate",String.valueOf(params[15]))
                .add("totalView",String.valueOf(params[16]))
                .add("totalComment",String.valueOf(params[17]))
                .add("totalRecommend",String.valueOf(params[18]))
                .add("scrapCount",String.valueOf(params[19]))
                .build();
    }
    public Board send(){
        try {
            AsyncTask<Board, Void, Board> asyncTask = new AsyncTask<Board, Void, Board>() {
                @Override
                protected Board doInBackground(Board... boards) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/board/modify";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
                    System.out.println(Url + apiName);
                    try {
                        try{
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            System.out.println(data);
                            JSONArray jsonArray = new JSONArray(data);
                            board=new Board();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            board = new Board(Integer.parseInt(String.valueOf(jsonObject1.get("category_id"))),String.valueOf(jsonObject1.get("title")),String.valueOf(jsonObject1.get("content_string")),
                                    String.valueOf(jsonObject1.get("user_account")),String.valueOf(jsonObject1.get("user_nickname")),
                                    Integer.parseInt(String.valueOf(jsonObject1.get("total_view"))),Integer.parseInt(String.valueOf(jsonObject1.get("total_recommend"))),Integer.parseInt(String.valueOf(jsonObject1.get("total_comment"))),
                                    Boolean.getBoolean(String.valueOf(jsonObject1.get("manage_board"))),String.valueOf(jsonObject1.get("image1")),String.valueOf(jsonObject1.get("image2")),String.valueOf(jsonObject1.get("image3"))
                                    ,String.valueOf(jsonObject1.get("image4")),String.valueOf(jsonObject1.get("image5")),String.valueOf(jsonObject1.get("image6")),String.valueOf(jsonObject1.get("image7"))
                                    ,String.valueOf(jsonObject1.get("image8")),Long.parseLong(String.valueOf(jsonObject1.get("scrap_count"))),
                                    String.valueOf(jsonObject1.get("created_date")));
                            board.setId(Long.parseLong(String.valueOf(jsonObject1.get("id"))));
                            board.setUserId(Long.parseLong(String.valueOf(jsonObject1.get("user_id"))));
                            board.setProductName(String.valueOf(jsonObject1.get("product_name")));
                            System.out.println(Long.parseLong(String.valueOf(jsonObject1.get("category_id"))));
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