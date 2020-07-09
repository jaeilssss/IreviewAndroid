package com.flink.ireview.http.User;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUpHttp  {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://192.168.0.129:8089/";

    protected String apiName;

    protected RequestBody body;

    String data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("account", (String) params[0])
                .add("password", (String) params[1])
                .add("email", (String) params[2])
                .add("name",(String)params[3])
                .add("nickName",(String)params[4])
                .add("phoneNumber", (String)params[5])
                .add("loginIp",(String)params[6])
                .add("birthYy",(String)params[7])
                .add("birthMm",(String)params[8])
                .add("birthDd",(String)params[9])
                .add("gender",(String)params[10])
                .add("interest1",(String)params[11])
                .add("interest2",(String)params[12])
                .add("interest3",(String)params[13])
                .add("interest4",(String)params[14])
                .add("interest5",(String)params[15])
                .build();
    }
public String send(){
    try {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();
                apiName = "api/member/signUp";
                client.setConnectTimeout(10, TimeUnit.SECONDS);
                Request request = new Request.Builder().url(Url + apiName).post(body).build();
                System.out.println(Url + apiName);
                try {
                    Response response = client.newCall(request).execute();
                    data = response.body().string();
                    Log.e(Tag, "result : " + data);
                    return data;

                } catch (IOException e) {
                    Log.e(Tag, "result : error");
                }
                return null;
            }
        };

        return asyncTask.execute().get();
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}
}

