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

abstract public class BaseActivity extends RxAppCompatActivity implements IToolBar {

    protected Toolbar mToolbar;
    private TextView mTitle;
    protected ImageView mTitleRightImg;
    protected Button mTitleRightBtn;

    private DialogUtil.CustomDialog mWaitingDialog;

    abstract protected void initLayout();

    abstract protected void initToolbar();

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

        initCustomActionBarUI();

        initToolbar();

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

    protected void initCustomActionBarUI() {
        mToolbar = (Toolbar) findViewById(R.id.hj_toolbar);

        // 设置toolbar
        if (mToolbar != null) {
            mTitle = (TextView) findViewById(R.id.tv_title);
            mTitleRightImg = (ImageView) findViewById(R.id.btn_right);
            mTitleRightBtn = (Button) findViewById(R.id.btn_right_text);

            mToolbar.setTitle("");

            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);

            setSupportActionBar(mToolbar);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnBack();
                }
            });
        }
    }

    protected void returnBack() {
        onBackPressed();
    }

    @Override
    public void updateToolbarBg(int resId) {
        if (mToolbar != null) {
            mToolbar.setBackgroundResource(resId);
        }
    }

    @Override
    public void showLeftButton(boolean isShow) {
        if (isShow) {
            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);
        } else {
            mToolbar.setNavigationIcon(null);
        }
    }

    @Override
    public void showRightImage(boolean isShow) {
        if (isShow) {
            mTitleRightImg.setVisibility(View.VISIBLE);
        } else {
            mTitleRightImg.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateTitle(String content) {
        mTitle.setText(content);
    }

    @Override
    public void clickRightImage(final Runnable runnable) {
        if (mTitleRightImg.getVisibility() == View.VISIBLE) {
            mTitleRightImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runnable.run();
                }
            });
        }
    }

    @Override
    public void showRightBtn(boolean isShow) {
        if (isShow) {
            mTitleRightBtn.setVisibility(View.VISIBLE);
        } else {
            mTitleRightBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void clickRightBtn(final Runnable runnable) {
        if (mTitleRightBtn.getVisibility() == View.VISIBLE) {
            mTitleRightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runnable.run();
                }
            });
        }
    }
}
