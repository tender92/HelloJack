package com.tender.hellojack.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tender.hellojack.R;

/**
 * Created by boyu on 2017/10/18.
 */

public class DialogUtil {

    public static void showHint(Context context, String txt) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.hj_dialog_toast, null);
        TextView msg = (TextView) view.findViewById(R.id.toast_short_msg);
        msg.setTextSize(TypedValue.COMPLEX_UNIT_PX, App.getDimensionByResId(R.dimen.hj_text_big_size));
        msg.setText(txt);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
