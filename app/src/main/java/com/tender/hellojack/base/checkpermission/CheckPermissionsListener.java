package com.tender.hellojack.base.checkpermission;

import java.util.List;

/**
 * @author：Bro0cL
 * @date: 2017/8/31
 */

public interface CheckPermissionsListener {
    void onGranted();
    void onDenied(List<String> permissions);
}
