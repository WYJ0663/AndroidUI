package com.example.TestListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity{
    private SlideCutListView slideCutListView;
    private ArrayAdapter<String> adapter;
    private List<String> dataSourceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();


    }

    private void init() {
        slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);

        for (int i = 0; i < 20; i++) {
            dataSourceList.add("滑动删除" + i);
        }

        adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.list_item, dataSourceList);
        slideCutListView.setAdapter(adapter);

        slideCutListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MyActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
