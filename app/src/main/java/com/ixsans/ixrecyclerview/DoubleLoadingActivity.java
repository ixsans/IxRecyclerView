package com.ixsans.ixrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ixsans.ixrecyclerview.adapter.DoubleLoadingAdapter;
import com.ixsans.ixrecyclerviewlib.IxRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DoubleLoadingActivity extends AppCompatActivity {
    List<String> list;
    DoubleLoadingAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_loading);

        // Init RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add("List Item " + i);
        }

        adapter = new DoubleLoadingAdapter(list, new IxRecyclerViewAdapter.RecyclerListener() {
            @Override
            public void onLoadPrevious() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final int newDataCount = 10;
                        for (int i = 0; i <= newDataCount; i++) {
                            list.add(0, "New data on top");
                        }
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                linearLayoutManager.scrollToPositionWithOffset(newDataCount+1, 0);
                            }
                        });
                    }
                }, 3000);

            }

            @Override
            public void onLoadMore() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start = list.size() + 1;
                        for (int i = start; i <= start+10; i++) {
                            list.add("New data on bottom " + i);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, 3000);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_header:
                adapter.showHeader(false);
                break;
            case R.id.action_delete_footer:
                adapter.showFooter(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
