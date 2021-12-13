package com.lucas.xim;


import com.lucas.xim.v1.UserInfo;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:25 下午
 */

public interface SDKListener {
    /**
     * SDK 正在连接到服务器
     */
    public void onConnecting();

    /**
     * SDK 已经成功连接到服务器
     */
    public void onConnectSuccess();

    /**
     * SDK 连接服务器失败
     */
    public void onConnectFailed(int code, String error);

    /**
     * 当前用户被踢下线，此时可以 UI 提示用户，并再次调用 V2TIMManager 的 login() 函数重新登录。
     */
    public void onKickedOffline();

    /**
     * 在线时票据过期：此时您需要生成新的 userSig 并再次调用 V2TIMManager 的 login() 函数重新登录。
     */
    public void onUserSigExpired();

    /**
     * 登录用户的资料发生了更新
     *
     */
    public void onSelfInfoUpdated(UserInfo info);


}

