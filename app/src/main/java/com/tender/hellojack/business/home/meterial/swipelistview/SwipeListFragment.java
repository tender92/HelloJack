package com.tender.hellojack.business.home.meterial.swipelistview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.tools.utils.ui.DisplayUtil;
import com.tender.tools.utils.ui.UIUtil;
import com.tender.tools.views.swipelistview.AbOnListViewListener;
import com.tender.tools.views.swipelistview.SwipeListView;
import com.tender.tools.views.swipelistview.SwipeListViewListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class SwipeListFragment extends BaseFragment implements SwipeListContract.View {

    private SwipeListContract.Presenter mPresenter;

    private SwipeListView mSwipeListView;
    private RelativeLayout rlGuide;
    private ImageView ivGesture, ivToTop;

    private List<CardTransBean> transBeanList = new ArrayList<>();
    private  SwipeListAdapter mAdapter;

    private boolean showGuide = true;
    private boolean clickGuide = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mSwipeListView.stopRefresh();
                    DialogUtil.showHint(mActivity, "下拉刷新");
                    break;
                case 1:
                    mSwipeListView.stopLoadMore();
                    DialogUtil.showHint(mActivity, "上拉加载");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_swipe_list, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        mSwipeListView = (SwipeListView) root.findViewById(R.id.slv_swipe_list);
        rlGuide = (RelativeLayout) root.findViewById(R.id.rl_swipe_list_guide);
        ivGesture = (ImageView) root.findViewById(R.id.iv_swipe_list_gesture);
        ivToTop = (ImageView) root.findViewById(R.id.iv_swipe_list_to_top);
        RxView.clicks(rlGuide).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (showGuide && !clickGuide) {
                    clickGuide = true;
                    ObjectAnimator animator = new ObjectAnimator().ofFloat(
                            ivGesture, "translationX", DisplayUtil.dip2px(mActivity, -160));
                    animator.setDuration(1000);
                    animator.start();
                    ObjectAnimator animator1 = new ObjectAnimator().ofFloat(
                            mSwipeListView.getChildAt(1).findViewById(R.id.ll_swipe_list_front), "translationX", DisplayUtil.dip2px(mActivity, -160));
                    animator1.setDuration(1000);
                    animator1.start();
                } else {
                    showGuide = false;
                    showGuide = false;
                    rlGuide.setVisibility(View.GONE);
                    ObjectAnimator animator1 = new ObjectAnimator().ofFloat(
                            mSwipeListView.getChildAt(1).findViewById(R.id.ll_swipe_list_front), "translationX", 0);
                    animator1.setDuration(1000);
                    animator1.start();
                }
            }
        });
        RxView.clicks(ivToTop).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mAdapter.notifyDataSetChanged();
                mSwipeListView.setSelection(1);
                DialogUtil.showHint(mActivity, "To Top");
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        ivToTop.setVisibility(View.GONE);
        initSwipeListView();
        setAdapter();
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
    public void setPresenter(SwipeListContract.Presenter presenter) {
        mPresenter = presenter;
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

            mTitle.setText("详细资料");
        }
    }

    private void initSwipeListView() {
        // 打开关闭下拉刷新加载更多功能
        mSwipeListView.setPullRefreshEnable(true);
        mSwipeListView.setPullLoadEnable(true);
//        // 设置进度条的样式
//        mSwipeListView.getHeaderView().setHeaderProgressBarDrawable(
//                ResourcesCompat.getDrawable(mActivity.getResources(), R.drawable.hj_tools_animate_loading, null));
//        mSwipeListView.getHeaderView().setBackgroundColor(
//                UIUtil.getColor(R.color.hj_tools_white));
//        mSwipeListView.getFooterView().setHeaderProgressBarDrawable(
//                ResourcesCompat.getDrawable(mActivity.getResources(), R.drawable.hj_tools_animate_loading, null));
//        mSwipeListView.getFooterView().setBackgroundColor(
//                UIUtil.getColor(R.color.hj_tools_white));

        initSwipeListMenu();

        mSwipeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mSwipeListView.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
                int lastPageCount = firstVisibleItem + visibleItemCount;
                int defineLength = PrefManager.getScreenHeight() / DisplayUtil.dip2px(mActivity, 59);
                if (lastPageCount <= defineLength + 3) {
                    ivToTop.setVisibility(View.GONE);
                } else {
                    ivToTop.setVisibility(View.VISIBLE);
                }
            }
        });

        mSwipeListView.setAbOnListViewListener(new AbOnListViewListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore() {
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        });
    }

    private void initSwipeListMenu() {
        mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
        mSwipeListView.setAnimationTime(0);
        mSwipeListView.setSwipeOpenOnLongPress(false);

        int deviceWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        View convertView = LayoutInflater.from(mActivity).inflate(R.layout.hj_tools_swipe_list_item, null, false);
        LinearLayout llBack = (LinearLayout) convertView.findViewById(R.id.ll_swipe_list_back);
        int llWidth = llBack.getLayoutParams().width;
        mSwipeListView.setOffsetLeft(deviceWidth - llWidth);
    }

    private void setAdapter() {
        transBeanList = mPresenter.getTransList();
        mAdapter = new SwipeListAdapter(transBeanList, mActivity);
        mSwipeListView.setAdapter(mAdapter);
    }
}
