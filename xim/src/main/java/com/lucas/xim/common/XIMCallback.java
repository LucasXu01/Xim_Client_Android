package com.lucas.xim.common;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:37 上午
 */
public interface XIMCallback {
    void onSuccess();

    void onError(int var1, String var2);
}

