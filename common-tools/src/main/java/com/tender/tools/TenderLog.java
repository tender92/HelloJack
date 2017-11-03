package com.tender.tools;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class TenderLog {

    public static void initLogConfig(String module, final boolean logActive) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(module)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return logActive;
            }
        });
    }

    public static void d(String msg, Object... params) {
        for (Object param : params) {
            msg = msg.replaceFirst("\\{\\}", param.toString());
        }
        d(msg);
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    /**
     * Set Map，List，Array
     * @param object
     */
    public static void d(Object object) {
        Logger.d(object);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void clearLogAdapters() {
        Logger.clearLogAdapters();
    }
}
