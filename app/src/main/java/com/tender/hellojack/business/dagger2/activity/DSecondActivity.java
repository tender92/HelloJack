package com.tender.hellojack.business.dagger2.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.bean.ClothHandler;
import com.tender.hellojack.business.dagger2.component.DaggerSecondComponent;
import com.tender.hellojack.manager.MyApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boyu on 2018/3/23.
 */

public class DSecondActivity extends AppCompatActivity {

    @Inject
    Cloth greenCloth;

    @Inject
    ClothHandler clothHandler;

    @BindView(R.id.tv_second_content)
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hj_activity_dagger_second);
        ButterKnife.bind(this);

        DaggerSecondComponent.builder()
                .baseComponent(((MyApplication)getApplication()).getBaseComponent())
                .build()
                .inject(this);

        tvContent.setText("绿布料加工后变成了" + clothHandler.handle(greenCloth) + "\nclothHandler地址:" + clothHandler);
    }
}
