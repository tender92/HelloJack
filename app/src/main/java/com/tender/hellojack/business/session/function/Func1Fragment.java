package com.tender.hellojack.business.session.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.utils.ScheduleProvider;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by boyu on 2017/12/27.
 */

public class Func1Fragment extends BaseFragment {

    private LinearLayout llImage, llRecord, llRedPacket, llTransfer, llLocation, llVideo,
        llCollection, llBusinessCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_session_func1, container, false);
        llImage = root.findViewById(R.id.ll_session_fun_image);
        llRecord = root.findViewById(R.id.ll_session_fun_record);
        llRedPacket = root.findViewById(R.id.ll_session_fun_red_packet);
        llTransfer = root.findViewById(R.id.ll_session_fun_transfer);
        llLocation = root.findViewById(R.id.ll_session_fun_location);
        llVideo = root.findViewById(R.id.ll_session_fun_video);
        llCollection = root.findViewById(R.id.ll_session_fun_collection);
        llBusinessCard = root.findViewById(R.id.ll_session_fun_business_card);

        RxView.clicks(llImage).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        });
        return root;
    }

    @Override
    protected void onBackPressed() {

    }
}
