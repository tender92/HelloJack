package com.tender.hellojack.business.home.once;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.tools.utils.ui.DialogUtil;

import java.util.concurrent.TimeUnit;

import jonathanfinerty.once.Amount;
import jonathanfinerty.once.Once;

import static jonathanfinerty.once.Once.markDone;

public class OnceFragment extends BaseFragment implements OnceContract.View {

    private static final String SHOW_NEW_SESSION_DIALOG = "NewSessionDialog";
    private static final String SHOW_FRESH_INSTALL_DIALOG = "FreshInstallDialog";
    private static final String SHOW_NEW_VERSION_DIALOG = "NewVersionDialog";
    private static final String SHOW_MINUTE_DIALOG = "OncePerMinuteDialog";
    private static final String SHOW_SECOND_DIALOG = "OncePerSecondDialog";

    private OnceContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Once.markDone("Activity Start");
        View root = inflater.inflate(R.layout.hj_fragment_once, container, false);

        root.findViewById(R.id.btn_once_session_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Once.beenDone(Once.THIS_APP_SESSION, SHOW_NEW_SESSION_DIALOG)) {
                    showDialog("This dialog should only appear once per app session");
                    Once.markDone(SHOW_NEW_SESSION_DIALOG);
                }
            }
        });
        root.findViewById(R.id.btn_once_app_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Once.beenDone(Once.THIS_APP_INSTALL, SHOW_FRESH_INSTALL_DIALOG)) {
                    showDialog("This dialog should only appear once per app installation");
                    Once.markDone(SHOW_FRESH_INSTALL_DIALOG);
                }
            }
        });
        root.findViewById(R.id.btn_once_app_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Once.beenDone(Once.THIS_APP_VERSION, SHOW_NEW_VERSION_DIALOG)) {
                    showDialog("This dialog should only appear once per app version");
                    markDone(SHOW_NEW_VERSION_DIALOG);
                }
            }
        });
        root.findViewById(R.id.btn_once_execute_one_per_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Once.beenDone(TimeUnit.MINUTES, 1, SHOW_MINUTE_DIALOG)) {
                    showDialog("This dialog should only appear once per minute");
                    markDone(SHOW_MINUTE_DIALOG);
                }
            }
        });
        root.findViewById(R.id.btn_once_execute_one_per_seconds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Once.beenDone(1000l, SHOW_SECOND_DIALOG)) {
                    showDialog("This dialog should only appear once per second");
                    markDone(SHOW_SECOND_DIALOG);
                }
            }
        });
        root.findViewById(R.id.btn_once_execute_one_per_three_times).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonPressedTag = "button pressed";
                Once.markDone(buttonPressedTag);
                if (Once.beenDone(buttonPressedTag, Amount.exactly(3))) {
                    showDialog("This dialog should only appear once per second");
                    Once.clearDone(buttonPressedTag);
                }
            }
        });
        root.findViewById(R.id.btn_once_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Once.clearAll();
            }
        });

        return root;
    }

    @Override
    public void initUIData() {

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
    public void setPresenter(OnceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showDialog(String message) {
        showMaterialDialog("提示", message, "确定", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMaterialDialog();
            }
        }, null);
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);
            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

            TextView title = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
            title.setText("一次性框架使用");
        }
    }
}
