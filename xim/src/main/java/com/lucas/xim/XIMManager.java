package com.lucas.xim;

import android.content.Context;
import android.text.TextUtils;

import com.lucas.xim.common.IMLog;
import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.config.IMOptions;
import com.lucas.xim.conversation.Conversation;
import com.lucas.xim.message.IMMsg;
import com.lucas.xim.message.IMMsgManagerImpl;
import com.lucas.xim.message.MessageCenter;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.v1.IMContext;
import com.lucas.xim.v1.IMSDKListener;
import com.lucas.xim.v1.IMValueCallback;
import com.lucas.xim.v1.MessageListener;
import com.lucas.xim.v1.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/18  9:54 下午
 */
public class XIMManager implements XIMInterface {
    public static final String TAG = XIMManager.class.getSimpleName();
    private final List<IMSDKListener> mIMSDKListenerList = new ArrayList<>();

    private XIMManager() {
    }

    public static XIMManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final XIMManager sInstance = new XIMManager();
    }

    @Override
    public boolean init(Context context, IMOptions options, String appSecret) {
        return BaseManager.getInstance().initSDK(context, options, new SDKListener() {
            @Override
            public void onConnecting() {

            }

            @Override
            public void onConnectSuccess() {
                IMLog.d(TAG, "init onConnectSuccess连接成功！");
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onConnectSuccess();
                        }
                    }
                });
            }

            @Override
            public void onConnectFailed(int code, String error) {

            }

            @Override
            public void onKickedOffline() {

            }

            @Override
            public void onUserSigExpired() {

            }

            @Override
            public void onSelfInfoUpdated(UserInfo info) {

            }
        });
    }

    @Override
    public void addIMSDKListener(final IMSDKListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mIMSDKListenerList.contains(listener)) {
                    return;
                }
                mIMSDKListenerList.add(listener);
            }
        });
    }

    @Override
    public void removeIMSDKListener(final IMSDKListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mIMSDKListenerList.remove(listener);
            }
        });
    }

    @Override
    public void login(final String uid, final String token, final IMCallback callback) {
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(token)) {
            callback.fail(BaseConstants.ERR_INVALID_PARAMETERS, "uid or token is empty");
            return;
        }

        BaseManager.getInstance().login(uid, token, callback);

    }

    @Override
    public void getOnlineMembers(IMCallback<List<String>> callback) {
        BaseManager.getInstance().getOnlineMembers(callback);
    }

    @Override
    public void send2User(IMMsg msg) {
        if (msg.getMsgType() != IMMsg.MESSAGE_TYPE_C2C){
            IMLog.e(TAG, "消息类型错误");
            return;
        }
        if (TextUtils.isEmpty(msg.getReceiver())){
            IMLog.e(TAG, "消息类型没有接受者");
            return;
        }
        IMMsgManagerImpl.getInstance().sendMessage(msg);
    }

    @Override
    public void send2Group(IMMsg msg) {
        if (msg.getMsgType() != IMMsg.MESSAGE_TYPE_GROUP){
            IMLog.e(TAG, "消息类型错误");
            return;
        }
        if (TextUtils.isEmpty(msg.getReceiver())){
            IMLog.e(TAG, "消息类型没有接受者");
            return;
        }
        MessageCenter.getInstance().sendMessage(msg);
    }

    @Override
    public void onRecMsg(String from_uid, String msg) {

    }

    @Override
    public String getMine() {
        return BaseManager.getInstance().myUid;
    }

    @Override
    public void addIMMsgListener(MessageListener listener) {
        IMMsgManagerImpl.getInstance().addMsgListener(listener);
    }

    @Override
    public void removeIMMsgListener(MessageListener listener) {
        IMMsgManagerImpl.getInstance().removeMsgListener(listener);
    }

    @Override
    public void addConversationListener(Conversation conversation, MessageListener listener) {
        IMMsgManagerImpl.getInstance().addConversationListener(conversation, listener);
    }

    @Override
    public void removeConversationListener(Conversation conversation) {
        IMMsgManagerImpl.getInstance().removeConversationListener(conversation);
    }

}
