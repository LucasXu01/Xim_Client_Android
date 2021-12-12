package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:41 上午
 */
public interface IMValueCallback<T> {
    void onSuccess(T var1);

    void onError(int var1, String var2);
}
