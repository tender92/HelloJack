package com.tender.hellojack.service.listenclipboard;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.business.translate.listener.ClipboardManagerCompat;
import com.tender.hellojack.business.translate.service.WrapApiService;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.preference.ReciteTrayPref;
import com.tender.hellojack.model.translate.Result;
import com.tender.tools.utils.ui.UIUtil;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu on 2018/1/31.
 */

public class ListenClipboardPresenter implements ListenClipBoardContract.Presenter {

    private final LiteOrm liteOrm;
    private final ListenClipBoardContract.View mView;
    private final BaseSchedule mScheduler;
    private final WrapApiService wrapApiService;

    private ReciteTrayPref reciteTrayPref;
    private ClipboardManagerCompat mClipboardWatcher;

    private CompositeSubscription mSubscription;
    private boolean mIsInit = false;

    /**
     * 循环展示单词结果
     */
    private List<Result> results;
    private ClipboardManagerCompat.OnPrimaryClipChangedListener mListener = new ClipboardManagerCompat.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            CharSequence content = mClipboardWatcher.getText();
            if (content != null) {
                performClipboardCheck(content.toString());
            }
        }
    };

    public ListenClipboardPresenter(LiteOrm liteOrm, WrapApiService wrapApiService, ListenClipBoardContract.View mView, BaseSchedule mScheduler) {
        this.liteOrm = liteOrm;
        this.wrapApiService = wrapApiService;
        this.mView = mView;
        this.mScheduler = mScheduler;

        mClipboardWatcher = ClipboardManagerCompat.create(UIUtil.getAppContext());
        reciteTrayPref = new ReciteTrayPref(UIUtil.getAppContext());
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!mIsInit) {

            QueryBuilder queryBuilder = new QueryBuilder(Result.class);
            queryBuilder = queryBuilder.whereNoEquals(Result.COL_MARK_DONE_ONCE, true);
            results = liteOrm.query(queryBuilder);

            mView.initData();
            mIsInit = true;
        }
    }

    @Override
    public void addPrimaryClipChangedListener() {

    }

    private void performClipboardCheck(String queryText) {

    }
}
