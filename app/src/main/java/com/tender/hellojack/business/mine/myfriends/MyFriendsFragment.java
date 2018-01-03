package com.tender.hellojack.business.mine.myfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.userinfo.UserInfoActivity;
import com.tender.hellojack.model.Contact;
import com.tender.hellojack.model.Friend;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.hellojack.utils.imageloder.ImageLoaderUtil;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.DisplayUtil;
import com.tender.tools.utils.UIUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.views.QuickIndexBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class MyFriendsFragment extends BaseFragment implements MyFriendsContract.View {

    private MyFriendsContract.Presenter mPresenter;
    private LQRAdapterForRecyclerView<Contact> mAdapter;
    private List<Contact> mContacts;

    private View vHeadView;
    private TextView tvFootView, tvNewFriendsUnread, tvGroupChatUnread;
    private LinearLayout llNewFriend, llGroupChat, llTag, llOfficial;

    private LQRRecyclerView rvContact;
    private QuickIndexBar qibLettes;
    private TextView tvSelectLetter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

        showHeaderViewUnread();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_my_friends, container, false);
        rvContact = (LQRRecyclerView) root.findViewById(R.id.rv_my_friends_contact);
        qibLettes = (QuickIndexBar) root.findViewById(R.id.qib_my_friends);
        tvSelectLetter = (TextView) root.findViewById(R.id.tv_my_friends_select_letter);

        vHeadView = View.inflate(mActivity, R.layout.hj_layout_my_friends_head_view, null);
        llNewFriend = (LinearLayout) vHeadView.findViewById(R.id.ll_my_friends_new_friends);
        llGroupChat = (LinearLayout) vHeadView.findViewById(R.id.ll_my_friends_group_chat);
        llTag = (LinearLayout) vHeadView.findViewById(R.id.ll_my_friends_tag);
        llOfficial = (LinearLayout) vHeadView.findViewById(R.id.ll_my_friends_official);
        tvNewFriendsUnread = (TextView) vHeadView.findViewById(R.id.tv_my_friends_new_friends_unread);
        tvGroupChatUnread = (TextView) vHeadView.findViewById(R.id.tv_my_friends_group_chat_unread);

        tvFootView = new TextView(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mActivity,50));
        tvFootView.setLayoutParams(params);
        tvFootView.setGravity(Gravity.CENTER);
        return root;
    }

    @Override
    public void initUIData() {
        mPresenter.initContacts();
        mContacts = mPresenter.getSortedContacts();
        if (mContacts.size() > 0) {
            if (tvFootView != null) {
                tvFootView.setVisibility(View.VISIBLE);
                tvFootView.setText(mContacts.size() + "位联系人");
            }
        } else {
            tvFootView.setVisibility(View.GONE);
        }
        setAdapter();

        qibLettes.setListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                showSelectLetter(letter);//显示字母提示
                if ("↑".equals(letter)) {
                    rvContact.moveToPosition(0);
                } else if ("☆".equals(letter)) {
                    rvContact.moveToPosition(0);
                } else { //找出第一个对应字母的位置后，滑动到指定位置
                    for (int i = 0; i < mContacts.size(); i ++) {
                        Contact contact = mContacts.get(i);
                        String c = contact.getmPinyin().charAt(0) + "";
                        if (c.equalsIgnoreCase(letter)) {
                            rvContact.moveToPosition(i);
                            break;
                        }
                    }
                }
            }
        });
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
    public void setPresenter(MyFriendsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    private void setAdapter() {
        mAdapter = new LQRAdapterForRecyclerView<Contact>(mActivity, R.layout.hj_layout_contact_item, mContacts) {
            @Override
            public void convert(LQRViewHolderForRecyclerView helper, final Contact item, int position) {
                helper.setText(R.id.tv_my_friends_item_name, StringUtil.hasValue(item.getmAlias()) ? item.getmAlias() : item.getmName());
                if (StringUtil.hasValue(item.getmAvatar())) {
                    ImageLoaderUtil.loadLocalImage(item.getmAvatar(), (ImageView) helper.getView(R.id.iv_my_friends_item_header));
                } else {
                    helper.setImageResource(R.id.iv_my_friends_item_header, R.mipmap.hj_mine_default_header);
                }

                String str = "";
                String currentLetter = item.getmPinyin().charAt(0) + "";//得到当前字母
                if (position == 0) {
                    str = currentLetter;
                } else {
                    String preLetter = mContacts.get(position - 1).getmPinyin().charAt(0) + "";//得到上一个字母
                    if (!preLetter.equalsIgnoreCase(currentLetter)) {//如果和上一个字母的首字母不同则显示字母栏
                        str = currentLetter;
                    }

                    int netIndex = position + 1;
                    if (netIndex < mContacts.size() - 1) {
                        String netLetter = mContacts.get(position + 1).getmPinyin().charAt(0) + "";//得到下一个字母
                        if (!netLetter.equalsIgnoreCase(currentLetter)) {//如果和下一个字母的首字母不同则隐藏下划线
                            helper.setViewVisibility(R.id.v_my_friends_item_line, View.INVISIBLE);
                        } else {
                            helper.setViewVisibility(R.id.v_my_friends_item_line, View.VISIBLE);
                        }
                    } else {
                        helper.setViewVisibility(R.id.v_my_friends_item_line, View.INVISIBLE);
                    }
                }

                if (StringUtil.hasValue(str)) { //根据str是否为空决定字母栏是否显示
                    helper.setViewVisibility(R.id.tv_my_friends_letter_index, View.VISIBLE);
                    helper.setText(R.id.tv_my_friends_letter_index, currentLetter);
                } else {
                    helper.setViewVisibility(R.id.tv_my_friends_letter_index, View.GONE);
                }

                RxView.clicks(helper.getView(R.id.ll_my_friends_item)).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mActivity, UserInfoActivity.class);
                        intent.putExtra(IntentConst.IRParam.MY_FRIENDS_ACCOUNT, item.getmAccount());
                        startActivity(intent);
                    }
                });
            }
        };
        mAdapter.addHeaderView(vHeadView);
        mAdapter.addFooterView(tvFootView);
        if (rvContact != null) {
            rvContact.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
    }

    @Override
    public void showSelectLetter(String letter) {
        tvSelectLetter.setVisibility(View.VISIBLE);
        tvSelectLetter.setText(letter);

        UIUtil.getMainHandle().removeCallbacksAndMessages(null);
        UIUtil.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                tvSelectLetter.setVisibility(View.GONE);
            }
        }, 500);
    }

    @Override
    public void showHeaderViewUnread() {
        tvNewFriendsUnread.setVisibility(View.VISIBLE);
        tvGroupChatUnread.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChangedWrapper();
    }
}
