package com.tender.hellojack.base;

import rx.Scheduler;

/**
 * Created by boyu on 2017/12/7.
 */

public interface BaseSchedule {
    Scheduler io();
    Scheduler ui();
    Scheduler compute();
}
