package com.tender.hellojack.business.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.myinfo.changename.ChangeNameActivity;
import com.tender.hellojack.business.myinfo.changesignature.ChangeSignatureActivity;
import com.tender.hellojack.business.myinfo.qrcodecard.QRCodeCardActivity;
import com.tender.hellojack.business.showimage.ShowImageActivity;
import com.tender.hellojack.model.UserInfo;
import com.tender.tools.utils.ui.DialogUtil;
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

    private String mineAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_my_info, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        llHeader = (LinearLayout) root.findViewById(R.id.ll_my_info_header);
        ivHeader = (ImageView) root.findViewById(R.id.iv_my_info_header);
        oivName = (OptionItemView) root.findViewById(R.id.oiv_my_info_name);
        oivAccount = (OptionItemView) root.findViewById(R.id.oiv_my_info_account);
        oivQRCode = (OptionItemView) root.findViewById(R.id.oiv_my_info_qr_code);
        oivAddress = (OptionItemView) root.findViewById(R.id.oiv_my_info_address);
        oivGender = (OptionItemView) root.findViewById(R.id.oiv_my_info_sex);
        oivRegion = (OptionItemView) root.findViewById(R.id.oiv_my_info_region);
        oivSignature = (OptionItemView) root.findViewById(R.id.oiv_my_info_signature);

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
                Intent intent = new Intent(mActivity, ShowImageActivity.class);
                intent.putExtra(IntentConst.IRParam.MINE_ACCOUNT, mineAccount);
                startActivity(intent);
            }
        });
        RxView.clicks(oivName).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, ChangeNameActivity.class);
                intent.putExtra(IntentConst.IRParam.MINE_ACCOUNT, mineAccount);
                startActivity(intent);
            }
        });
        RxView.clicks(oivQRCode).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, QRCodeCardActivity.class);
                intent.putExtra(IntentConst.IRParam.MINE_ACCOUNT, mineAccount);
                startActivity(intent);
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
                Intent intent = new Intent(mActivity, ChangeSignatureActivity.class);
                intent.putExtra(IntentConst.IRParam.MINE_ACCOUNT, mineAccount);
                startActivity(intent);
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

        Intent intent = mActivity.getIntent();
        if (intent != null) {
            mineAccount = intent.getStringExtra(IntentConst.IRParam.MINE_ACCOUNT);
        }

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
                    mPresenter.updateMineAvatar(mineAccount, path);
                    ImageLoaderUtil.loadLocalImage(path, ivHeader);
                }
            }
        } else if (requestCode == IntentConst.IRCode.MY_INFO_CITY_PICKER &&
                resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                oivRegion.setRightText(city);
                mPresenter.updateMineRegion(mineAccount, city);
            }
        }
    }
    public void onBackPressed() {
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
        mPresenter.getMineInfo(mineAccount);
    }

    @Override
    public void getCity(String name) {
        oivAddress.setRightText(name);
        mPresenter.updateMineAddress(mineAccount, name);
    }

    @Override
    public void showGenderDialog() {
        if (genderDialog == null) {
            View root = LayoutInflater.from(mActivity).inflate(R.layout.hj_layout_select_gender, null);
            tvMale = (TextView) root.findViewById(R.id.tv_gender_male);
            tvFemale = (TextView) root.findViewById(R.id.tv_gender_female);
            RxView.clicks(tvMale).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    mPresenter.updateMineGender(mineAccount,1);
                    updateGenderDialog(1);
                    genderDialog.dismiss();
                    oivGender.setRightText("男");
                }
            });
            RxView.clicks(tvFemale).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    mPresenter.updateMineGender(mineAccount,0);
                    updateGenderDialog(0);
                    genderDialog.dismiss();
                    oivGender.setRightText("女");
                }
            });
            genderDialog = new DialogUtil.CustomDialog(mActivity, root);

        }
        updateGenderDialog(mPresenter.getMineGender(mineAccount));
        genderDialog.show();
    }

    @Override
    public void showMineInfo(UserInfo mineInfo) {
        String headerPath = mineInfo.getAvatar();
        if (StringUtil.hasValue(headerPath)) {
            ImageLoaderUtil.loadLocalImage(headerPath, ivHeader);
        } else {
            ivHeader.setImageResource(R.mipmap.hj_mine_default_header);
        }
        oivName.setRightText(mineInfo.getName());
        oivAccount.setRightText(mineInfo.getAccount());
        oivGender.setRightText(mineInfo.getGender() == 1 ? "男" : "女");
        oivAddress.setRightText(mineInfo.getAddress());
        oivRegion.setRightText(mineInfo.getRegion());
        oivSignature.setRightText(mineInfo.getSignature());
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

            mTitle.setText("个人信息");
        }
    }
}
