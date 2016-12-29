package com.qtking.httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Think on 2016/12/29.
 */

public class HttpUtil {

    public static void sendRequestByURLConnection(final String urlAddress, final HttpCallBackListener callBackListenert) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                BufferedReader reader = null;

                StringBuilder data = new StringBuilder();

                try {
                    URL url = new URL(urlAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = reader.readLine()) != null) {

                        data.append(line);

                    }
                    if (callBackListenert != null) {
                        callBackListenert.onFinish(data.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBackListenert != null) {
                        callBackListenert.onError(e);
                    }
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
        }).start();

    }


    public static void sendRequestByOkHttp(String urlAddress, Callback callBackListener) {


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlAddress).build();

        client.newCall(request).enqueue(callBackListener);


    }

    public interface HttpCallBackListener {
        public void onFinish(String response);

        public void onError(Exception e);
    }
}
