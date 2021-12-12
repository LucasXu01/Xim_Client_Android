package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:24 下午
 */
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class IMContext {
    private static final String TAG = IMContext.class.getSimpleName();

    private Context mContext;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private static class Holder {
        public static IMContext instance = new IMContext();
    }

    public static IMContext getInstance() {
        return Holder.instance;
    }

    private IMContext() {

    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getApplicationContext() {
        return mContext;
    }

    public void runOnMainThread(Runnable runnable) {
        mMainHandler.post(runnable);
    }
}

