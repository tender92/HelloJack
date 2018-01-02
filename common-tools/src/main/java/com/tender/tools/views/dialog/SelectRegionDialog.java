package com.tender.tools.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tender.tools.R;
import com.tender.tools.views.wheelview.WheelModel;
import com.tender.tools.views.wheelview.WheelView;

import java.util.List;

/**
 * Created by boyu on 2017/12/7.
 */

public class SelectRegionDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private int selectIndex;
    private boolean isShowIcon = false;
    private List<WheelModel> mListData;
    private WheelDialogCallBack callback;

    private TextView tvConfirm, tvCancel;
    private WheelView wheelView;

    public SelectRegionDialog(Context context, int selectIndex, boolean isShowIcon, List<WheelModel> mListData, WheelDialogCallBack callback) {
        super(context, R.style.hj_tools_dialog_style);
        this.context = context;
        this.selectIndex = selectIndex;
        this.isShowIcon = isShowIcon;
        this.mListData = mListData;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hj_tools_wheel_select_region);
        tvConfirm = (TextView) findViewById(R.id.tv_wheel_dialog_confirm);
        tvCancel = (TextView) findViewById(R.id.tv_wheel_dialog_cancel);
        wheelView = (WheelView) findViewById(R.id.wheel_view_region);

        wheelView.setIsShowIcon(isShowIcon);
        wheelView.setIsLoop(false);
        wheelView.setItems(mListData, selectIndex);

        tvConfirm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        wheelView.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int selectedIndex, String key, String item) {
                selectIndex = selectedIndex;
            }
        });
        init();
    }

    private void init() {
        //获取当前Activity所在的窗体
        Window dialogWindow = this.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_wheel_dialog_confirm) {
            callback.onCallback(context, String.valueOf(selectIndex));
            this.dismiss();
        } else if (id == R.id.tv_wheel_dialog_cancel) {
            this.dismiss();
        }

    }
}
