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

public class FIndIdHttp {
    private static final String Tag = "HttpSender";
    private static final String Url = "http://11.12.11.111:8081/";
    protected String apiName;
    String data;
    protected RequestBody body;
    public void setBodyContents(Object... params) {
        // 스프링 부트는 account 를 username 으로 써야 인식됌!!!
        body = new FormEncodingBuilder().add("name", String.valueOf(params[0]))
                .add("email", String.valueOf(params[1]))
                .build();
    }
        public String send(){
            try {
                AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        OkHttpClient client = new OkHttpClient();
                        apiName = "api/member/findId";
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
