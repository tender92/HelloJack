package com.tender.hellojack.utils;

import com.tender.hellojack.base.BaseSchedule;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by boyu on 2017/12/7.
 */

public class ScheduleProvider implements BaseSchedule {

    private static ScheduleProvider instance;

    synchronized public static ScheduleProvider getInstance() {
        if (instance == null) {
            instance = new ScheduleProvider();
        }
        return instance;
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.computation();
    }
}
