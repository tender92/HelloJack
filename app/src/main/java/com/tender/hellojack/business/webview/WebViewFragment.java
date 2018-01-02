package com.tender.hellojack.business.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.tools.IntentConst;
import com.tender.tools.TenderLog;
import com.tender.tools.views.ProgressWebView;

public class WebViewFragment extends BaseFragment implements WebViewContract.View {

    private WebViewContract.Presenter mPresenter;

    private ProgressWebView pwvWebView;

    private boolean isLoading = false;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_web_view, container, false);

        pwvWebView = (ProgressWebView) root.findViewById(R.id.pwv_web_view);
        return root;
    }

    @Override
    public void initUIData() {
        String url = mActivity.getIntent().getStringExtra(IntentConst.IRParam.WEB_VIEW_URL);
        WebSettings settings = pwvWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.supportMultipleWindows();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() - 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        pwvWebView.setVerticalScrollbarOverlay(true);
        pwvWebView.setWebViewClient(new MyWebViewClient());
        pwvWebView.loadUrl(url);
    }

    @Override
    public void showNetLoading(String tip) {
        super.showWaitingDialog(tip);
    }

    @Override
    public void hideNetLoading() {
        super.hideWaitingDialog();
    }

    @Override
    public void setPresenter(WebViewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {
        isLoading = false;
        //如果当前浏览器可以后退，则后退上一个页面
        if (pwvWebView.canGoBack()) {
            pwvWebView.goBack();
        } else {
            mActivity.finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pwvWebView != null) {
            pwvWebView.removeAllViews();
            try {
                pwvWebView.destroy();
            } catch (Exception e) {
                TenderLog.e(e.getMessage());
            }
            pwvWebView = null;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //在自己浏览器中跳转
            pwvWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isLoading = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isLoading = false;
        }
    }
}
