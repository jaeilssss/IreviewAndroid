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

public class getCountMyReview {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;

    protected RequestBody body;
    private Long data;

    public void setBodyContents(Object... params) {
        body = new FormEncodingBuilder().add("userId", String.valueOf(params[0]))

                .build();
    }

    public Long send() {
        try {
            AsyncTask<Long,Long,Long> asyncTask = new AsyncTask<Long, Long, Long>() {
                @Override
                protected Long doInBackground(Long... strings) {
                    OkHttpClient client = new OkHttpClient();
                    apiName = "api/board/count/my/review";
                    client.setConnectTimeout(10, TimeUnit.SECONDS);
                    Request request = new Request.Builder().url(Url + apiName).post(body).build();
                    try {
                        Response response = client.newCall(request).execute();
                        data = Long.parseLong(response.body().string());
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
