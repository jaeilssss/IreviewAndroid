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

public class MyInfoModifyHttp {
   private static final String Tag = "HttpSender";
    private static final String Url = "http://172.30.1.10:8089/";

    protected String apiName;

    protected RequestBody body;
    Member member;
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
                .add("interest1",String.valueOf(params[12]))
                .add("interest2",String.valueOf(params[13]))
                .add("interest3",String.valueOf(params[14]))
                .add("interest4",String.valueOf(params[15]))
                .add("interest5",String.valueOf(params[16]))
                .build();
    }
    public Member send(){
        try {
            AsyncTask<Member, Void, Member> asyncTask = new AsyncTask<Member, Void, Member>() {
                @Override
                protected Member doInBackground(Member... members) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/member/myInfo/modify";
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

                            member = new Member(String.valueOf(jsonObject1.get("account")),String.valueOf(jsonObject1.get("password")),String.valueOf(jsonObject1.get("email")),String.valueOf(jsonObject1.get("name"))
                                    ,String.valueOf(jsonObject1.get("nick_name")),String.valueOf(jsonObject1.get("phone_number")),String.valueOf(jsonObject1.get("birth_yy")),String.valueOf(jsonObject1.get("birth_mm")),String.valueOf(jsonObject1.get("birth_dd"))
                                    ,String.valueOf(jsonObject1.get("gender")));
                            member.setId(Long.valueOf(String.valueOf(jsonObject1.get("id"))));
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