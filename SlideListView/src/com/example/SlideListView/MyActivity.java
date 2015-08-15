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

    private SlideListView listView;
    private SlideAdapter adapter;
    private List<String> dataSourceList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        init();


    }

    private void init() {

        listView = (SlideListView) findViewById(R.id.list_view);

        dataSourceList = new ArrayList<String>();


        for (int i = 0; i < 20; i++) {
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new SlideAdapter(this, dataSourceList, listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MyActivity.this, "onItemClick--" + position, Toast.LENGTH_SHORT).show();
        listView.closeView();


    }

}
