package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:36 上午
 */
public abstract class IMSDKListener {
    public IMSDKListener() {
    }

    public void onConnecting() {
    }

    public void onConnectSuccess() {
    }

    public void onConnectFailed(int code, String error) {
    }

    public void onKickedOffline() {
    }

    public void onUserSigExpired() {
    }

    public void onSelfInfoUpdated(IMUserFullInfo info) {
    }
}
