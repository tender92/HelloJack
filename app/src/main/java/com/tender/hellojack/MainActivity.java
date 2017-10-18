package com.tender.hellojack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.tender.hellojack.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_to_my_info)
    Button btnToMyInfo;

    @OnClick(R.id.btn_to_my_info)
    public void click(Button button) {
        showToast("我要去我的小黑屋玩啦！");
        startActivity(new Intent(MainActivity.this, MineActivity.class));
    }

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
                showToast("你好我好大家好！");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
