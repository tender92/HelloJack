package com.tender.hellojack.business.translate.listener;

import android.content.ClipData;
import android.content.Context;
import android.text.ClipboardManager;
import android.text.TextUtils;

import com.tender.tools.utils.ui.UIUtil;

/**
 * Created by boyu on 2018/2/1.
 */

public class ClipboardManagerImpl9 extends ClipboardManagerCompat implements Runnable {
    private ClipboardManager mClipboardManager;
    private CharSequence mLastData;
    private boolean mWorking = false;

    public ClipboardManagerImpl9(Context context) {
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void run() {
        if (mWorking) {
            CharSequence data = getText();
            check(data);
            UIUtil.getMainHandle().postDelayed(this, 1000);
        }
    }

    @Override
    public void setPrimaryClip(ClipData clip) {
        mClipboardManager.setText(clip.toString());
    }

    @Override
    public CharSequence getText() {
        return mClipboardManager.getText();
    }

    @Override
    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.addPrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 1) {
                startListen();
            }
        }
    }

    @Override
    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.removePrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 0) {
                stopListen();
            }
        }
    }

    private void check(CharSequence data) {
        if (TextUtils.isEmpty(mLastData) && TextUtils.isEmpty(data)) {
            return;
        }

        if (!TextUtils.isEmpty(mLastData) && mLastData.equals(data)) {
            return;
        }
        mLastData = data;
        notifyPrimaryClipChanged();
    }

    private void startListen() {
        mWorking = true;
        UIUtil.getMainHandle().postDelayed(this, 10000);
    }

    private void stopListen() {
        mWorking = false;
        UIUtil.getMainHandle().removeCallbacks(this);
    }
}
