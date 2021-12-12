package com.lucas.ximdemo.xim.common;
import android.text.TextUtils;
import android.util.Log;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  6:26 下午
 */


public class IMLog {
    private static final String TAG = IMLog.class.getSimpleName();

    public static final int LOG_LEVEL_OFF = 0;
    public static final int LOG_LEVEL_VERBOSE = 2;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_INFO = 4;
    public static final int LOG_LEVEL_WARN = 5;
    public static final int LOG_LEVEL_ERROR = 6;

    /**
     * 打印INFO级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void v(String strTag, String strInfo) {
        log(LOG_LEVEL_VERBOSE, strTag, strInfo);
    }

    /**
     * 打印DEBUG级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void d(String strTag, String strInfo) {
        log(LOG_LEVEL_DEBUG, strTag, strInfo);
    }

    /**
     * 打印INFO级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void i(String strTag, String strInfo) {
        log(LOG_LEVEL_INFO, strTag, strInfo);
    }

    /**
     * 打印WARN级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void w(String strTag, String strInfo) {
        log(LOG_LEVEL_WARN, strTag, strInfo);
    }

    /**
     * 打印ERROR级别日志
     *
     * @param strTag  TAG
     * @param strInfo 消息
     */
    public static void e(String strTag, String strInfo) {
        log(LOG_LEVEL_ERROR, strTag, strInfo);
    }

    private static void log(int logLevel, String strTag, String strInfo) {

        if (logLevel < LOG_LEVEL_OFF || logLevel > LOG_LEVEL_ERROR) {
            IMLog.e(TAG, "invalid logLevel： " + logLevel);
            return;
        }

        if (TextUtils.isEmpty(strTag)) {
            IMLog.e(TAG, "empty logTag" );
            return;
        }

        if (TextUtils.isEmpty(strInfo)) {
            IMLog.e(TAG, "empty logContent");
            return;
        }

        switch(logLevel){
            case LOG_LEVEL_OFF:

                break;
            case LOG_LEVEL_VERBOSE:
                Log.v(strTag, strInfo);
                break;
            case LOG_LEVEL_DEBUG:
                Log.d(strTag, strInfo);
                break;
            case LOG_LEVEL_INFO:
                Log.i(strTag, strInfo);
                break;
            case LOG_LEVEL_WARN:
                Log.w(strTag, strInfo);
                break;
            case LOG_LEVEL_ERROR:
                Log.e(strTag, strInfo);
                break;
        }

    }

    /**
     * 打印异常堆栈信息
     * @param strTag
     * @param strInfo
     * @param tr
     */
    public static void writeException(String strTag, String strInfo, Throwable tr) {
        Log.e(strTag, strInfo + " exception : " + Log.getStackTraceString(tr));
    }

}
