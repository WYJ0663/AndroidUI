package com.example.SlideListView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.SlideListView.R;
import com.example.SlideListView.SlideView;

import java.util.List;


public class SlideAdapter extends BaseAdapter implements
        OnClickListener, SlideView.OnSlideListener {

    // 上次处于打开状态的SlideView
    private SlideView mLastSlideViewWithStatusOn;
    private LayoutInflater mInflater;
    List<String> mList;
    Context mContext;
    // 上次处于打开状态的SlideView
    private int mPosition;

    public SlideAdapter(Context context, List<String> list) {
        super();
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        mPosition = position;
        ViewHolder viewHolder;
        SlideView slideView = (SlideView) convertView;
        if (convertView == null) {
            // 这里是我们的item
            View itemView = mInflater.inflate(R.layout.list_item, null);

            slideView = new SlideView(mContext);
            // 这里把item加入到slideView
            slideView.setContentView(itemView);

            // 下面是做一些数据缓存
            viewHolder = new ViewHolder(slideView);
            slideView.setOnSlideListener(this);

            slideView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        viewHolder.deleteView.setOnClickListener(this);

        return slideView;
    }


    public void onSlide(View view, int status) {
        // 如果当前存在已经打开的SlideView，那么将其关闭
        if (mLastSlideViewWithStatusOn != null
                && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }
        // 记录本次处于打开状态的view
        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }

    }

    // 调用接口方法实现
    private TopbarClickListener listener;

    public void setOnTopbarClickListener(TopbarClickListener listener) {
        this.listener = listener;
    }

    public interface TopbarClickListener {
        public void click(View v);
    }

    public void onClick(View v) {
        if (listener != null)
            listener.click(v);
    }

    class ViewHolder {
        public TextView textView;
        public ViewGroup deleteView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.title);
            deleteView = (ViewGroup) view.findViewById(R.id.holder);
        }
    }

}