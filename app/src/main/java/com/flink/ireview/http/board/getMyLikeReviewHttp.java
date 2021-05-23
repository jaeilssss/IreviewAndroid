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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class getMyLikeReviewHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";

    protected String apiName;
    private ArrayList<Board> list;
    protected RequestBody body;
    Board board;
    String data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("userId",String.valueOf(params[0]))
                .add("start",String.valueOf(params[1]))
                .build();
    }
    public ArrayList<Board> send(){
        try {
            AsyncTask<ArrayList<Board>, Void, ArrayList<Board>> asyncTask = new AsyncTask<ArrayList<Board>, Void, ArrayList<Board>>() {
                @Override
                protected ArrayList<Board> doInBackground(ArrayList<Board>... boards) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/like/user/recommendList";
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
                                board=new Board();
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
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

//                                System.out.println(Long.parseLong(String.valueOf(jsonObject1.get("scrap_count"))));

                                list.add(board);
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