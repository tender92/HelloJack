package com.tender.hellojack.business.translate;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.orhanobut.logger.Logger;
import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.business.translate.result.AbResult;
import com.tender.hellojack.business.translate.service.WrapApiService;
import com.tender.hellojack.data.LocalDicHelper;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.preference.TranslatePref;
import com.tender.hellojack.model.translate.ETranslateFrom;
import com.tender.hellojack.model.translate.Result;
import com.tender.hellojack.utils.Injection;
import com.tender.tools.TenderLog;
import com.tender.tools.manager.PrefManager;
import com.tender.tools.utils.file.FileCache;
import com.trello.rxlifecycle.android.FragmentEvent;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import jonathanfinerty.once.Once;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.AsyncOnSubscribe;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class TranslatePresenter implements TranslateContract.Presenter {

    private final LiteOrm mLiteOrm;
    private final TranslateContract.View mView;
    private final BaseSchedule mSchedule;
    private final WrapApiService apiService;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;
    private FileCache fileCache = new FileCache();

    public TranslatePresenter(LiteOrm mLiteOrm, TranslateContract.View mView,
                              BaseSchedule mSchedule, WrapApiService apiService) {
        this.mLiteOrm = mLiteOrm;
        this.mView = mView;
        this.mSchedule = mSchedule;
        this.apiService = apiService;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {
            mView.initUIData();
            hasInit = true;
        }
    }

    @Override
    public void executeSearch() {
        mSubscription.add(apiService.translate(TranslatePref.getInstance().getTranslateWay(), mView.getInput())
                .filter(new Func1<AbResult, Boolean>() {
                    @Override
                    public Boolean call(AbResult abResult) {
                        return abResult != null && abResult.wrapErrorCode() == 0;
                    }
                })
                .subscribeOn(mSchedule.io())
                .observeOn(mSchedule.ui())
                .map(new Func1<AbResult, List<String>>() {
                    @Override
                    public List<String> call(AbResult abResult) {
                        Result result = abResult.getResult();
                        if (result == null) return null;
                        result.setCreate_time(System.currentTimeMillis());
                        result.setUpdate_time(System.currentTimeMillis());
                        mView.addTagForView(result);
                        mView.showSoundView(!StringUtils.isEmpty(result.getEnMp3()));
                        mView.showFavoriteView(isFavorite(result.getQuery()) != null);
                        List<String> temp = abResult.wrapExplains();
                        //增加音标显示
                        String phAm = abResult.getResult().getPhAm();
                        if (!temp.isEmpty() && !TextUtils.isEmpty(phAm)) {
                            temp.add(0, "[" + phAm + "]");
                            return temp;
                        }
                        return abResult.wrapTranslation();
                    }
                })
                .filter(new Func1<List<String>, Boolean>() {
                    @Override
                    public Boolean call(List<String> strings) {
                        return strings != null && !strings.isEmpty();
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        if (strings == null) {
                            return Observable.error(new Exception(("啥也没有翻译出来!")));
                        }
                        return Observable.from(strings);
                    }
                })
                .compose(mView.<String>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.addExplainItem(s);
                    }

                    @Override
                    public void onCompleted() {
                        mView.onTranslateComplete();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.onTranslateError(throwable);
                    }
                }));

    }

    @Override
    public void checkTranslateWay() {
        ETranslateFrom from = TranslatePref.getInstance().getTranslateWay();
        mView.initTranslateWay(from);
    }

    @Override
    public void checkVersion() {

    }

    @Override
    public void analysisLocalDic(Context context) {
        mSubscription.add(Observable.just(LocalDicHelper.getLocalDic(context))
                .subscribeOn(mSchedule.io())
                .observeOn(mSchedule.ui())
                .compose(mView.<List<String>>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mView.attachLocalDic(strings);
                    }
                }));
    }

    @Override
    public void favoriteWord(Result result) {
        mLiteOrm.insert(result);
    }

    @Override
    public void unFavoriteWord(Result result) {
        WhereBuilder builder = WhereBuilder.create(Result.class).andEquals(Result.COL_QUERY, result.getQuery());
        mLiteOrm.delete(builder);
    }

    @Override
    public void playSound(final Context context, final String fileName, String mp3Url) {
        File cacheFile = fileCache.getCacheFileByUrl(context, fileName);
        if (cacheFile != null && cacheFile.exists()) {
            playSound(context,cacheFile);
            return;
        }
        Call<ResponseBody> call = Injection.provideRequestService().downloadSoundFile(mp3Url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        cacheAndPlaySound(context, fileName, response.body().bytes());
                    } catch (IOException e) {
                        TenderLog.e(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                TenderLog.e(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void cacheAndPlaySound(final Context context, final String fileName, final byte[] data) {
        mSubscription.add(Observable.just(fileCache.cacheFileOnDisk(context, fileName, data))
                .subscribeOn(mSchedule.io())
                .observeOn(mSchedule.ui())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        playSound(context, file);
                    }
                })
        );
    }

    private Result isFavorite(String words) {
        QueryBuilder queryBuilder = new QueryBuilder(Result.class);
        queryBuilder = queryBuilder.whereEquals(Result.COL_QUERY, words);
        List<Result> results = mLiteOrm.query(queryBuilder);
        if (results.isEmpty()) {
            return  null;
        }
        return results.get(0);
    }

    private void playSound(Context context, File file) {
        if (file == null) return;
        Uri myUri = Uri.fromFile(file);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context, myUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            TenderLog.e(e.getMessage());
        }
        mediaPlayer.start();
    }
}