package com.lucas.ximdemo.xim.message;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:12 下午
 */

import com.lucas.ximdemo.xim.BaseConstants;
import com.lucas.ximdemo.xim.common.IMCallback;
import com.lucas.ximdemo.xim.common.IMLog;
import com.lucas.ximdemo.xim.manager.BaseManager;
import com.lucas.ximdemo.xim.v1.IMContext;
import com.lucas.ximdemo.xim.v1.Message;
import com.lucas.ximdemo.xim.v1.MessageKey;
import com.lucas.ximdemo.xim.v1.MessageListener;
import com.lucas.ximdemo.xim.v1.MessageReceipt;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageCenter {
    private static final String TAG = "MessageCenter";

    private Object mLockObject = new Object();
    private MessageListener mMessageListener;
    private CopyOnWriteArrayList<MessageListener> mMessageProxyListenerList;

    private static class MessageCenterHolder {
        private static final MessageCenter messageCenter = new MessageCenter();
    }

    public static MessageCenter getInstance() {
        return MessageCenter.MessageCenterHolder.messageCenter;
    }

    protected MessageCenter() {
        mMessageProxyListenerList = new CopyOnWriteArrayList<>();
    }

    public void init() {
        initMessageListener();
    }

    private void initMessageListener() {
        mMessageListener = new MessageListener() {
            @Override
            public void onReceiveNewMessage(final List<Message> messageList) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLockObject) {
                            for (MessageListener messageListener : mMessageProxyListenerList) {
                                messageListener.onReceiveNewMessage(messageList);
                            }
                        }
                    }
                });
            }

            @Override
            public void onReceiveMessageReceipt(final List<MessageReceipt> receiptList) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLockObject) {
                            for (MessageListener messageListener : mMessageProxyListenerList) {
                                messageListener.onReceiveMessageReceipt(receiptList);
                            }
                        }
                    }
                });
            }

            @Override
            public void onReceiveMessageRevoked(final List<MessageKey> keyList) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLockObject) {
                            for (MessageListener messageListener : mMessageProxyListenerList) {
                                messageListener.onReceiveMessageRevoked(keyList);
                            }
                        }
                    }
                });
            }

            @Override
            public void onReceiveMessageModified(final List<Message> messageList) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLockObject) {
                            for (MessageListener messageListener : mMessageProxyListenerList) {
                                messageListener.onReceiveMessageModified(messageList);
                            }
                        }
                    }
                });
            }
        };
        nativeSetMessageListener(mMessageListener);
    }

    public void addMessageListener(MessageListener messageListener) {
        synchronized (mLockObject) {
            mMessageProxyListenerList.add(messageListener);
        }
    }

    public String sendMessage(Message message, MessageUploadProgressCallback progressCallback, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return null;
        }

        return nativeSendMessage(message, progressCallback, callback);
    }


    public void setC2CMessageRead(String userID, long readTime, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetC2CMessageRead(userID, readTime, callback);
    }

    public void setGroupMessageRead(String groupID, long readSequence, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetGroupMessageRead(groupID, readSequence, callback);
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

    protected native void nativeSetMessageListener(MessageListener messageListener);

    protected native String nativeSendMessage(Message message, MessageUploadProgressCallback messageUploadProgressCallback, IMCallback callback);


    protected native void nativeSetC2CMessageRead(String userID, long readTime, IMCallback callback);

    protected native void nativeSetGroupMessageRead(String groupID, long readSequence, IMCallback callback);

    protected native void nativeGetC2CHistoryMessageList(String userID,
                                                         MessageListGetOption messageListGetOption,
                                                         IMCallback callback);

    protected native void nativeGetGroupHistoryMessageList(String groupID,
                                                           MessageListGetOption messageListGetOption,
                                                           IMCallback callback);

}
