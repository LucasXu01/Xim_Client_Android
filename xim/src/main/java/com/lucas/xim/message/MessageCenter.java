package com.lucas.xim.message;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:12 下午
 */

import com.lucas.xim.BaseConstants;
import com.lucas.xim.conversation.Conversation;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.common.IMLog;
import com.lucas.xim.BaseManager;
import com.lucas.xim.v1.IMContext;
import com.lucas.xim.v1.MessageListener;
import com.lucas.xim.ximcore.client.console.SendToUserConsoleCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageCenter {
    private static final String TAG = "MessageCenter";

    private Object mLockObject = new Object();
    private CopyOnWriteArrayList<MessageListener> mMessageListenerList;
    private Map<Conversation, MessageListener> conversationMessageListenerMap;

    private static class MessageCenterHolder {
        private static final MessageCenter messageCenter = new MessageCenter();
    }

    public static MessageCenter getInstance() {
        return MessageCenter.MessageCenterHolder.messageCenter;
    }

    protected MessageCenter() {
        mMessageListenerList = new CopyOnWriteArrayList<>();
    }

    public void init() {
        conversationMessageListenerMap = new HashMap<>();
    }



    public void addMessageListener(MessageListener messageListener) {
        if (messageListener == null) {
            return;
        }
        synchronized (mLockObject) {
            if (mMessageListenerList.contains(messageListener)) {
                return;
            }
            mMessageListenerList.add(messageListener);
        }
    }

    public void removeMessageListener(MessageListener messageListener) {
        if (messageListener == null) {
            return;
        }
        synchronized (mLockObject) {
            mMessageListenerList.remove(messageListener);
        }
    }

    public void addConversationListener(Conversation conversation, MessageListener listener) {
        if (listener == null || conversation == null) {
            IMLog.e(TAG, "listener == null || conversation == null");
            return;
        }
        synchronized (mLockObject) {
            conversationMessageListenerMap.put(conversation, listener);
        }
    }

    public void removeConversationListener(Conversation conversation) {
        if (conversation == null) {
            IMLog.e(TAG, "listener == null || conversation == null");
            return;
        }
        synchronized (mLockObject) {
            conversationMessageListenerMap.remove(conversation);
        }
    }

    public void sendMessage(IMMsg message) {
        if (!BaseManager.getInstance().isInited()) {
            IMLog.d(TAG, BaseConstants.ERR_SDK_NOT_INITIALIZED + ": sdk not init");
            return;
        }
        if (!BaseManager.getInstance().isLogin) {
            IMLog.d(TAG, BaseConstants.ERROR_NOT_LOGIN + ": 用户未登录");
            return;
        }
        IMLog.d(TAG, "sendMessage()");
        SendToUserConsoleCommand sendToUserConsoleCommand = new SendToUserConsoleCommand();
        sendToUserConsoleCommand.exec(BaseManager.getInstance().getChannel(), message);
    }


    public void onReceiveMsg(IMMsg imMsg) {
        if (!BaseManager.getInstance().isInited()) {
            IMLog.d(TAG, BaseConstants.ERR_SDK_NOT_INITIALIZED + ": sdk not init");
            return;
        }
        if (imMsg == null) {
            IMLog.d(TAG, BaseConstants.ERROR_MSG_NULL + ": messageList为null");
            return;
        }
        if (mMessageListenerList == null) {
            IMLog.d(TAG, ": mMessageListenerList为null");
            return;
        }

        //会话的listener回调
        for (Conversation conversation : conversationMessageListenerMap.keySet()){
            if (conversation.getConversationType() == imMsg.getMsgType() && conversation.getConversationId().equals(imMsg.getReceiver())){
                conversationMessageListenerMap.get(conversation).onReceiveNewMessage(imMsg);
            }
        }

        //全局的listener回调
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                synchronized (mLockObject) {
                    for (MessageListener messageListener : mMessageListenerList) {
                        messageListener.onReceiveNewMessage(imMsg);
                    }
                }
            }
        });

    }

    public void getC2CHistoryMessageList(String userID,
                                         MessageListGetOption messageListGetOption,
                                         IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetC2CHistoryMessageList(userID, messageListGetOption, callback);
    }

    public void getGroupHistoryMessageList(String groupID,
                                           MessageListGetOption messageListGetOption,
                                           IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupHistoryMessageList(groupID, messageListGetOption, callback);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected native void nativeGetC2CHistoryMessageList(String userID,
                                                         MessageListGetOption messageListGetOption,
                                                         IMCallback callback);

    protected native void nativeGetGroupHistoryMessageList(String groupID,
                                                           MessageListGetOption messageListGetOption,
                                                           IMCallback callback);

}
