package com.ixsans.ixrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ixsans.ixrecyclerview.adapter.EndlessLoadingAdapter;

import java.util.ArrayList;
import java.util.List;

public class EndlessLoadingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<String> list;
    EndlessLoadingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_loading);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add("List Item " + i);
        }

        adapter = new EndlessLoadingAdapter(recyclerView, list, new EndlessLoadingAdapter.RecyclerListener() {
            @Override
            public void onLoadPrevious() {}

            @Override
            public void onLoadMore() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int start = list.size() + 1;
                        for (int i = start; i <= start+10; i++) {
                            list.add("List Item " + i);
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
        menu.findItem(R.id.action_disable_loading).setVisible(true);
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

            case R.id.action_disable_loading:
                adapter.setEnableLoadMore(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
