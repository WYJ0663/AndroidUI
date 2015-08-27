package com.example.SlideListView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by WYJ on 2015/8/2.
 */
public class SlideListView extends ListView implements View.OnTouchListener,AbsListView.OnScrollListener {

    private static final String TAG = "SlideListView";
    private Context context;

    /**
     * 当前滑动的ListView　position
     */
    private int slidePosition;
    /**
     * 手指按下X的坐标
     */
    private int downY;
    /**
     * 手指按下Y的坐标
     */
    private int downX;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * ListView的item
     */
    private View itemView;
    /**
     * 滑动类
     */

    private boolean isSlide = false;
    /**
     * 认为是用户滑动的最小距离
     */
    private int mTouchSlop;
    private Scroller scroller;

    private int itemViewWidth;

    //是否有打开的
    private boolean isOpen = false;

    public SlideListView(Context context) {
        super(context);
        init(context);
    }


    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        scroller = new Scroller(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();


        itemViewWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
                        .getDisplayMetrics());

        setOnTouchListener(this);
        setOnScrollListener(this);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isOpen && itemView != null) {
                    if (slidePosition != pointToPosition(x, y)) {
                        closeView();
                        return  true;
                    }
                }

                slidePosition = pointToPosition(x, y);

                // 无效的position, 不做任何处理
                if (slidePosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(event);
                }

                // 获取我们点击的item view
                itemView = getChildAt(slidePosition - getFirstVisiblePosition());

                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;

        }

        return super.dispatchTouchEvent(event);
    }

    int lastX;
    private static final int TAN = 2;

    private VelocityTracker velocityTracker;
    private static final int SNAP_VELOCITY = 200;

    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        return super.onTouchEvent(ev);
    }


    private void smoothScrollTo(int destX, int destY) {
        // 缓慢滚动到指定位置
        int scrollX = itemView.getScrollX();
        int delta = destX - scrollX;
        // 以三倍时长滑向destX，效果就是慢慢滑动
        scroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    public void closeView() {
        int d = itemView.getScrollX();
        scroller.startScroll(d, 0, -d, 0, d * 2);
        isOpen = false;
        invalidate();

    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }

    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

            if (isTBScroll) {
                return false;
            }

        addVelocityTracker(event);
        int x = (int) event.getX();

        int scrollX = itemView.getScrollX();

        if (slidePosition != INVALID_POSITION) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;

                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaX = x - lastX; // X移动距离
                    int newScrollX = scrollX - deltaX;
                    if (deltaX != 0) {
                        if (newScrollX < 0) {
                            newScrollX = 0;
                        } else if (newScrollX > itemViewWidth) {
                            newScrollX = itemViewWidth;
                        }
                        itemView.scrollTo(newScrollX, 0);
                    }

                    lastX = x;
                break;

                case MotionEvent.ACTION_UP:
                    if (scroller.isFinished()) {
                        velocityTracker.computeCurrentVelocity(1000);
                        int velocityX = (int) velocityTracker.getXVelocity();
                        Log.d(TAG, "velocityX=" + velocityX);

                        int newScrollXUp = 0;
                        // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
                        if (scrollX > itemViewWidth * 0.5) {
                            newScrollXUp = itemViewWidth;
                            isOpen = true;
                        } else {
                            isOpen = false;
                        }

                        if (isOpen && velocityX > SNAP_VELOCITY) {
                            newScrollXUp = 0;
                            isOpen = false;
                        } else if (!isOpen && velocityX < -SNAP_VELOCITY) {//左负数打开
                            newScrollXUp = itemViewWidth;
                            isOpen = true;
                        }
                        // 慢慢滑向终点
                        this.smoothScrollTo(newScrollXUp, 0);

                        recycleVelocityTracker();
                    }
                    break;
            }

        }
        return false;
    }

    private boolean isTBScroll = false;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch(scrollState){
            case OnScrollListener.SCROLL_STATE_IDLE://空闲状态
                isTBScroll = false;
                break;
            case OnScrollListener.SCROLL_STATE_FLING://滚动状态
                isTBScroll = true;
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                isTBScroll = true;
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
