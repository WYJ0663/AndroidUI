package com.example.SlideListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.SlideListView.adapter.SlideAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements AdapterView.OnItemClickListener {
    /**
     * Called when the activity is first created.
     */

    private ListViewCompat listView;
    private SlideAdapter adapter;
    private List<String> dataSourceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        init();


    }

    private void init() {

        listView = (ListViewCompat) findViewById(R.id.list_view);

        dataSourceList = new ArrayList<String>();


        for (int i = 0; i < 20; i++) {
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new SlideAdapter(this, dataSourceList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 这里处理ListItem的点击事件
        SlideView slideView = (SlideView) view;
        slideView.getStatus();
        if (slideView.getStatus() == SlideView.OnSlideListener.SLIDE_STATUS_OFF) {
            Toast.makeText(MyActivity.this, "onItemClick position=" + position + "  "
                    + slideView.getStatus(), Toast.LENGTH_SHORT).show();
        }

        adapter.setOnTopbarClickListener(new SlideAdapter.TopbarClickListener() {
            public void click(View v) {
                if (v.getId() == R.id.holder) {
                    int position = listView.getPositionForView(v);
                    Toast.makeText(MyActivity.this, "onItemClick Delete position=" + position
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
