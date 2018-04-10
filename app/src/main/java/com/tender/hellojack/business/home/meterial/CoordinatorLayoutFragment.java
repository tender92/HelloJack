package com.tender.hellojack.business.home.meterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.home.meterial.swipelistview.SwipeListActivity;
import com.tender.hellojack.business.home.meterial.tablayout.TabLayoutActivity;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.TenderLog;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class CoordinatorLayoutFragment extends BaseFragment implements CoordinatorLayoutContract.View {

    private CoordinatorLayoutContract.Presenter mPresenter;
    private TextView tvContent, tvThree, tvFive;
    private View mBottomView;

    private BottomSheetBehavior mBehavior;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_coordinator_layout, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.toolbar);
        tvContent = (TextView) root.findViewById(R.id.textView);
        mBottomView = root.findViewById(R.id.bottom_sheet);
        tvThree = (TextView) root.findViewById(R.id.tv_bottom_sheet_three);
        tvFive = (TextView) root.findViewById(R.id.tv_bottom_sheet_five);

        RxView.clicks(tvThree).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, TabLayoutActivity.class));
            }
        });

        RxView.clicks(tvFive).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, SwipeListActivity.class));
            }
        });
        return root;
    }

    @Override
    public void initUIData() {
        mBehavior = BottomSheetBehavior.from(mBottomView);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                TenderLog.d("BottomSheet State:" + newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                TenderLog.d("BottomSheet Slide Offset:" + slideOffset);
            }
        });
        tvContent.setText(R.string.hj_text);
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
    public void setPresenter(CoordinatorLayoutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("Material Design");
            mActivity.setSupportActionBar(mToolbar);
        }
    }
}
