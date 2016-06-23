package com.ixsans.ixrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDoubleLoading;
    Button btnEndlessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDoubleLoading = (Button) findViewById(R.id.btn_double_loading);
        btnDoubleLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoubleLoadingActivity.class));
            }
        });

        btnEndlessLoading = (Button) findViewById(R.id.btn_endless_loading);
        btnEndlessLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EndlessLoadingActivity.class));
            }
        });
    }
}
