package com.tender.hellojack.business.dagger2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.business.dagger2.bean.Cloth;
import com.tender.hellojack.business.dagger2.bean.Cloths;
import com.tender.hellojack.business.dagger2.bean.Shoe;
import com.tender.hellojack.business.dagger2.component.DaggerFirstComponent;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by boyu on 2018/3/23.
 */

public class DFirstActivity extends AppCompatActivity {

    @BindView(R.id.btn_first_second)
    Button btnSecond;
    @BindView(R.id.btn_first_third)
    Button btnThird;
    @BindView(R.id.btn_first_fourth)
    Button btnFourth;
    @BindView(R.id.tv_first_content)
    TextView tvContent;

    @Inject
    Shoe shoe;

    @Inject
    @Named("red")
    Cloths redCloths;

    @Inject
    @Named("blue")
    Cloths blueCloths;

    @Inject
    Cloth cloth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hj_activity_dagger_first);
        ButterKnife.bind(this);

        DaggerFirstComponent.builder().build().inject(this);
        tvContent.setText("我有" + shoe + "和" + redCloths + "和" + blueCloths + ":" + (cloth == redCloths.getCloth()));

        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DFirstActivity.this, DSecondActivity.class));
            }
        });

        btnThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DFirstActivity.this, DThirdActivity.class));
            }
        });

        btnFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DFirstActivity.this, DFourthActivity.class));
            }
        });
    }
}
