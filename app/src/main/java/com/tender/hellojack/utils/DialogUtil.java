package com.tender.hellojack.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tender.hellojack.R;
import com.tender.hellojack.manager.PrefManager;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    public static void showConfirmDialog(Context con, String content, String yes, String no,
                                         final Runnable confirmAction, final Runnable cancelAction) {
        if (con != null) {
            new SelectedDialog(con, content, false, yes, no, confirmAction, cancelAction).show();
        }
    }

    /**
     * 加载框
     */
    public static class WaitingDialog extends Dialog {

        private String content;
        private Context context;

        public WaitingDialog(@NonNull Context con, @NonNull String tip) {
            super(con, R.style.hj_tools_dialog);
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

    public static class CustomDialog extends Dialog {

        public CustomDialog(Context context, View layout) {
            super(context, R.style.hj_tools_dialog);
            setContentView(layout);
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }

        public CustomDialog(Context context, int width, int height, View layout) {
            super(context, R.style.hj_tools_dialog);
            setContentView(layout);

            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            params.width = (int) (metrics.density * width);
            params.height = (int) (metrics.density * height);
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
    }

    public static class SelectedDialog extends Dialog implements
            android.view.View.OnClickListener {
        private Button selectDialog, exitDialog;
        private TextView longTextView;
        private LinearLayout btn_layout;
        private boolean scroll;
        private Runnable runnableY, runnableN;
        private String content, yesStr, noStr;
        private Context context;

        public SelectedDialog(Context context, String content, boolean scroll,
                              String yesStr, String noStr, Runnable runnableY,
                              Runnable runnableN) {
            super(context, R.style.hj_tools_dialog);
            this.context = context;
            this.content = content;
            this.scroll = scroll;
            this.yesStr = yesStr;
            this.noStr = noStr;
            this.runnableY = runnableY;
            this.runnableN = runnableN;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            try {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.hj_dialog_select_yes_or_no);
                selectDialog = findViewById(R.id.btn_prompt_two_yes);
                btn_layout = findViewById(R.id.btn_layout);
                ScrollView scrollView = findViewById(R.id.scroll_prompt_two_context);
                TextView shortTextView = findViewById(R.id.txt_prompt_two_content_short);
                exitDialog = findViewById(R.id.btn_prompt_two_no);
//                selectDialog.setBackgroundResource(R.drawable.hj_dialog_white_blue_left);
//                exitDialog.setBackgroundResource(R.drawable.hj_dialog_white_blue_right);
                longTextView = findViewById(R.id.txt_prompt_two_context_long);

                selectDialog.setTextSize(TypedValue.COMPLEX_UNIT_PX, context
                        .getResources().getDimension(R.dimen.hj_text_big_size));
                exitDialog.setTextSize(TypedValue.COMPLEX_UNIT_PX, context
                        .getResources().getDimension(R.dimen.hj_text_big_size));
                longTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context
                        .getResources().getDimension(R.dimen.hj_text_big_size));
                shortTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context
                        .getResources().getDimension(R.dimen.hj_text_big_size));

                ViewGroup.LayoutParams btnLp = btn_layout.getLayoutParams();
                btnLp.width = (int) (PrefManager.getScreenWidth() * 0.7334);
                btnLp.height = (int) (PrefManager.getScreenHeight() * 0.0792f);
                btn_layout.setLayoutParams(btnLp);
                if (scroll) {
                    scrollView.setVisibility(View.VISIBLE);
                    shortTextView.setVisibility(View.GONE);
                } else {
                    scrollView.setVisibility(View.GONE);
                    shortTextView.setVisibility(View.VISIBLE);
                }

                ViewGroup.LayoutParams lp = shortTextView.getLayoutParams();
                lp.width = (int) (PrefManager.getScreenHeight() * 0.74);
                lp.height = (int) (lp.width * 0.37);
                selectDialog.setHeight((int) (lp.height * 0.35));
                exitDialog.setHeight((int) (lp.height * 0.35));

                shortTextView.setLayoutParams(lp);
                shortTextView.setText(content);

                selectDialog.setText(yesStr);
                exitDialog.setText(noStr);
                selectDialog.setOnClickListener(this);
                exitDialog.setOnClickListener(this);
                setCancelable(false);
                setCanceledOnTouchOutside(false);

            } catch (RuntimeException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                throw e;
            }
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_prompt_two_yes) {
                this.dismiss();
                if (this.runnableY != null) {
                    this.runnableY.run();
                }

            } else if (id == R.id.btn_prompt_two_no) {
                this.dismiss();
                if (this.runnableN != null) {
                    this.runnableN.run();
                }

            }

        }
    }
}
