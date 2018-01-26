package com.tender.hellojack.business.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.business.setting.about.AboutWeChatFragment;
import com.tender.hellojack.business.setting.about.AboutWeChatPresenter;
import com.tender.hellojack.business.setting.accountsafety.AccountSafetyFragment;
import com.tender.hellojack.business.setting.accountsafety.AccountSafetyPresenter;
import com.tender.hellojack.business.setting.chat.ChatFragment;
import com.tender.hellojack.business.setting.chat.ChatPresenter;
import com.tender.hellojack.business.setting.common.CommonFragment;
import com.tender.hellojack.business.setting.common.CommonPresenter;
import com.tender.hellojack.business.setting.newmessage.NewMessageNotifyFragment;
import com.tender.hellojack.business.setting.newmessage.NewMessageNotifyPresenter;
import com.tender.hellojack.business.setting.nodisturb.NoDisturbFragment;
import com.tender.hellojack.business.setting.nodisturb.NoDisturbPresenter;
import com.tender.hellojack.business.setting.privacy.PrivacyFragment;
import com.tender.hellojack.business.setting.privacy.PrivacyPresenter;
import com.tender.hellojack.manager.MyApplication;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ActivityUtils;
import com.tender.tools.utils.ui.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boyu on 2018/1/7.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.dl_setting_layout)
    DrawerLayout dlSetting;
    @BindView(R.id.nm_setting_navigation)
    NavigationView nmSetting;

    private int currentItem;
    private int lastItem = 0;

    private List<Fragment> fragments;
    private NewMessageNotifyFragment newMessageNotifyFragment;
    private NoDisturbFragment noDisturbFragment;
    private ChatFragment chatFragment;
    private PrivacyFragment privacyFragment;
    private CommonFragment commonFragment;
    private AccountSafetyFragment accountSafetyFragment;
    private AboutWeChatFragment aboutWeChatFragment;

    private DialogUtil.CustomDialog exitDialog;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        updateTitle("新消息提醒");
        mToolbar.setNavigationIcon(R.mipmap.hj_setting_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlSetting.openDrawer(Gravity.START);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dlSetting.setStatusBarBackground(R.drawable.hj_shape_toolbar_bg);
        if (nmSetting != null) {
            nmSetting.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    lastItem = currentItem;
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_item_new_message) {
                        currentItem = 0;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("新消息提醒");
                        if (newMessageNotifyFragment == null) {
                            newMessageNotifyFragment = new NewMessageNotifyFragment();
                            fragments.add(newMessageNotifyFragment);
                            new NewMessageNotifyPresenter(Injection.provideRepository(),
                                    newMessageNotifyFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                newMessageNotifyFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_no_disturb) {
                        currentItem = 1;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("勿扰模式");
                        if (noDisturbFragment == null) {
                            noDisturbFragment = new NoDisturbFragment();
                            fragments.add(noDisturbFragment);
                            new NoDisturbPresenter(Injection.provideRepository(),
                                    noDisturbFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                noDisturbFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_chat) {
                        currentItem = 2;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("聊天");
                        if (chatFragment == null) {
                            chatFragment = new ChatFragment();
                            fragments.add(chatFragment);
                            new ChatPresenter(Injection.provideRepository(),
                                    chatFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                chatFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_privacy) {
                        currentItem = 3;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("隐私");
                        if (privacyFragment == null) {
                            privacyFragment = new PrivacyFragment();
                            fragments.add(privacyFragment);
                            new PrivacyPresenter(Injection.provideRepository(),
                                    privacyFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                privacyFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_common) {
                        currentItem = 4;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("通用");
                        if (commonFragment == null) {
                            commonFragment = new CommonFragment();
                            fragments.add(commonFragment);
                            new CommonPresenter(Injection.provideRepository(),
                                    commonFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                commonFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_safety) {
                        currentItem = 5;
                        if (currentItem == lastItem) {
                            return false;
                        }
                        updateTitle("账号与安全");
                        if (accountSafetyFragment == null) {
                            accountSafetyFragment = new AccountSafetyFragment();
                            fragments.add(accountSafetyFragment);
                            new AccountSafetyPresenter(Injection.provideRepository(),
                                    accountSafetyFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                accountSafetyFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_about) {
                        currentItem = 6;
                        if (currentItem == lastItem) {
                            dlSetting.closeDrawers();
                            return false;
                        }
                        updateTitle("关于微信");
                        if (aboutWeChatFragment == null) {
                            aboutWeChatFragment = new AboutWeChatFragment();
                            fragments.add(aboutWeChatFragment);
                            new AboutWeChatPresenter(Injection.provideRepository(),
                                    aboutWeChatFragment,
                                    Injection.provideSchedule());
                        }
                        ActivityUtils.showFragment(getSupportFragmentManager(),
                                aboutWeChatFragment, R.id.fl_setting_content, fragments);
                    } else if (itemId == R.id.menu_item_exit) {
                        if (exitDialog == null) {
                            View exitView = View.inflate(SettingActivity.this, R.layout.hj_dialog_setting_exit, null);
                            exitDialog = new DialogUtil.CustomDialog(SettingActivity.this, exitView);
                            exitView.findViewById(R.id.tv_setting_exit_account).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    exitDialog.dismiss();
                                }
                            });
                            exitView.findViewById(R.id.tv_setting_exit_app).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    exitDialog.dismiss();
                                    MyApplication.exit(0);
                                }
                            });
                        }
                        exitDialog.show();
                    }
                    dlSetting.closeDrawers();
                    return true;
                }
            });
        }

        currentItem = 0;
        fragments = new ArrayList<>();
        if (newMessageNotifyFragment == null) {
            newMessageNotifyFragment = new NewMessageNotifyFragment();
            fragments.add(newMessageNotifyFragment);
            new NewMessageNotifyPresenter(Injection.provideRepository(),
                    newMessageNotifyFragment,
                    Injection.provideSchedule());
            ActivityUtils.showFragment(getSupportFragmentManager(),
                    newMessageNotifyFragment, R.id.fl_setting_content, null);
        }
    }

    @Override
    public void onBackPressed() {
        if (dlSetting.isDrawerOpen(Gravity.START)) {
            dlSetting.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
