package com.tender.hellojack.business.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseActivity;
import com.tender.tools.views.viewpager.CustomViewPager;
import com.tender.tools.views.viewpager.PagerIndicator;

import java.util.ArrayList;

/**
 * Created by boyu
 */
public class GuideActivity extends BaseActivity {

    private LinearLayout llContainer;
    private CustomViewPager vpGuide;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private PagerIndicator mPagerIndicator;

    @Override
    protected void initLayout() {
        setContentView(R.layout.hj_activity_guide);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        llContainer = (LinearLayout) findViewById(R.id.ll_guide_indicator);
        vpGuide = (CustomViewPager) findViewById(R.id.vp_guide);

        fragments.add(new GuideOneFragment());
        fragments.add(new GuideTwoFragment());
        fragments.add(new GuideThreeFragment());

        vpGuide.setAdapter(new GuidePagerAdapter(getSupportFragmentManager(), fragments));
        vpGuide.addOnPageChangeListener(new GuidePagerListener());
        mPagerIndicator = new PagerIndicator(this, llContainer, fragments.size(), 0);

        vpGuide.setCurrentItem(0);
    }

    private class GuidePagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mPagerIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            for (Fragment fragment : fragments) {
                ((PlayAnimation)fragment).stopAnimation();
            }
            ((PlayAnimation)fragments.get(position)).startAnimation();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public interface PlayAnimation {

        void startAnimation();

        void stopAnimation();
    }
}