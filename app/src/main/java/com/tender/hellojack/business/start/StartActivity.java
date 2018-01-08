package com.tender.hellojack.business.start;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.tender.hellojack.base.checkpermission.CheckPermissionsActivity;
import com.tender.hellojack.base.checkpermission.CheckPermissionsListener;
import com.tender.hellojack.business.home.HomeActivity;
import com.tender.hellojack.business.login.LoginActivity;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.DialogUtil;
import com.tender.tools.TenderLog;
import com.tender.tools.utils.DisplayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by boyu on 2017/12/7.
 */

public class StartActivity extends CheckPermissionsActivity implements CheckPermissionsListener {

//    private StartFragment contentFragment;

    @Override
    protected void initLayout() {
//        setContentView(R.layout.hj_activity_model);
    }

    @Override
    protected void initToolbar() {
//        hideToolbar();
//        View view = findViewById(R.id.hj_view_fill_up);
//        view.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        PrefManager.saveScreenWidth(metrics.widthPixels - 2 * DisplayUtil.dip2px(this, 20));
        PrefManager.saveScreenHeight(metrics.heightPixels);

//        contentFragment = (StartFragment) getSupportFragmentManager().findFragmentById(R.id.hj_contentFrame);
//        if (contentFragment == null) {
//            contentFragment = new StartFragment();
//            ActivityUtils.showFragment(getSupportFragmentManager(), contentFragment, R.id.hj_contentFrame, null);
//        }

        testIn();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(this, neededPermissions, this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onGranted() {
        delayToLogin();
    }

    @Override
    public void onDenied(List<String> permissions) {
        StringBuilder deniedPermissions = new StringBuilder();
        for (int i = 0; i < permissions.size(); i ++) {
            deniedPermissions.append(permissions);
        }
        deniedPermissions.append("\n权限被拒绝，进入设置页面开通 \n");
        DialogUtil.showConfirmDialog(StartActivity.this, deniedPermissions.toString(), "设置", "取消",
                new Runnable() {
                    @Override
                    public void run() {
                        Uri uri = Uri.parse("package:" + StartActivity.this.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                        startActivity(intent);
                    }
                }, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
    }

    public void delayToLogin() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            TenderLog.e(e.getMessage());
        }
        startActivity(new Intent(this, HomeActivity.class));
//        startActivity(new Intent(this, LoginActivity.class));
    }


    private void testIn() {
        JSONObject jsonObject = new JSONObject();
        JSONArray arrayName = new JSONArray().put("小明").put("小刘").put("小丕");
        JSONArray arraySex = new JSONArray().put(1).put(0);
        JSONArray arrayFriends = new JSONArray().put(new Exception("你错了！")).put(new Exception("我错了"));
        try {
            jsonObject.put("test", "textIn");
            jsonObject.put("name", arrayName);
            jsonObject.put("sex", arraySex);
            jsonObject.put("friend", arrayFriends);
            TenderLog.d(jsonObject.toString());
        } catch (JSONException e) {
            TenderLog.e(e.getMessage());
        }
    }
}
