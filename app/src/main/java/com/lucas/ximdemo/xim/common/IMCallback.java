package com.lucas.ximdemo.xim.common;

import com.lucas.ximdemo.xim.v1.IMContext;
import com.lucas.ximdemo.xim.v1.IMValueCallback;
import com.lucas.ximdemo.xim.v1.XIMCallback;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:40 下午
 */
public abstract class IMCallback<T> {

    protected XIMCallback callback;
    protected IMValueCallback<T> valueCallback;

    protected IMCallback(XIMCallback cb) {
        this.callback = cb;
    }

    protected IMCallback(IMValueCallback<T> cb) {
        this.valueCallback = cb;
    }

    public void success(final T data) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess();
                } else if (valueCallback != null) {
                    valueCallback.onSuccess(data);
                }
            }
        });
    }

    public void fail(final int code, final String errorMessage) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(code, errorMessage);
                } else if (valueCallback != null) {
                    valueCallback.onError(code, errorMessage);
                }
            }
        });
    }

    public void fail(final int code, final String errorMessage, final T data) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(code, errorMessage);
                } else if (valueCallback != null) {
                    valueCallback.onError(code, errorMessage);
                }
            }
        });
    }
}

