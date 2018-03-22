package com.sincodest.maptest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.sincodest.maptest.util.Util;

/**
 * Created by Administrator on 2018/3/9.
 */

public class MyTestService extends Service{

    public MyBinder myBinder = new MyBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("service", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("service", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service", "onBind");
        return myBinder;
    }
    private boolean flag = true;
    private static int I = 0;
    public class MyBinder extends Binder {

        public void startGoOn(){
            flag = true;
            tell(I);
        }

        public void stopTell(){
            flag = false;
        }

        public void tell( int i){
            flag = true;
            I = i;
            Thread thread1 = new Thread(){
                @Override
                public void run() {
//                    int i = 0;
                    while(flag){
                        Log.i("service", I++ + "");

                        Message msg = Message.obtain();
                        msg.what = 0;
                        msg.arg1 = I;
                        mHandler.sendMessage(msg);

                        SystemClock.sleep(1000);
                    }
                }
            };
            thread1.start();


        }

        public void setOnResultOkListener(OnResultOkListener listener) {
            mListener = listener;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    mListener.show(msg.arg1);
                    break;
            }

        }
    };

    private OnResultOkListener mListener;
    @Override
    public void onDestroy() {
        flag = false;
        Log.i("service", "onDestroy");
        super.onDestroy();
    }

}
