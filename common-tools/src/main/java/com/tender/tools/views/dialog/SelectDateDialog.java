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
import com.tender.tools.utils.StringUtil;
import com.tender.tools.views.wheelview.WheelUtil;
import com.tender.tools.views.wheelview.WheelView;

import java.util.Calendar;

/**
 * Created by boyu on 2017/12/5.
 */

public class SelectDateDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String selectText;
    private WheelDialogCallBack callBack;

    private String selectYear, selectMonth, selectDay;
    private Calendar a = Calendar.getInstance();
    private int nowYear, nowMonth, nowDay;

    private TextView tvConfirm, tvCancel;
    private WheelView wvYear, wvMonth, wvDay;

    public SelectDateDialog(Context context, String selectDate, WheelDialogCallBack callBack) {
        super(context, R.style.hj_tools_dialog_style);
        this.context = context;
        this.selectText = selectDate;
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hj_tools_wheel_dialog);
        tvConfirm = findViewById(R.id.tv_wheel_dialog_confirm);
        tvCancel = findViewById(R.id.tv_wheel_dialog_cancel);
        wvYear = findViewById(R.id.wheel_view_year);
        wvMonth = findViewById(R.id.wheel_view_month);
        wvDay = findViewById(R.id.wheel_view_day);

        wvMonth.setIsLoop(true);
        wvDay.setIsLoop(true);

        if (StringUtil.isEmpty(selectText)) {
            nowYear = a.get(Calendar.YEAR);
            nowMonth = a.get(Calendar.MONTH) + 1;
            nowDay = a.get(Calendar.DAY_OF_MONTH);
            //拼接默认选择的日期
            selectYear = String.valueOf(nowYear);
            selectMonth = String.valueOf(nowMonth);
            selectDay = String.valueOf(nowDay);
            selectText = getFormatDate(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), Integer.parseInt(selectDay));
        } else {
            selectYear = selectText.substring(0, 4);
            selectMonth = selectText.substring(5, 7);
            selectDay = selectText.substring(8);
        }

        wvYear.setItems(WheelUtil.getYearItems(), Integer.parseInt(selectYear) - WheelUtil.START_YEAR);
        wvMonth.setItems(WheelUtil.getMonthItems(), Integer.parseInt(selectMonth) - 1);//索引从0开始，所以要-1
        wvDay.setItems(WheelUtil.getDayItems(nowYear, nowMonth), Integer.parseInt(selectDay) - 1);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        wvYear.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String key, String item) {
                selectYear = key;
                wvDay.setItems(WheelUtil.getDayItems(Integer.parseInt(selectYear), Integer.parseInt(selectMonth)), Integer.parseInt(selectDay) - 1);
            }
        });
        wvMonth.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String key, String item) {
                selectMonth = key;
                wvDay.setItems(WheelUtil.getDayItems(Integer.parseInt(selectYear), Integer.parseInt(selectMonth)), Integer.parseInt(selectDay) - 1);
            }
        });
        wvDay.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String key, String item) {
                selectDay = key;
            }
        });

        init();
    }

    /**
     * 设置dialog
     */
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
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_wheel_dialog_confirm) {
            selectText = getFormatDate(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), Integer.parseInt(selectDay));
            callBack.onCallback(context, selectText);
            this.dismiss();
        } else if (id == R.id.tv_wheel_dialog_cancel) {
            this.dismiss();
        }
    }

    private String getFormatDate(int year, int monthOfYear, int dayOfMonth) {
        String month, day, date;
        if (monthOfYear <= 9) {
            month = "0" + monthOfYear;
        } else {
            month = String.valueOf(monthOfYear);
        }
        if (dayOfMonth <= 9) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }
        date = year + "-" + month + "-" + day;
        return date;
    }
}
