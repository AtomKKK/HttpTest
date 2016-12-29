package com.qtking.httptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.btn_send)
    Button mButton;
    @BindView(R.id.txt_result)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send)
    public void btnOnClick() {
        String url="https://www.baidu.com";
/*

        HttpUtil.sendRequestByURLConnection(url, new HttpUtil.HttpCallBackListener() {


            @Override
            public void onFinish(String response) {
               showResponse(response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
*/

       HttpUtil.sendRequestByOkHttp(url, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {

           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                showResponse(response.body().string());
           }
       });
    }

    private void showResponse(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(data);
            }
        });
    }






}
