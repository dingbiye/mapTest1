package com.sincodest.maptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/19.
 */

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    WebView wv_test;
    EditText et_address;
    Button btn_enter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initUI();

        initWeb();
    }

    private void initWeb() {
        wv_test.getSettings().setJavaScriptEnabled(true);
        wv_test.setWebViewClient(new WebViewClient());
        wv_test.loadUrl("http://www.baidu.com");
    }

    private void initUI() {

        wv_test = findViewById(R.id.wv_test);
        et_address = findViewById(R.id.et_address);
        btn_enter = findViewById(R.id.btn_enter);

        btn_enter.setOnClickListener(this);
    }

    private String address;
    OkHttpClient client;
    Request request;
    Response response;
    String responseData = "aa";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter:

                address = et_address.getText().toString().trim();
                //v_test.loadUrl(address);
                client = new OkHttpClient();
                request = new Request.Builder().url(address)
                        .build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            response = client.newCall(request).execute();
                            responseData = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (responseData.equals("aa")) {
                                        Toast.makeText(getApplicationContext(), "执行太快了", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }

                                    et_address.setText(responseData);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
        }
    }
}
