package com.tender.hellojack;

import android.os.Bundle;
import android.widget.Toast;

import com.tender.hellojack.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_main);
    }

    @Override
    protected void initToolbar() {
        showLeftButton(false);
        showRightButton(true);
        updateTitle("MainActivity");
        clickRightButton(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "你好我好大家好！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
