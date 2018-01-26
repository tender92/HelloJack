package com.tender.hellojack.business.guide;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;

/**
 * Created by boyu on 2018/1/26.
 */

public class GuideTwoFragment extends BaseFragment implements GuideActivity.PlayAnimation {

    private static final long ANIMATOR_DURATION = 5400L;

    private ImageView ivSmallText, ivBigText, ivPacket, ivCoin;
    private View vSmallText, vBigText;
    private FrameLayout flBackground;

    private ValueAnimator vaSmallText, vaBigText;
    private AnimatorSet asBackground;
    private TranslateAnimation taCoin;
    private ScaleAnimation saPacket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_guide_two, container, false);

        ivSmallText = (ImageView) root.findViewById(R.id.iv_guide_two_small_text);
        ivBigText = (ImageView) root.findViewById(R.id.iv_guide_two_big_text);
        ivPacket = (ImageView) root.findViewById(R.id.iv_guide_two_packet);
        ivCoin = (ImageView) root.findViewById(R.id.iv_guide_two_coin);
        vSmallText = root.findViewById(R.id.v_guide_two_small_text);
        vBigText = root.findViewById(R.id.v_guide_two_big_text);
        flBackground = (FrameLayout) root.findViewById(R.id.fl_guide_two_bg);

        ivSmallText.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams vSmallTextParam = vSmallText.getLayoutParams();
        vSmallTextParam.width = ivSmallText.getMeasuredWidth();
        vSmallTextParam.height = ivSmallText.getMeasuredHeight();
        vSmallText.requestLayout();
        vaSmallText = ValueAnimator.ofInt(ivSmallText.getMeasuredHeight(), 0);

        ivBigText.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams vBigTextParam = vBigText.getLayoutParams();
        vBigTextParam.width = ivBigText.getMeasuredWidth();
        vBigTextParam.height = ivBigText.getMeasuredHeight();
        vBigText.requestLayout();
        vaBigText = ValueAnimator.ofInt(ivBigText.getMeasuredHeight(), 0);

        initAnimation();
        return root;
    }

    @Override
    protected void onBackPressed() {

    }

    private void initAnimation() {
        vaSmallText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                vSmallText.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                vSmallText.requestLayout();
            }
        });
        vaSmallText.setDuration(ANIMATOR_DURATION / 2);

        vaBigText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                vBigText.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                vBigText.requestLayout();
            }
        });
        vaBigText.setDuration(ANIMATOR_DURATION / 2);

        asBackground = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity, R.animator.hj_animator_guide_alpha);
        asBackground.setTarget(flBackground);

        taCoin = new TranslateAnimation(0f, 0f, -300f, 0f);// 位移动画，从button的上方300像素位置开始
        taCoin.setDuration(ANIMATOR_DURATION / 4);
        taCoin.setInterpolator(new BounceInterpolator());// 弹跳动画

        saPacket = new ScaleAnimation(1.2f, 1f, 1.2f, 1f,
                RotateAnimation.RELATIVE_TO_SELF, 0.6f,
                RotateAnimation.RELATIVE_TO_SELF, 0.7f);
        saPacket.setDuration(ANIMATOR_DURATION / 4);
        saPacket.setInterpolator(new BounceInterpolator());
    }

    @Override
    public void startAnimation() {
        ivCoin.setVisibility(View.INVISIBLE);
        ivPacket.setVisibility(View.INVISIBLE);

        vaSmallText.start();
        vaBigText.start();
        asBackground.start();
        asBackground.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ivCoin.startAnimation(taCoin);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        taCoin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivCoin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivPacket.startAnimation(saPacket);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        saPacket.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ivPacket.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void stopAnimation() {
        if (vaSmallText != null) vaSmallText.cancel();
        if (vaBigText != null) vaBigText.cancel();
        if (asBackground != null) asBackground.cancel();
        if (ivPacket != null) ivPacket.clearAnimation();
        if (ivCoin != null) ivCoin.clearAnimation();
    }
}
