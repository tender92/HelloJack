package com.tender.hellojack.business.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.litesuits.orm.LiteOrm;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.hellojack.business.mine.MineFragment;
import com.tender.hellojack.business.mine.MinePresenter;
import com.tender.hellojack.business.translate.TranslateFragment;
import com.tender.hellojack.business.translate.TranslatePresenter;
import com.tender.hellojack.business.translate.service.ApiBaiDu;
import com.tender.hellojack.business.translate.service.TranslateApiProvider;
import com.tender.hellojack.business.translate.service.WrapApiService;
import com.tender.hellojack.manager.MyApplication;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.utils.ui.DialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by boyu on 2017/12/7.
 */

public class HomeActivity extends BaseActivity {

    private boolean isExit = false;
    private Handler mHandler = new Handler();

    @BindView(R.id.vp_home_content)
    ViewPager vpContent;
    @BindView(R.id.bnb_home_bottom)
    BottomNavigationBar bnbBottom;

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private HomeFragment mHomeFragment;
    private TranslateFragment mTranslate;
    private MineFragment mMineFragment;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        bnbBottom.addItem(new BottomNavigationItem(R.mipmap.hj_home_mine_active, "首页")
                .setInactiveIconResource(R.mipmap.hj_home_mine_inactive)
                .setInActiveColorResource(R.color.hj_tools_home_bar_inactive))
                .addItem(new BottomNavigationItem(R.mipmap.hj_home_translate_active, "翻译")
                        .setInactiveIconResource(R.mipmap.hj_home_translate_inactive)
                        .setInActiveColorResource(R.color.hj_tools_home_bar_inactive))
                .addItem(new BottomNavigationItem(R.mipmap.hj_home_mine_active, "我的")
                        .setInactiveIconResource(R.mipmap.hj_home_mine_inactive)
                        .setInActiveColorResource(R.color.hj_tools_home_bar_inactive))
                .initialise();
        bnbBottom.setMode(BottomNavigationBar.MODE_FIXED);
        bnbBottom.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bnbBottom.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        mHomeFragment = new HomeFragment();
        new HomePresenter(Injection.provideRepository(), mHomeFragment, Injection.provideSchedule());
        mFragmentList.add(mHomeFragment);

        mTranslate = new TranslateFragment();
        new TranslatePresenter(Injection.provideLiteOrm(), mTranslate, Injection.provideSchedule(),
                Injection.provideWrapApiService());
        mFragmentList.add(mTranslate);

        mMineFragment = new MineFragment();
        new MinePresenter(Injection.provideRepository(), mMineFragment, Injection.provideSchedule());
        mFragmentList.add(mMineFragment);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bnbBottom.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            MyApplication.exit(0);
        } else {
            isExit = true;
            DialogUtil.showHint(HomeActivity.this, "再按一次推出程序");
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    isExit = false;
                }
            }, 3000);
        }
    }
}
