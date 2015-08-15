package com.example.SlideListView.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.SlideListView.R;
import com.example.SlideListView.SlideListView;


public class SlideAdapter extends BaseAdapter implements
        OnClickListener {

    private LayoutInflater mInflater;
    List<String> mList;
    Context mContext;
    SlideListView mListView;
    // 上次处于打开状态的SlideView
    private int mPosition;

    public SlideAdapter(Context context, List<String> list, SlideListView listView) {
        super();
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListView = listView;
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
        if (convertView == null) {
            // 这里是我们的item
            convertView = mInflater.inflate(R.layout.item, null);


            // 下面是做一些数据缓存
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
            viewHolder.deleteView = (TextView) convertView.findViewById(R.id.delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(mList.get(position));
//        viewHolder.textView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "text==" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//        viewHolder.deleteView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "delete==" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text:

                break;
            case R.id.delete:

                break;
        }

    }


    class ViewHolder {
        public TextView textView;
        public TextView deleteView;


    }

}