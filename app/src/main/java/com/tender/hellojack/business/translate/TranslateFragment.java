package com.tender.hellojack.business.translate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.tender.hellojack.BuildConfig;
import com.tender.hellojack.R;
import com.tender.hellojack.base.BaseFragment;
import com.tender.hellojack.data.preference.TranslatePref;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.hellojack.model.translate.Result;
import com.tender.hellojack.service.listenclipboard.ListenClipboardService;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.tools.utils.ui.KeyBoardUtils;

import org.apache.commons.lang3.StringUtils;

import java.net.UnknownHostException;
import java.util.List;

import static com.tender.hellojack.R.id.tv_translate_input;

public class TranslateFragment extends BaseFragment implements TranslateContract.View {

    private TranslateContract.Presenter mPresenter;

    private TextView tvClear;
    private AutoCompleteTextView actvInput;
    private AppCompatSpinner acsWay;
    private Button btnTranslate;
    private AppCompatImageView ivFavorite, ivSound, ivCopy;
    private LinearLayout llResult;
    private RelativeLayout rlAction;

    private String tempInput;

    private boolean isFavorite = false;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hj_fragment_translate, container, false);
        mToolbar = (Toolbar) root.findViewById(R.id.hj_toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_toolbar_title);
        tvClear = (TextView) root.findViewById(R.id.tv_translate_clear);
        actvInput = (AutoCompleteTextView) root.findViewById(tv_translate_input);
        acsWay = (AppCompatSpinner) root.findViewById(R.id.spinner_translate_way);
        btnTranslate = (Button) root.findViewById(R.id.btn_translate_translate);
        ivFavorite = (AppCompatImageView) root.findViewById(R.id.iv_translate_favorite);
        ivSound = (AppCompatImageView) root.findViewById(R.id.iv_translate_sound);
        ivCopy = (AppCompatImageView) root.findViewById(R.id.iv_translate_content_copy);
        llResult = (LinearLayout) root.findViewById(R.id.ll_translate_result);
        rlAction = (RelativeLayout) root.findViewById(R.id.rl_translate_action);

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actvInput.setText("");
                isFavorite = false;
                ivFavorite.setImageResource(R.drawable.hj_translate_favorite_border_black_24dp);
                ivFavorite.setTag(null);
                ivSound.setTag(null);
                llResult.removeAllViews();
                rlAction.setVisibility(View.GONE);
                KeyBoardUtils.closeSoftKeyboard(actvInput);
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate();
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFavoriteAnim(view);
            }
        });

        ivSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object obj = view.getTag();
                if (obj != null && obj instanceof Result) {
                    Result entity = (Result) obj;
                    String fileName = entity.getMp3FileName();
                    String mp3Url = entity.getEnMp3();
                    mPresenter.playSound(mActivity, fileName, mp3Url);
                }
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtils.closeSoftKeyboard(actvInput);
                DialogUtil.showHint(mActivity, "长按翻译结果可复制");
            }
        });

        return root;
    }

    @Override
    public void initUIData() {
        actvInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() ==KeyEvent.ACTION_DOWN && keyCode ==KeyEvent.KEYCODE_ENTER) {
                    actvInput.dismissDropDown();
                    translate();
                    return true;
                }
                return false;
            }
        });
        actvInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tempInput = editable.toString();
                tvClear.setVisibility(StringUtils.isEmpty(tempInput) ? View.INVISIBLE : View.VISIBLE);
            }
        });
        initSpinner();
        acsWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        TranslatePref.getInstance().saveTranslateWay(ETranslateFrom.BAI_DU.name());
                        translate();
                        break;
                    case 1:
                        TranslatePref.getInstance().saveTranslateWay(ETranslateFrom.YOU_DAO.name());
                        translate();
                        break;
                    case 2:
                        TranslatePref.getInstance().saveTranslateWay(ETranslateFrom.JIN_SHAN.name());
                        translate();
                        break;
                    case 3:
                        TranslatePref.getInstance().saveTranslateWay(ETranslateFrom.GOOGLE.name());
                        translate();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        startListenClipboardService();//监听黏贴版服务
        mPresenter.checkTranslateWay();
//        mPresenter.checkVersion();
        mPresenter.analysisLocalDic(mActivity);
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
    public void setPresenter(TranslateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
//            mToolbar.setNavigationIcon(R.mipmap.hj_toolbar_back);

            mActivity.setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

            mTitle.setText("咕咚翻译");
        }
    }

    @Override
    public String getInput() {
        return actvInput.getText().toString();
    }

    @Override
    public void onPrepareTranslate() {
        llResult.removeAllViews();
        rlAction.setVisibility(View.GONE);
        btnTranslate.setText("翻译中...");
        btnTranslate.setClickable(false);
        ivFavorite.setClickable(false);
        ivSound.setClickable(false);
        ivCopy.setClickable(false);
    }

    @Override
    public void onTranslateComplete() {
        btnTranslate.setClickable(true);
        btnTranslate.setText("翻译");
        rlAction.setVisibility(View.VISIBLE);
        ivFavorite.setClickable(true);
        ivSound.setClickable(true);
        ivCopy.setClickable(true);
    }

    @Override
    public void onTranslateError(Throwable e) {
        String msg;
        if (e instanceof JsonSyntaxException) {
            msg = "不支持的查询操作，你可以切换别的翻译方式碰碰运气" + (BuildConfig.DEBUG ? "  " + e.getMessage() : "");
        } else if (e instanceof UnknownHostException) {
            msg = "网络异常，请检查后重试" + (BuildConfig.DEBUG ? "  " + e.getMessage() : "");
        } else {
            msg = "未知的异常" + (BuildConfig.DEBUG ? "  " + e.getMessage() : "");
            e.printStackTrace();
        }
        TextView tv = new TextView(mActivity);
        tv.setTextColor(ContextCompat.getColor(mActivity, R.color.hj_tools_gray12));
        tv.setPadding(0,6,0,6);
        tv.setTextSize(14);
        tv.setTextIsSelectable(false);
        tv.setGravity(Gravity.LEFT);
        tv.setText(msg);
        btnTranslate.setClickable(true);
        btnTranslate.setText("翻译");
    }

    @Override
    public void addTagForView(Result result) {
        ivFavorite.setTag(result);
        ivSound.setTag(result);
    }

    @Override
    public void showSoundView(boolean isShow) {
        ivSound.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFavoriteView(boolean isFavorite) {
        ivFavorite.setImageResource(isFavorite ? R.drawable.hj_translate_favorite_pink_24dp : R.drawable.hj_translate_favorite_border_black_24dp);
        this.isFavorite = isFavorite;
    }

    @Override
    public void addExplainItem(String explain) {
        TextView tv = new TextView(mActivity);
        tv.setTextColor(ContextCompat.getColor(mActivity, R.color.hj_tools_gray12));
        tv.setPadding(0,6,0,6);
        tv.setTextSize(14);
        tv.setTextIsSelectable(true);
        tv.setGravity(Gravity.LEFT);
        tv.setText(explain);
        llResult.addView(tv);
    }

    @Override
    public void initTranslateWay(ETranslateFrom from) {
        acsWay.setSelection(from.getIndex(), true);
    }

    @Override
    public void attachLocalDic(List<String> dic) {
        ArrayAdapter<String> wordAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                dic);
        actvInput.setAdapter(wordAdapter);
        actvInput.setThreshold(1);
        actvInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                translate();
            }
        });
    }

    @Override
    public void startFavoriteAnim(final View view) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f, 1f, 1.2f, 1f);
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f, 1f, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX, animY);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Object obj = view.getTag();
                if (obj != null && obj instanceof Result) {
                    Result entity = (Result) obj;
                    if (isFavorite) {
                        mPresenter.unFavoriteWord(entity);
                        ivFavorite.setImageResource(R.drawable.hj_translate_favorite_border_black_24dp);
                        isFavorite = false;
                    } else {
                        mPresenter.favoriteWord(entity);
                        ivFavorite.setImageResource(R.drawable.hj_translate_favorite_pink_24dp);
                        isFavorite = true;
                    }
                }
            }
        });
        animatorSet.start();
    }

    private void translate() {
        KeyBoardUtils.closeSoftKeyboard(actvInput);
        onPrepareTranslate();
        mPresenter.executeSearch();
    }

    private void startListenClipboardService() {
        ListenClipboardService.start(mActivity);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mActivity,
                R.array.hj_translate_way, R.layout.hj_layout_translate_spinner_title);
        adapter.setDropDownViewResource(R.layout.hj_layout_translate_spinner_item);
        acsWay.setAdapter(adapter);
    }
}
