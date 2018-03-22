package com.sincodest.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TestDouble extends Activity {
    private static volatile int i1 = 0;
    private static int i2 = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 100:
//                    for(int t = 0; t < 2; t++){
//                        SystemClock.sleep(500);
                        tv_1.setText(i1++ + "");
//                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };

    private TextView tv_1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_double);



        tv_1 = findViewById(R.id.tv_1);
        final TextView tv_2 = findViewById(R.id.tv_2);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.i("test", i1 + "");
                    mHandler.sendEmptyMessage(100);

                    SystemClock.sleep(1);
                }
            }
        }.start();

        //        while(true){
        //            Log.i("test", i1 + "");
        //            tv_1.setText(i1++ + "");
        //            SystemClock.sleep(1000);
        //        }

        //        while(true){
        //            tv_2.setText(i2++ + "");
        //            SystemClock.sleep(1000);
        //        }
    }
}
