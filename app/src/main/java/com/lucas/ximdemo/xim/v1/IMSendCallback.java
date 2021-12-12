package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:34 下午
 */
public interface IMSendCallback<T> extends IMValueCallback<T> {
    void onProgress(int var1);
}

