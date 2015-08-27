package com.example.SlideListView;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class ListViewCompat extends ListView {

    private static final String TAG = "ListViewCompat";

    private SlideView mFocusedItemView;

    public ListViewCompat(Context context) {
        this(context, null);
    }

    public ListViewCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    int tempX;
    int tempY;
    int temp = 20;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                SlideView view = mFocusedItemView;


                // 我们想知道当前点击了哪一行
                int position = pointToPosition(x, y);
                Log.e(TAG, "postion=" + position);
                if (position != INVALID_POSITION) {
                    // 得到当前点击行的数据从而取出当前行的item。
                    // 可能有人怀疑，为什么要这么干？为什么不用getChildAt(position)？
                    // 因为ListView会进行缓存，如果你不这么干，有些行的view你是得不到的。
//				MessageItem data = (MessageItem) getItemAtPosition(position);
//				mFocusedItemView = data.slideView;

                    // 获取我们点击的item view
                    mFocusedItemView = (SlideView) getChildAt(position - getFirstVisiblePosition());
                    Log.e(TAG, "FocusedItemView=" + mFocusedItemView);


                    if (view != null) {
                        if (mFocusedItemView != view) {
                            view.shrink();
                        }
                    }
                    tempX = x;
                    tempY = y;
                }
            }
            default:
                break;
        }

        // 向当前点击的view发送滑动事件请求，其实就是向SlideView发请求
        if (mFocusedItemView != null) {

            //判断左右还是上下滑动
            if (Math.abs(tempX - x) > temp || Math.abs(tempY - y) > temp) {
                if (2 * Math.abs(tempX - x) > Math.abs(tempY - y)) {
                    mFocusedItemView.onRequireTouchEvent(event);
                    return true;
                } else {
                    return super.dispatchTouchEvent(event);
                }
            } else {
                return super.dispatchTouchEvent(event);
            }
        }

        return super.dispatchTouchEvent(event);
    }


}
