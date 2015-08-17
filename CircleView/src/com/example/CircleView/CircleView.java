package com.example.CircleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by WYJ on 2015-08-17.
 */
public class CircleView extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;
    private int mDoProgress;

    int mSpeed;

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFirstColor = context.getResources().getColor(R.color.blue);
        mSecondColor = context.getResources().getColor(R.color.red);
        mCircleWidth = 8;

        mProgress = 0;
        mDoProgress = 0;

        mSpeed = 40;

        mPaint = new Paint();
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mDoProgress++;
                    if (mDoProgress <= mProgress && mDoProgress >= 0) {
                        postInvalidate();
                    }


                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onFinishInflate() {


        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int centre = getWidth() / 2; // 外半径
        int radius = centre - mCircleWidth / 2;//内半径

        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        //      if (!isNext) {// 第一颜色的圈完整，第二颜色跑
        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环

        mPaint.setColor(mSecondColor); // 设置圆环的颜色
        canvas.drawArc(oval, -90, mDoProgress, false, mPaint); // 根据进度画圆弧
//        }

    }

    Thread thread;

    public void setProgress(int progress) {
        mProgress = progress;
    }

}
