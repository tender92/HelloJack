package com.tender.hellojack.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
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

    public static void showWaitingDialog(Context context, String tip) {

    }

    public static class WaitingDialog extends Dialog {

        private String content;
        private Context context;

        public WaitingDialog(@NonNull Context con, @NonNull String tip) {
            super(con, R.style.hj_dialog);
            content = tip;
            context = con;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View root = View.inflate(context, R.layout.hj_dialog_waiting, null);
            TextView tvTip = (TextView) root.findViewById(R.id.tv_dialog_waiting_tip);
            tvTip.setText(content);
        }
    }
}
