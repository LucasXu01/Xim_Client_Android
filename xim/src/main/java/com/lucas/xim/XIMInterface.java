package com.lucas.xim;

import android.content.Context;

import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.config.IMOptions;
import com.lucas.xim.conversation.Conversation;
import com.lucas.xim.message.IMMsg;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.v1.IMSDKListener;
import com.lucas.xim.v1.IMValueCallback;
import com.lucas.xim.v1.MessageListener;

import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/18  9:51 下午
 */
public interface XIMInterface {

    /**
     * 初始化xim sdk
     *
     * @param context
     * @param options
     * @param appSecret
     * @return
     */
    boolean init(Context context, IMOptions options, String appSecret);

    /**
     * add sdk级别 的listener
     *
     * @param listener
     */
    void addIMSDKListener(IMSDKListener listener);

    /**
     * remove sdk级别 的listener
     *
     * @param listener
     */
    void removeIMSDKListener(IMSDKListener listener);

    /**
     * 用户登陆
     * @param var1
     * @param var2
     * @param callback
     */
    void login(final String var1, final String var2, final IMCallback callback);

    /**
     * 获取在线人员列表
     * @param callback
     */
    void getOnlineMembers(IMCallback<List<String>> callback);

    /**
     * C2C发消息
     * @param msg
     */
    public void send2User(IMMsg msg);

    /**
     * 给群组发消息
     * @param msg
     */
    public void send2Group(IMMsg msg);

    /**
     * 获得消息
     * @param from_uid
     * @param msg
     */
    public void onRecMsg(String from_uid, String msg);

    /**
     * 获取当前登陆的本用户信息
     * @return
     */
    public String getMine();

    /**
     * 添加消息监听器
     * @param listener
     */
    void addIMMsgListener(MessageListener listener);

    /**
     * 移除消息监听器
     * @param listener
     */
    void removeIMMsgListener(MessageListener listener);

    /**
     * 添加会话监听器，有关这个会话的msg都会进行回调
     * @param conversation
     * @param listener
     */
    void addConversationListener(Conversation conversation, MessageListener listener);

    /**
     * 移除会话监听器，有关这个会话的msg都会进行回调的监听器移除（注意：多次添加需要多次移除)
     * @param conversation
     */
    void removeConversationListener(Conversation conversation);

}
