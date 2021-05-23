package com.flink.ireview.http.board;

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

public class getWriterInfoHttp  {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;

    protected RequestBody body;
    Member member;
    public void setBodyContents(Object... params) {
        // 스프링 부트는 account 를 username 으로 써야 인식됌!!!
        body = new FormEncodingBuilder().add("id",String.valueOf(params[0])).build();
    }
    public Member send(){
        try {
            AsyncTask<Member, Void, Member> asyncTask = new AsyncTask<Member, Void, Member>() {
                @Override
                protected Member doInBackground(Member... members) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/member/get/writer/info";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
                    System.out.println(Url + apiName);
                    try {
                        try{
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            JSONArray jsonArray = new JSONArray(data);
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            member=new Member();
                            member.setSumNailImage(String.valueOf(jsonObject1.get("sumnailImage")));
                            member.setAccount(String.valueOf(jsonObject1.get("account")));
                            member.setId(Long.parseLong(String.valueOf(jsonObject1.get("id"))));
                            member.setNickName(String.valueOf(jsonObject1.get("nickName")));
                            Log.e(Tag, "result : " + member.getNickName());
                            return member;
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

