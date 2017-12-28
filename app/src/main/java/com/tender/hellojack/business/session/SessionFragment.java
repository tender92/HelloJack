package com.tender.hellojack.business.session;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lqr.emoji.EmoticonPickerView;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.IEmoticonSelectedListener;
import com.lqr.recyclerview.LQRRecyclerView;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.business.session.function.Func2Fragment;
import com.tender.hellojack.business.session.function.Func1Fragment;
import com.tender.hellojack.business.session.function.FuncPagerAdapter;
import com.tender.hellojack.model.Message;
import com.tender.hellojack.model.UserInfo;
import com.tender.hellojack.utils.ScheduleProvider;
import com.tender.tools.IntentConst;
import com.tender.tools.utils.KeyBoardUtils;
import com.tender.tools.utils.UIUtil;
import com.tender.tools.utils.string.StringUtil;
import com.tender.tools.views.DotView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import rx.functions.Action1;

public class SessionFragment extends BaseFragment implements SessionContract.View, IAudioRecordCallback,
        IEmoticonSelectedListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private SessionContract.Presenter mPresenter;

    private EditText etInputContent;
    private ImageView ivAudio, ivAdd, ivEmo;
    private Button btnAudio, btnSend;
    private EmoticonPickerView epvEmo;
    private FrameLayout flBottom, flPlayAudio;
    private LQRRecyclerView rvMessages;
    private TextView tvChronometerTip;
    private Chronometer cTimer;
    private LinearLayout llFunction;
    private BGARefreshLayout rlMessage;
    private ViewPager vpFunction;
    private DotView dtBottom;

    //发送语音相关参数
    private boolean mTouched;
    private AudioRecorder mAudioRecorderHelper;
    private boolean mCanceled;

    //表情软键盘
    private EmotionKeyboard mEmotionKeyboard;

    //底部控件 功能区
    private FuncPagerAdapter mBottomFucAdapter;
    private List<BaseFragment> mFragments;

    //消息列表相关
    private List<Message> messages = new ArrayList<>();
    private SessionAdapter adapter;
    private Runnable rvMessageScrollToBottomTask = new Runnable() {
        @Override
        public void run() {
            rvMessages.moveToPosition(messages.size() - 1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();

        messages.clear();
        setAdapter();
        mPresenter.loadHistoryData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_session, container, false);
        etInputContent = root.findViewById(R.id.et_session_input_content);
        btnAudio = root.findViewById(R.id.btn_session_audio);
        btnSend = root.findViewById(R.id.btn_session_send);
        ivAudio = root.findViewById(R.id.iv_session_audio);
        ivAdd = root.findViewById(R.id.iv_session_add);
        ivEmo = root.findViewById(R.id.iv_session_emo);
        epvEmo = root.findViewById(R.id.epv_session_emo);
        flBottom = root.findViewById(R.id.fl_session_bottom);
        flPlayAudio = root.findViewById(R.id.fl_session_play_audio);
        rvMessages = root.findViewById(R.id.rv_session_recycle_view);
        tvChronometerTip = root.findViewById(R.id.tv_session_chronometer_tip);
        cTimer = root.findViewById(R.id.c_session_chronometer);
        llFunction = root.findViewById(R.id.ll_session_function);
        rlMessage = root.findViewById(R.id.rl_session_refresh_layout);
        vpFunction = root.findViewById(R.id.vp_session_function);
        dtBottom = root.findViewById(R.id.dv_session_dots);

        RxView.clicks(ivAudio).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showAudioBtnVisibility();
            }
        });
        RxView.clicks(btnSend).throttleFirst(1, TimeUnit.SECONDS).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String content = etInputContent.getText().toString();
                if (StringUtil.hasValue(content)) {
                    mPresenter.sendMessage(content);
                    etInputContent.setText("");
                }
            }
        });
        RxView.touches(rvMessages).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<MotionEvent>() {
            @Override
            public void call(MotionEvent motionEvent) {
                showBottomVisibility();
            }
        });
        RxView.touches(btnAudio).observeOn(ScheduleProvider.getInstance().ui()).subscribe(new Action1<MotionEvent>() {
            @Override
            public void call(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouched = true;
                        initAudioRecord();
                        onStartAudioRecord();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mTouched = false;
                        cancelAudioRecord(isCanceled(btnAudio, motionEvent));
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouched = false;
                        hidePlayAudio();
                        endAudioRecord(isCanceled(btnAudio, motionEvent));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mTouched = false;
                        hidePlayAudio();
                        endAudioRecord(isCanceled(btnAudio, motionEvent));
                        break;
                }
            }
        });
        return root;
    }

    @Override
    public void initUIData() {
        initEmotionPickerView();
        initEmotionKeyboard();
        initRefreshLayout();
        initBottomFunc();
        etInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.hasValue(etInputContent.getText().toString())) {
                    ivAdd.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                } else {
                    ivAdd.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }
            }
        });
        etInputContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rvScrollToBottom();
                }
            }
        });
        vpFunction.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //改变小圆点位置
                dtBottom.changeCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        closeKeyBoardAndLoseFocus();
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
    public void setPresenter(SessionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void showAudioBtnVisibility() {
        if (btnAudio.getVisibility() == View.GONE) {
            showBtnAudio();
        } else {
            hideBtnAudio();
        }
        ivAudio.setImageResource(btnAudio.getVisibility() == View.GONE ? R.mipmap.hj_session_voice : R.mipmap.hj_session_keyboard);
    }

    @Override
    public boolean showBottomVisibility() {
        if (etInputContent.hasFocus()) {
            closeKeyBoardAndLoseFocus();
            return true;
        } else if (flBottom.getVisibility() == View.VISIBLE) {
            flBottom.setVisibility(View.GONE);
            closeKeyBoardAndLoseFocus();
            return true;
        }
        return false;
    }

    @Override
    public void showBtnAudio() {
        btnAudio.setVisibility(View.VISIBLE);
        etInputContent.setVisibility(View.GONE);
        ivEmo.setVisibility(View.GONE);

    }

    @Override
    public void hideBtnAudio() {
        btnAudio.setVisibility(View.GONE);
        etInputContent.setVisibility(View.VISIBLE);
        ivEmo.setVisibility(View.VISIBLE);

        //打开键盘
        openKeyBoardAndGetFocus();
    }

    @Override
    public void showPlayAudio() {
        btnAudio.setText("松开 结束");
    }

    @Override
    public void hidePlayAudio() {
        btnAudio.setText("按住 说话");
        flPlayAudio.setVisibility(View.GONE);
    }

    @Override
    public void updateChronometerTip(boolean cancel) {
        if (cancel){
            tvChronometerTip.setText("松开手指，取消发送");
            tvChronometerTip.setBackgroundResource(R.drawable.hj_tools_shape_rect_stroke_red);
        } else {
            tvChronometerTip.setText("手指上滑，取消发送");
            tvChronometerTip.setBackgroundResource(0);
        }
    }

    @Override
    public void openKeyBoardAndGetFocus() {
        etInputContent.requestFocus();
        KeyBoardUtils.openKeyboard(etInputContent, mActivity);
    }

    @Override
    public void showSendMsg(Message message) {
        adapter.addLastItem(message);
        adapter.notifyDataSetChanged();
        rvScrollToBottom();
    }

    /**
     * 设置表情、贴图控件
     */
    private void initEmotionPickerView() {
        epvEmo.setWithSticker(true);
        epvEmo.show(this);
        epvEmo.attachEditText(etInputContent);
    }

    /**
     * 初始化表情软键盘
     */
    private void initEmotionKeyboard() {
        //1、创建EmotionKeyboard对象
        mEmotionKeyboard = EmotionKeyboard.with(mActivity);
        //2、绑定输入框控件
        mEmotionKeyboard.bindToEditText(etInputContent);
        //3、绑定输入框上面的消息列表控件（这里用的是RecyclerView，其他控件也可以，注意该控件是会影响输入框位置的控件）
        mEmotionKeyboard.bindToContent(rvMessages);
        //4、绑定输入框下面的底部区域（这里是把表情区和功能区共放在FrameLayout下，所以绑定的控件是FrameLayout）
        mEmotionKeyboard.setEmotionView(flBottom);
        //5、绑定表情按钮（可以绑定多个，如微信就有2个，一个是表情按钮，一个是功能按钮）
        mEmotionKeyboard.bindToEmotionButton(ivEmo, ivAdd);
        //6、当在第5步中绑定了多个EmotionButton时，这里的回调监听的view就有用了，注意是为了判断是否要自己来控制底部的显隐，还是交给EmotionKeyboard控制
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                if (btnAudio.getVisibility() == View.VISIBLE) {
                    hideBtnAudio();
                }
                if (flBottom.getVisibility() == View.VISIBLE) {
                    //表情控件显示而点击的按钮是ivAdd时，拦截事件，隐藏表情控件，显示功能区
                    if (epvEmo.getVisibility() == View.VISIBLE && view.getId() == R.id.iv_session_add) {
                        epvEmo.setVisibility(View.GONE);
                        llFunction.setVisibility(View.VISIBLE);
                        return true;
                    } else if (llFunction.getVisibility() == View.VISIBLE && view.getId() == R.id.iv_session_emo){
                        epvEmo.setVisibility(View.VISIBLE);
                        llFunction.setVisibility(View.GONE);
                        return true;
                    }
                } else {
                    //点击ivEmo，显示表情控件
                    if (view.getId() == R.id.iv_session_emo) {
                        epvEmo.setVisibility(View.VISIBLE);
                        llFunction.setVisibility(View.GONE);
                    } else {
                        epvEmo.setVisibility(View.GONE);
                        llFunction.setVisibility(View.VISIBLE);
                    }
                }
                rvScrollToBottom();
                return false;
            }
        });
    }

    /**
     * 初始化消息刷新列表控件
     */
    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        rlMessage.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder holder = new BGANormalRefreshViewHolder(mActivity, false);
        rlMessage.setRefreshViewHolder(holder);
    }

    /**
     * 初始化底部功能区
     */
    private void initBottomFunc() {
        mFragments = new ArrayList<>();
        Func1Fragment fragment1 = new Func1Fragment();
        Func2Fragment fragment2 = new Func2Fragment();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mBottomFucAdapter = new FuncPagerAdapter(((FragmentActivity)mActivity).getSupportFragmentManager(), mFragments);
        vpFunction.setAdapter(mBottomFucAdapter);

        //初始化圆点的个数及当前被选中的位置
        dtBottom.initData(mFragments.size(), 0);
    }

    private void setAdapter() {
        if (adapter == null) {
            Intent intent = mActivity.getIntent();
            if (intent != null) {
                UserInfo userInfo = (UserInfo)intent.getSerializableExtra(IntentConst.IRParam.USER_INFO_USER);
                adapter = new SessionAdapter(mActivity, messages, userInfo);
            } else {
                adapter = new SessionAdapter(mActivity, messages, new UserInfo());
            }
            rvMessages.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 消息列表滚动至最后
     */
    private void rvScrollToBottom() {
        UIUtil.postTaskDelay(rvMessageScrollToBottomTask, 100);
    }

    /**
     * 失去焦点，并关闭键盘
     */
    private void closeKeyBoardAndLoseFocus() {
        etInputContent.clearFocus();
        KeyBoardUtils.closeKeyboard(etInputContent, mActivity);
        flBottom.setVisibility(View.GONE);
    }

    /**
     * 初始化AudioRecord
     */
    private void initAudioRecord() {
        if (mAudioRecorderHelper == null)
            mAudioRecorderHelper = new AudioRecorder(mActivity, RecordType.AAC, AudioRecorder.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND, this);
    }

    /**
     * 开始语音录制
     */
    private void onStartAudioRecord() {
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAudioRecorderHelper.startRecord();
        if (!mTouched) {
            return;
        }
        showPlayAudio();
        updateChronometerTip(false);
        startAudioRecordAnim();
    }

    /**
     * 取消语音录制
     */
    private void cancelAudioRecord(boolean cancel) {
        if (mCanceled == cancel) {
            return;
        }
        mCanceled = cancel;
        updateChronometerTip(cancel);
    }

    private void endAudioRecord(boolean cancel) {
        mActivity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAudioRecorderHelper.completeRecord(cancel);
        hidePlayAudio();
        stopAudioRecordAnim();
    }

    /**
     * 开始语音录制动画
     */
    private void startAudioRecordAnim() {
        flPlayAudio.setVisibility(View.VISIBLE);
        cTimer.setBase(SystemClock.elapsedRealtime());//时间复位
        cTimer.start();
    }

    /**
     * 结束语音录制动画
     */
    private void stopAudioRecordAnim() {
        flPlayAudio.setVisibility(View.GONE);
        cTimer.stop();
        cTimer.setBase(SystemClock.elapsedRealtime());//时间复位
    }

    /**
     * 语音录制是否被取消
     */
    private static boolean isCanceled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    //录音回调 开始
    @Override
    public void onRecordReady() {

    }

    @Override
    public void onRecordStart(File audioFile, RecordType recordType) {

    }

    @Override
    public void onRecordSuccess(File audioFile, long audioLength, RecordType recordType) {

    }

    @Override
    public void onRecordFail() {

    }

    @Override
    public void onRecordCancel() {

    }

    @Override
    public void onRecordReachedMaxTime(int maxTime) {

    }
    //录音回调 结束


    //Emoji选择监听 开始
    @Override
    public void onEmojiSelected(String s) {

    }

    @Override
    public void onStickerSelected(String s, String s1) {

    }
    //Emoji选择监听 结束

    //消息刷新列表控件 代理 开始
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
    //消息刷新列表控件 代理 结束
}
