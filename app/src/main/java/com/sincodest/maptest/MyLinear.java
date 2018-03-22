package com.sincodest.maptest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/3/13.
 */

public class MyLinear extends LinearLayout {

    private static final String TAG = "TEST";
    public MyLinear(Context context) {
        super(context, null);
    }

    public MyLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyLinear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public MyLinear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "MyLinear dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "MyLinear onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "MyLinear onInterceptTouchEvent: ");
//        return super.onInterceptTouchEvent(ev);
        return true;
    }
}
