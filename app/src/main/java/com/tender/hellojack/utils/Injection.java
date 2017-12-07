package com.tender.hellojack.utils;

import com.tender.hellojack.data.ResourceRepository;
import com.tender.hellojack.data.local.LocalResource;
import com.tender.hellojack.data.remote.RemoteResource;

/**
 * Created by boyu on 2017/12/7.
 */

public class Injection {
    public static ResourceRepository provideRepository() {
       return ResourceRepository.getInstance(LocalResource.getInstance(), RemoteResource.getInstance());
    }
    public static ScheduleProvider provideSchedule() {
        return ScheduleProvider.getInstance();
    }
}
