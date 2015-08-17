package com.example.ViewDragHelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by WYJ on 15/6/3.
 */
public class VDHLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;

    public VDHLayout(Context context) {
        super(context);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View view, int pointerId) {

                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }


            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int bopBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - bopBound;

                final int newTop = Math.min(Math.max(top, bopBound), bottomBound);

                return newTop;
            }
        });
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


}