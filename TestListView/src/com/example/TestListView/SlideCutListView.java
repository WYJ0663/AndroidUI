package com.example.TestListView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * @author xiaanming
 * @blog http://blog.csdn.net/xiaanming
 */
public class SlideCutListView extends ListView implements View.OnTouchListener {


    public SlideCutListView(Context context) {
        this(context, null);
    }

    public SlideCutListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOnTouchListener(this);
    }

    private String TAG = "TestListView";

    /**
     * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.i(TAG, "dispatchTouchEvent-ACTION_DOWN");
                super.dispatchTouchEvent(event);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                //会自动刷新
                Log.i(TAG, "dispatchTouchEvent-ACTION_MOVE");
                super.dispatchTouchEvent(event);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                //没有响应move时间会自动启动
                //无法屏蔽下级
                Log.i(TAG, "dispatchTouchEvent-ACTION_UP");
                super.dispatchTouchEvent(event);
                return true;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        //在listView 只有down事件有效
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent-ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent-ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent-ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "onTouch-ACTION_DOWN");
                return false;//不行
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "onTouch-ACTION_MOVE");
                return true;//可以屏蔽onTouchEvent move事件
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "onTouch-ACTION_UP");
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent-ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent-ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent-ACTION_UP");
                break;
        }


        return super.onTouchEvent(event);
    }
}