package com.tender.hellojack.business.guide;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.home.HomeActivity;

/**
 * Created by boyu on 2018/1/26.
 */

public class GuideThreeFragment extends Fragment implements GuideActivity.PlayAnimation {

    private static final long ANIMATOR_DURATION = 5400L;

    private ImageView ivSmallText, ivBigText, ivCloud, ivStar;
    private View vSmallText, vBigText;
    private FrameLayout flBackground;
    private Button btnGoHome;

    private ValueAnimator vaSmallText, vaBigText;
    private AnimatorSet asBackground, asCloud, asStar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_guide_three, container, false);
        ivSmallText = (ImageView) root.findViewById(R.id.iv_guide_three_small_text);
        ivBigText = (ImageView) root.findViewById(R.id.iv_guide_three_big_text);
        ivCloud = (ImageView) root.findViewById(R.id.iv_guide_three_cloud);
        ivStar = (ImageView) root.findViewById(R.id.iv_guide_three_star);
        vSmallText = root.findViewById(R.id.v_guide_three_small_text);
        vBigText = root.findViewById(R.id.v_guide_three_big_text);
        flBackground = (FrameLayout) root.findViewById(R.id.fl_guide_three_bg);
        btnGoHome = (Button) root.findViewById(R.id.btn_guide_three_go_home);

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

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

        asBackground = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.hj_animator_guide_alpha);
        asBackground.setTarget(flBackground);

        asCloud = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.hj_animator_guiede_cloud);
        asCloud.setTarget(ivCloud);

        asStar = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.hj_animator_guiede_star);
        asStar.setTarget(ivStar);

    }

    @Override
    public void startAnimation() {
        ivCloud.setVisibility(View.INVISIBLE);
        ivStar.setVisibility(View.INVISIBLE);

        vaSmallText.start();
        vaBigText.start();
        asBackground.start();
        asBackground.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                asCloud.start();
                asStar.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        asStar.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                ivStar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        asCloud.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                ivCloud.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

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
        if (asCloud != null) asCloud.cancel();
        if (asStar != null) asStar.cancel();
    }
}
