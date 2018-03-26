package com.tender.hellojack.business.dagger2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.bean.ClothHandler;
import com.tender.hellojack.business.dagger2.module.FourModule;
import com.tender.hellojack.manager.MyApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boyu on 2018/3/23.
 */

public class DFourthActivity extends AppCompatActivity {
    @BindView(R.id.tv_fourth_content)
    TextView tvContent;

    @Inject
    Cloth blackCloth;

    @Inject
    ClothHandler clothHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hj_activity_dagger_fourth);
        ButterKnife.bind(this);

        ((MyApplication)getApplication()).getBaseComponent().getSubFourComponent(new FourModule()).inject(this);
        tvContent.setText("黑布料加工后变成了" + clothHandler.handle(blackCloth) + "\nclothHandler地址:" + clothHandler);
    }
}
