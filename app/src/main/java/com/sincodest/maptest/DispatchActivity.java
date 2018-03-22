package com.sincodest.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/3/13.
 */

public class DispatchActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("TEST", "DispatchActivity dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TEST", "DispatchActivity onTouchEvent: ");
        return super.onTouchEvent(event);
    }

}
