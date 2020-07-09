package com.flink.ireview.http.User;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://192.168.0.129:8089/";
    protected String apiName;

    protected RequestBody body;

    String data;
    public void setBodyContents(Object... params) {
        System.out.println("--------");
        System.out.println(String.valueOf(params[0]));
        // 스프링 부트는 account 를 username 으로 써야 인식됌!!!
body = new FormEncodingBuilder().add("username",String.valueOf(params[0]))
        .add("password",String.valueOf(params[1])).build();

    }
    public String send(){
        try {
            AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/member/login";
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






