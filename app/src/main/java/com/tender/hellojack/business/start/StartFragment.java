package com.tender.hellojack.business.start;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tender.hellojack.Const;
import com.tender.hellojack.R;
import com.tender.hellojack.business.home.HomeActivity;
import com.tender.hellojack.utils.DialogUtil;
import com.tender.tools.TenderLog;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;

/**
 * Created by boyu on 2017/12/7.
 */

public class StartFragment extends RxFragment {

    private String permissionInfo = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_start, container, false);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermissions();
    }

    public boolean onBackPressed() {
        return false;
    }

    @TargetApi(23)
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();

            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            /*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "读写权限被拒绝，进入设置页面开通 \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), Const.IRCode.SDK_PERMISSION_REQUEST);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    TenderLog.e(e.getMessage());
                }
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                TenderLog.e(e.getMessage());
            }
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionList, String permission) {
        if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {// 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Const.IRCode.SDK_PERMISSION_REQUEST) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                DialogUtil.showConfirmDialog(getActivity(), permissionInfo, "设置", "取消",
                        new Runnable() {
                            @Override
                            public void run() {
                                Uri uri = Uri.parse("package:" + getActivity().getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                                startActivity(intent);
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    TenderLog.e(e.getMessage());
                }
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        }
    }
}
