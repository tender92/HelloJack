package com.tender.hellojack.business.home.meterial.swipelistview;

import com.tender.hellojack.base.BaseSchedule;
import com.tender.hellojack.data.ResourceRepository;
import com.tender.tools.utils.ui.DialogUtil;
import com.tender.tools.utils.ui.UIUtil;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by boyu
 */
public class SwipeListPresenter implements SwipeListContract.Presenter {

    private final ResourceRepository mRepository;
    private final SwipeListContract.View mView;
    private final BaseSchedule mSchedule;

    private CompositeSubscription mSubscription;

    private boolean hasInit = false;

    private List<CardTransBean> transBeanList = new ArrayList<>();

    public SwipeListPresenter(ResourceRepository mRepository, SwipeListContract.View mView, BaseSchedule mSchedule) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mSchedule = mSchedule;

        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!hasInit) {

            final CardTransBean transBean = new CardTransBean();
            transBean.setCardNo("620078 ****** 0001");
            transBean.setTransMoney("26.00");
            transBean.setTransTime("2017-10-17 16:45:26");
            transBean.setTransStatus("成功");
            transBean.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean);
                }
            });
            transBean.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean);
                }
            });
            transBean.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean);
                }
            });
            transBeanList.add(transBean);

            final CardTransBean transBean1 = new CardTransBean();
            transBean1.setCardNo("620078 ****** 0002");
            transBean1.setTransMoney("26.00");
            transBean1.setTransTime("2017-10-17 16:45:26");
            transBean1.setTransStatus("异常");
            transBean1.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean1);
                }
            });
            transBean1.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean1);
                }
            });
            transBean1.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean1);
                }
            });
            transBeanList.add(transBean1);

            final CardTransBean transBean2 = new CardTransBean();
            transBean2.setCardNo("620078 ****** 0003");
            transBean2.setTransMoney("26.00");
            transBean2.setTransTime("2017-10-17 16:45:26");
            transBean2.setTransStatus("异常");
            transBean2.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean2);
                }
            });
            transBean2.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean2);
                }
            });
            transBean2.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean2);
                }
            });
            transBeanList.add(transBean2);

            final CardTransBean transBean4 = new CardTransBean();
            transBean4.setCardNo("620078 ****** 0004");
            transBean4.setTransMoney("26.00");
            transBean4.setTransTime("2017-10-17 16:45:26");
            transBean4.setTransStatus("成功");
            transBean4.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean4);
                }
            });
            transBean4.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean4);
                }
            });
            transBean4.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean4);
                }
            });
            transBeanList.add(transBean4);

            final CardTransBean transBean3 = new CardTransBean();
            transBean3.setCardNo("620078 ****** 0005");
            transBean3.setTransMoney("26.00");
            transBean3.setTransTime("2017-10-17 16:45:26");
            transBean3.setTransStatus("异常");
            transBean3.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean3);
                }
            });
            transBean3.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean3);
                }
            });
            transBean3.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean3);
                }
            });
            transBeanList.add(transBean3);

            final CardTransBean transBean6 = new CardTransBean();
            transBean6.setCardNo("620078 ****** 0006");
            transBean6.setTransMoney("26.00");
            transBean6.setTransTime("2017-10-17 16:45:26");
            transBean6.setTransStatus("异常");
            transBean6.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean6);
                }
            });
            transBean6.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean6);
                }
            });
            transBean6.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean6);
                }
            });
            transBeanList.add(transBean6);

            final CardTransBean transBean7 = new CardTransBean();
            transBean7.setCardNo("620078 ****** 0007");
            transBean7.setTransMoney("26.00");
            transBean7.setTransTime("2017-10-17 16:45:26");
            transBean7.setTransStatus("成功");
            transBean7.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean7);
                }
            });
            transBean7.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean7);
                }
            });
            transBean7.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean7);
                }
            });
            transBeanList.add(transBean7);

            final CardTransBean transBean8= new CardTransBean();
            transBean8.setCardNo("620078 ****** 0008");
            transBean8.setTransMoney("26.00");
            transBean8.setTransTime("2017-10-17 16:45:26");
            transBean8.setTransStatus("异常");
            transBean8.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean8);
                }
            });
            transBean8.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean8);
                }
            });
            transBean8.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean8);
                }
            });
            transBeanList.add(transBean8);

            final CardTransBean transBean9 = new CardTransBean();
            transBean9.setCardNo("620078 ****** 0009");
            transBean9.setTransMoney("26.00");
            transBean9.setTransTime("2017-10-17 16:45:26");
            transBean9.setTransStatus("异常");
            transBean9.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean9);
                }
            });
            transBean9.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean9);
                }
            });
            transBean9.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean9);
                }
            });
            transBeanList.add(transBean9);

            final CardTransBean transBean10 = new CardTransBean();
            transBean10.setCardNo("620078 ****** 0010");
            transBean10.setTransMoney("26.00");
            transBean10.setTransTime("2017-10-17 16:45:26");
            transBean10.setTransStatus("成功");
            transBean10.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean10);
                }
            });
            transBean10.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean10);
                }
            });
            transBean10.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean10);
                }
            });
            transBeanList.add(transBean10);

            final CardTransBean transBean11 = new CardTransBean();
            transBean11.setCardNo("620078 ****** 0011");
            transBean11.setTransMoney("26.00");
            transBean11.setTransTime("2017-10-17 16:45:26");
            transBean11.setTransStatus("异常");
            transBean11.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean11);
                }
            });
            transBean11.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean11);
                }
            });
            transBean11.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean11);
                }
            });
            transBeanList.add(transBean11);

            final CardTransBean transBean12 = new CardTransBean();
            transBean12.setCardNo("620078 ****** 0012");
            transBean12.setTransMoney("26.00");
            transBean12.setTransTime("2017-10-17 16:45:26");
            transBean12.setTransStatus("异常");
            transBean12.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean12);
                }
            });
            transBean12.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean12);
                }
            });
            transBean12.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean12);
                }
            });
            transBeanList.add(transBean12);

            final CardTransBean transBean13 = new CardTransBean();
            transBean13.setCardNo("620078 ****** 0013");
            transBean13.setTransMoney("26.00");
            transBean13.setTransTime("2017-10-17 16:45:26");
            transBean13.setTransStatus("异常");
            transBean13.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean13);
                }
            });
            transBean13.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean13);
                }
            });
            transBean13.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean13);
                }
            });
            transBeanList.add(transBean13);

            final CardTransBean transBean14 = new CardTransBean();
            transBean14.setCardNo("620078 ****** 0014");
            transBean14.setTransMoney("26.00");
            transBean14.setTransTime("2017-10-17 16:45:26");
            transBean14.setTransStatus("成功");
            transBean14.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean14);
                }
            });
            transBean14.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean14);
                }
            });
            transBean14.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean14);
                }
            });
            transBeanList.add(transBean14);

            final CardTransBean transBean15 = new CardTransBean();
            transBean15.setCardNo("620078 ****** 0015");
            transBean15.setTransMoney("26.00");
            transBean15.setTransTime("2017-10-17 16:45:26");
            transBean15.setTransStatus("异常");
            transBean15.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean15);
                }
            });
            transBean15.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean15);
                }
            });
            transBean15.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean15);
                }
            });
            transBeanList.add(transBean15);

            final CardTransBean transBean16 = new CardTransBean();
            transBean16.setCardNo("620078 ****** 0016");
            transBean16.setTransMoney("26.00");
            transBean16.setTransTime("2017-10-17 16:45:26");
            transBean16.setTransStatus("异常");
            transBean16.setRunnableLeft(new Runnable() {
                @Override
                public void run() {
                    onClickLeft(transBean16);
                }
            });
            transBean16.setRunnableRight(new Runnable() {
                @Override
                public void run() {
                    onClickRight(transBean16);
                }
            });
            transBean16.setRunnableItem(new Runnable() {
                @Override
                public void run() {
                    onClickItem(transBean16);
                }
            });
            transBeanList.add(transBean16);

            mView.initUIData();
            hasInit = true;
        }
    }

    @Override
    public List<CardTransBean> getTransList() {
        return transBeanList;
    }

    @Override
    public void onClickLeft(CardTransBean transBean) {
        DialogUtil.showHint(UIUtil.getAppContext(), "撤销交易"+ transBean.getCardNo());
    }

    @Override
    public void onClickRight(CardTransBean transBean) {
        DialogUtil.showHint(UIUtil.getAppContext(), "补发签购单"+ transBean.getCardNo());
    }

    @Override
    public void onClickItem(CardTransBean transBean) {
        DialogUtil.showHint(UIUtil.getAppContext(), "Item点击事件"+ transBean.getCardNo());
    }
}