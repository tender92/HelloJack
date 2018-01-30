package com.tender.hellojack.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.manager.MyApplication;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.umengshare.DataAnalyticsManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by boyu on 2017/10/18.
 */

abstract public class BaseActivity extends RxAppCompatActivity {

    abstract protected void initLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();

        /**
         * &  与运算符（两个操作数中位都为1，结果才为1，否则结果为0）
         * |  或运算符（两个位只要有一个为1，那么结果就是1，否则就为0）
         * ~  非运算符（如果位为0，结果是1，如果位为1，结果是0）
         * ^  异或运算符（两个操作数的位中，相同则结果为0，不同则结果为1）
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams mLayoutParams = getWindow().getAttributes();
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | mLayoutParams.flags;  //只要其中一个为1，结果为1
        }

        MyApplication.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataAnalyticsManager.getInstance().onPageStart(this.getClass().toString());
        DataAnalyticsManager.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataAnalyticsManager.getInstance().onPageEnd(this.getClass().toString());
        DataAnalyticsManager.getInstance().onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.removeActivity(this);
    }
}
