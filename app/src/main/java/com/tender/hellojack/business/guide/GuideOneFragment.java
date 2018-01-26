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
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;

public class GuideOneFragment extends BaseFragment implements GuideActivity.PlayAnimation {

    private static final long ANIMATOR_DURATION = 5400L;

    private ImageView ivSmallText, ivBigText, ivPhone;
    private View vSmallText, vBigText;
    private FrameLayout flBackground;

    private ValueAnimator vaSmallText, vaBigText;
    private AnimatorSet asBackground;
    private RotateAnimation raPhone;

    @Override
    public void onResume() {
        super.onResume();
        startAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_guide_one, container, false);
        ivSmallText = (ImageView) root.findViewById(R.id.iv_guide_one_small_text);
        ivBigText = (ImageView) root.findViewById(R.id.iv_guide_one_big_text);
        ivPhone = (ImageView) root.findViewById(R.id.iv_guide_one_phone);
        vSmallText = root.findViewById(R.id.v_guide_one_small_text);
        vBigText = root.findViewById(R.id.v_guide_one_big_text);
        flBackground = (FrameLayout) root.findViewById(R.id.fl_guide_one_bg);

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
    public void startAnimation() {
        vaSmallText.start();
        vaBigText.start();
        asBackground.start();
        asBackground.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ivPhone.startAnimation(raPhone);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void stopAnimation() {
        if (vaSmallText != null) vaSmallText.cancel();
        if (vaBigText != null) vaBigText.cancel();
        if (asBackground != null) asBackground.cancel();
        if (ivPhone != null) ivPhone.clearAnimation();
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

        raPhone = new RotateAnimation(0f, 10f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.9f);
        raPhone.setDuration(ANIMATOR_DURATION / 2 / 6);
        raPhone.setRepeatMode(RotateAnimation.REVERSE);
        raPhone.setRepeatCount(5);
    }

    @Override
    protected void onBackPressed() {

    }
}
