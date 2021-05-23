package com.flink.ireview.http.board;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class reviewSearchLikeHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;

    protected RequestBody body;
    private String data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("userId", String.valueOf(params[0]))
                .add("reviewId",String.valueOf(params[1]))
                .add("categoryId",String.valueOf(params[2]))
                .build();
    }

    public String send() {
        try {
            AsyncTask<String,String,String> asyncTask = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/like/search";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
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
        } catch (Exception e) {
            Log.e(Tag,"error");
        }
        return null;
    }
}

