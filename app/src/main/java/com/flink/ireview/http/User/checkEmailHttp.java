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

public class checkEmailHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://172.30.1.10:8080/";
    protected String apiName;

    protected RequestBody body;

    String data;
    public void setBodyContents(Object... params) {

        body = new FormEncodingBuilder().add("email",String.valueOf(params[0])).build();
    }
    public String send(){
        try {
            AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/member/check/email";
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