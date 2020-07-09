package com.flink.ireview.http.User;

import android.os.AsyncTask;
import android.util.Log;

import com.flink.ireview.Dto.Member;
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

public class MyInfoHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://192.168.0.129:8089/";
    protected String apiName;

    protected RequestBody body;


    Member member;
    public void setBodyContents(Object... params) {
        // 스프링 부트는 account 를 username 으로 써야 인식됌!!!
        body = new FormEncodingBuilder().add("username",String.valueOf(params[0])).build();
    }
    public Member send(){
        try {
            AsyncTask<Member, Void, Member> asyncTask = new AsyncTask<Member, Void, Member>() {
                @Override
                protected Member doInBackground(Member... members) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/member/myInfo";
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
                            System.out.println("1:"+jsonObject1.get("gender"));
                            System.out.println("2:"+jsonObject1.get("email"));
                            System.out.println(jsonObject1.get("phone_number"));

                            member = new Member(String.valueOf(jsonObject1.get("account")),String.valueOf(jsonObject1.get("password")),String.valueOf(jsonObject1.get("email")),String.valueOf(jsonObject1.get("name"))
                            ,String.valueOf(jsonObject1.get("nick_name")),String.valueOf(jsonObject1.get("phone_number")),String.valueOf(jsonObject1.get("birth_yy")),String.valueOf(jsonObject1.get("birth_mm")),String.valueOf(jsonObject1.get("birth_dd"))
                            ,String.valueOf(jsonObject1.get("gender")));
                            System.out.println("111");
                            member.setId(Long.valueOf(String.valueOf(jsonObject1.get("id"))));
                            System.out.println("222");
                            return member;

////                            Log.e(Tag, "result : " + data);
                        }catch(JSONException e){
                            Log.e(Tag, "result : JSONerror");
                            return null;
                        }
                    } catch (IOException e) {
                        Log.e(Tag, "result : error");
                    }
                    return member;
                }
            };

            return asyncTask.execute().get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return member;
    }
}