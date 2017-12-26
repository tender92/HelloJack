package com.tender.hellojack.business.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.lqr.optionitemview.OptionItemView;
import com.tender.hellojack.R;
import com.tender.hellojack.ShowBigImageActivity;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.myinfo.changename.ChangeNameActivity;
import com.tender.hellojack.business.myinfo.changesignature.ChangeSignatureActivity;
import com.tender.hellojack.business.myinfo.qrcodecard.QRCodeCardActivity;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.DialogUtil;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.string.StringUtil;
import com.tender.umengshare.DataAnalyticsManager;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
import rx.functions.Action1;

/**
 * Created by boyu on 2017/12/7.
 */

public class MyInfoFragment extends BaseFragment implements MyInfoContract.View, CityPickerListener {

    private MyInfoContract.Presenter mPresenter;

    private LinearLayout llHeader;
    private ImageView ivHeader;
    private OptionItemView oivName, oivAccount, oivQRCode, oivAddress, oivGender, oivRegion, oivSignature;

    private DialogUtil.CustomDialog genderDialog;
    private TextView tvMale, tvFemale;
    private Drawable mGenderSelect, mGenderUnSelect;

    //省市区联动选择器
    private CityPicker mCityPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_my_info, container, false);
        llHeader = root.findViewById(R.id.ll_my_info_header);
        ivHeader = root.findViewById(R.id.iv_my_info_header);
        oivName = root.findViewById(R.id.oiv_my_info_name);
        oivAccount = root.findViewById(R.id.oiv_my_info_account);
        oivQRCode = root.findViewById(R.id.oiv_my_info_qr_code);
        oivAddress = root.findViewById(R.id.oiv_my_info_address);
        oivGender = root.findViewById(R.id.oiv_my_info_sex);
        oivRegion = root.findViewById(R.id.oiv_my_info_region);
        oivSignature = root.findViewById(R.id.oiv_my_info_signature);

        RxView.clicks(llHeader).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                DataAnalyticsManager.getInstance().onEvent(mActivity, "event_myinfo_change_header");
                startActivityForResult(new Intent(mActivity, ImageGridActivity.class),
                        IntentConst.IRCode.MY_INFO_IMAGE_PICKER);
            }
        });
        RxView.clicks(ivHeader).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                DataAnalyticsManager.getInstance().onEvent(mActivity, "event_myinfo_show_header");
                startActivity(new Intent(mActivity, ShowBigImageActivity.class));
            }
        });
        RxView.clicks(oivName).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, ChangeNameActivity.class));
            }
        });
        RxView.clicks(oivQRCode).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, QRCodeCardActivity.class));
            }
        });
        RxView.clicks(oivAddress).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mCityPicker.show();
            }
        });
        RxView.clicks(oivGender).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showGenderDialog();
            }
        });
        RxView.clicks(oivRegion).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivityForResult(new Intent(mActivity, CityPickerActivity.class), IntentConst.IRCode.MY_INFO_CITY_PICKER);
            }
        });
        RxView.clicks(oivSignature).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(mActivity, ChangeSignatureActivity.class));
            }
        });
        return root;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCityPicker = new CityPicker(mActivity, this);

        mGenderSelect = mActivity.getResources().getDrawable(R.mipmap.hj_list_selected);
        mGenderUnSelect = mActivity.getResources().getDrawable(R.mipmap.hj_list_unselected);
        mGenderSelect.setBounds(0, 0, mGenderSelect.getMinimumWidth(), mGenderSelect.getMinimumHeight());
        mGenderUnSelect.setBounds(0, 0, mGenderUnSelect.getMinimumWidth(), mGenderUnSelect.getMinimumHeight());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConst.IRCode.MY_INFO_IMAGE_PICKER &&
                resultCode == ImagePicker.RESULT_CODE_ITEMS) { //返回多张照片
            if (data != null) {
                ArrayList<ImageItem> imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageList != null && imageList.size() > 0) {
                    String path = imageList.get(0).path;
                    PrefManager.setUserHeaderPath(path);
                    ImageLoaderUtil.loadLocalImage(path, ivHeader);
                }
            }
        } else if (requestCode == IntentConst.IRCode.MY_INFO_CITY_PICKER &&
                resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                oivRegion.setRightText(city);
                PrefManager.setUserRegion(city);
            }
        }
    }

    @Override
    protected void onBackPressed() {
        if (mCityPicker.isShow()) {
            mCityPicker.close();
        } else {
            mActivity.finish();
        }
    }

    @Override
    public void setPresenter(MyInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void initUIData() {
        String headerPath = PrefManager.getUserHeaderPath();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        }
        oivName.setRightText(PrefManager.getUserName());
        oivAccount.setRightText(PrefManager.getUserAccount());
        oivGender.setRightText(PrefManager.getUserGender() == 1 ? "男" : "女");
        oivAddress.setRightText(PrefManager.getUserAddress());
        oivRegion.setRightText(PrefManager.getUserRegion());
        oivSignature.setRightText(PrefManager.getUserSignature());
    }

    @Override
    public void getCity(String name) {
        oivAddress.setRightText(name);
        PrefManager.setUseAddress(name);
    }

    @Override
    public void showGenderDialog() {
        if (genderDialog == null) {
            View root = LayoutInflater.from(mActivity).inflate(R.layout.hj_layout_select_gender, null);
            tvMale = root.findViewById(R.id.tv_gender_male);
            tvFemale = root.findViewById(R.id.tv_gender_female);
            RxView.clicks(tvMale).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    PrefManager.setUserGender(1);
                    updateGenderDialog(1);
                    genderDialog.dismiss();
                    oivGender.setRightText("男");
                }
            });
            RxView.clicks(tvFemale).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    PrefManager.setUserGender(0);
                    updateGenderDialog(0);
                    genderDialog.dismiss();
                    oivGender.setRightText("女");
                }
            });
            genderDialog = new DialogUtil.CustomDialog(mActivity, root);

        }
        updateGenderDialog(PrefManager.getUserGender());
        genderDialog.show();
    }

    private void updateGenderDialog(int gender) {
        if (1 == gender) {
            tvMale.setCompoundDrawables(null, null, mGenderSelect, null);
            tvFemale.setCompoundDrawables(null, null, mGenderUnSelect, null);
        } else {
            tvMale.setCompoundDrawables(null, null, mGenderUnSelect, null);
            tvFemale.setCompoundDrawables(null, null, mGenderSelect, null);
        }
    }
}
