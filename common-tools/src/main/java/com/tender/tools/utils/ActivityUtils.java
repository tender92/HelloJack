/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tender.tools.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * 显示目标fragment，并隐藏其他的fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     * @param fragmentsToHide
     */
    public static void showFragment(FragmentManager fragmentManager,
                                    Fragment fragment, int frameId, List<Fragment> fragmentsToHide) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentsToHide != null) {
            for (Fragment f : fragmentsToHide) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }

        if (!fragment.isAdded()) {
            transaction.add(frameId, fragment);
        }

        transaction.show(fragment);
        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager,
                                       Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
    }

    /**
     * 移除fragment
     * @param fragmentManager
     * @param fragment
     */
    public static void removeFragment(FragmentManager fragmentManager,
                                      Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
    }

}
