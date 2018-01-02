package com.tender.hellojack.business.mine.cardpacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.mine.cardpacket.coupon.CouponActivity;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class CardPacketFragment extends BaseFragment implements CardPacketContract.View {

    private CardPacketContract.Presenter mPresenter;

    private CardView cvFriendCoupon;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_card_packet, container, false);
        cvFriendCoupon = (CardView) root.findViewById(R.id.cv_card_packet_friend_coupon);

        RxView.clicks(cvFriendCoupon).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, CouponActivity.class));
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
    public void setPresenter(CardPacketContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }
}
