package com.lucas.ximdemo.xim.v1;

import android.text.TextUtils;

import com.lucas.ximdemo.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:12 下午
 */
class IMMessageManagerImpl  extends IMMessageManager {
    private final String TAG = "V2TIMMessageManagerImpl";
    private final int MAX_FORWARD_COUNT = 300;
    private final int MAX_ABSTRACT_COUNT = 5;
    private final int MAX_ABSTRACT_LENGTH = 100;

    private Object mLockObject = new Object();
    private MessageListener mMessageListener;
    private List<V2TIMAdvancedMsgListener> mV2TIMMsgListenerList;

    private static class V2TIMMessageManagerImplHolder {
        private static final V2TIMMessageManagerImpl v2TIMMessageManagerImpl = new V2TIMMessageManagerImpl();
    }

    static IMMessageManagerImpl getInstance() {
        return V2TIMMessageManagerImplHolder.v2TIMMessageManagerImpl;
    }

    private V2TIMMessageManagerImpl() {
        mV2TIMMsgListenerList = new CopyOnWriteArrayList<>();
    }

    void initListener() {
        mMessageListener = new MessageListener() {
            @Override
            public void onReceiveNewMessage(List<Message> messageList) {
                if (messageList == null || messageList.isEmpty()) {
                    return;
                }
                Message message = messageList.get(0);
                V2TIMMessage v2TIMMessage = new V2TIMMessage();
                v2TIMMessage.setMessage(message);
                V2TIMSignalingInfo v2TIMSignalingInfo = V2TIMSignalingManagerImpl.getInstance().processSignalingMessage(v2TIMMessage);
                if (v2TIMSignalingInfo != null && v2TIMSignalingInfo.isOnlineUserOnly()) {
                    return;
                }
                onRecvNewMessage(v2TIMMessage);
            }

            @Override
            public void onReceiveMessageReceipt(List<MessageReceipt> receiptList) {
                List<V2TIMMessageReceipt> v2TIMMessageReceiptList = new ArrayList<>();
                for (MessageReceipt messageReceipt : receiptList) {
                    V2TIMMessageReceipt v2TIMMessageReceipt = new V2TIMMessageReceipt();
                    v2TIMMessageReceipt.setMessageReceipt(messageReceipt);
                    v2TIMMessageReceiptList.add(v2TIMMessageReceipt);
                }
                synchronized (mLockObject) {
                    for (V2TIMAdvancedMsgListener listener : mV2TIMMsgListenerList) {
                        listener.onRecvC2CReadReceipt(v2TIMMessageReceiptList);
                    }
                }
            }

            @Override
            public void onReceiveMessageRevoked(List<MessageKey> keyList) {
                if (keyList == null || keyList.isEmpty()) {
                    return;
                }
                MessageKey messageKey = keyList.get(0);
                synchronized (mLockObject) {
                    for (V2TIMAdvancedMsgListener listener : mV2TIMMsgListenerList) {
                        listener.onRecvMessageRevoked(messageKey.getMessageID());
                    }
                }
            }

            @Override
            public void onReceiveMessageModified(List<Message> messageList) {
                for (Message message : messageList) {
                    V2TIMMessage v2TIMMessage = new V2TIMMessage();
                    v2TIMMessage.setMessage(message);
                    synchronized (mLockObject) {
                        for (V2TIMAdvancedMsgListener listener : mV2TIMMsgListenerList) {
                            listener.onRecvMessageModified(v2TIMMessage);
                        }
                    }
                }
            }
        };
        MessageCenter.getInstance().addMessageListener(mMessageListener);
    }

    protected void onRecvNewMessage(V2TIMMessage v2TIMMessage) {
        synchronized (mLockObject) {
            for (V2TIMAdvancedMsgListener listener : mV2TIMMsgListenerList) {
                listener.onRecvNewMessage(v2TIMMessage);
            }
        }
    }

    @Override
    public void addAdvancedMsgListener(V2TIMAdvancedMsgListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (mLockObject) {
            if (mV2TIMMsgListenerList.contains(listener)) {
                return;
            }
            mV2TIMMsgListenerList.add(listener);
        }
    }

    @Override
    public void removeAdvancedMsgListener(V2TIMAdvancedMsgListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (mLockObject) {
            mV2TIMMsgListenerList.remove(listener);
        }
    }

    @Override
    public IMMessage createTextMessage(String text) {
        IMMessage v2TIMMessage = new IMMessage();

        Message message = v2TIMMessage.getMessage();
        TextElement textElement = new TextElement();
        textElement.setTextContent(text);
        message.addElement(textElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createTextAtMessage(String text, List<String> atUserList) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        TextElement textElement = new TextElement();
        textElement.setTextContent(text);
        message.addElement(textElement);
        v2TIMMessage.setGroupAtUserList(atUserList);
        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createCustomMessage(byte[] data) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        CustomElement customElement = new CustomElement();
        customElement.setData(data);
        message.addElement(customElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createCustomMessage(byte[] data, String description, byte[] extension) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        CustomElement customElement = new CustomElement();
        customElement.setData(data);
        customElement.setDescription(description);
        customElement.setExtension(extension);
        message.addElement(customElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createImageMessage(String imagePath) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        ImageElement imageElement = new ImageElement();
        imageElement.setOriginImageFilePath(imagePath);
        message.addElement(imageElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createSoundMessage(String soundPath, int duration) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        SoundElement soundElement = new SoundElement();
        if (duration < 0) {
            duration = 0;
        }
        soundElement.setSoundDuration(duration);
        soundElement.setSoundFilePath(soundPath);
        message.addElement(soundElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createVideoMessage(String videoFilePath, String type, int duration, String snapshotPath) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        VideoElement videoElement = new VideoElement();
        videoElement.setVideoFilePath(videoFilePath);
        videoElement.setSnapshotFilePath(snapshotPath);
        if (duration < 0) {
            duration = 0;
        }
        videoElement.setVideoDuration(duration);
        videoElement.setVideoType(type);
        message.addElement(videoElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createFileMessage(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            V2TIMMessage v2TIMMessage = new V2TIMMessage();

            Message message = v2TIMMessage.getMessage();
            FileElement fileElement = new FileElement();
            fileElement.setFilePath(filePath);
            fileElement.setFileName(fileName);
            message.addElement(fileElement);

            return v2TIMMessage;
        }
        return null;
    }

    @Override
    public V2TIMMessage createLocationMessage(String desc, double longitude, double latitude) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        LocationElement locationElement = new LocationElement();
        locationElement.setLongitude(longitude);
        locationElement.setLatitude(latitude);
        locationElement.setDescription(desc);
        message.addElement(locationElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createFaceMessage(int index, byte[] data) {
        V2TIMMessage v2TIMMessage = new V2TIMMessage();

        Message message = v2TIMMessage.getMessage();
        FaceElement faceElement = new FaceElement();
        faceElement.setFaceData(data);
        faceElement.setFaceIndex(index);
        message.addElement(faceElement);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createMergerMessage(List<V2TIMMessage> messageList,
                                            String title,
                                            List<String> abstractList,
                                            String compatibleText) {
        if (messageList == null || messageList.size() == 0 || messageList.size() > MAX_FORWARD_COUNT) {
            IMLog.e(TAG, "messageList invalid, the number of messageList must be between 1 and " + MAX_FORWARD_COUNT);
            return null;
        }


        List<String> abstractListAdjust = new ArrayList<>();
        if (abstractList != null) {
            int abstractCount = abstractList.size();
            abstractCount = abstractCount >= MAX_ABSTRACT_COUNT ? MAX_ABSTRACT_COUNT : abstractCount;
            for (int i = 0; i < abstractCount; ++i) {
                String abstractItem = abstractList.get(i);
                if (abstractItem != null) {
                    if (abstractItem.length() > MAX_ABSTRACT_LENGTH) {
                        abstractItem = abstractItem.substring(0, MAX_ABSTRACT_LENGTH);
                    }
                    abstractListAdjust.add(abstractItem);
                }
            }
        }

        for (V2TIMMessage v2TIMMessage : messageList) {
            if (V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC != v2TIMMessage.getStatus()) {
                IMLog.e(TAG, "message status must be V2TIM_MSG_STATUS_SEND_SUCC");
                return null;
            }
            if (V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS == v2TIMMessage.getElemType()) {
                IMLog.e(TAG, "group tips message is not support");
                return null;
            }
        }

        if (null == compatibleText) {
            IMLog.e(TAG, "compatibleText invalid, compatibleText cannot be null");
            return null;
        }

        MergerElement mergerElement = new MergerElement();
        mergerElement.setTitle(title);
        mergerElement.setAbstractList(abstractListAdjust);
        mergerElement.setCompatibleText(compatibleText);
        List<Message> nativeMessageList = new ArrayList<>();
        for (V2TIMMessage v2TIMMessage : messageList) {
            nativeMessageList.add(v2TIMMessage.getMessage());
        }
        mergerElement.setMessageList(nativeMessageList);

        V2TIMMessage v2TIMMessage = new V2TIMMessage();
        Message message = v2TIMMessage.getMessage();
        message.addElement(mergerElement);
        message.setForward(true);

        return v2TIMMessage;
    }

    @Override
    public V2TIMMessage createForwardMessage(V2TIMMessage message) {
        if (message == null) {
            IMLog.e(TAG, "createForwardMessage, message cannot be null");
            return null;
        }
        if (V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC != message.getStatus()) {
            IMLog.e(TAG, "message status must be V2TIM_MSG_STATUS_SEND_SUCC");
            return null;
        }
        if (V2TIMMessage.V2TIM_ELEM_TYPE_GROUP_TIPS == message.getElemType()) {
            IMLog.e(TAG, "group tips message is not support");
            return null;
        }
        V2TIMMessage v2TIMMessage = new V2TIMMessage();
        Message nativeMessage = v2TIMMessage.getMessage();
        nativeMessage.setMessageBaseElements(message.getMessage().getMessageBaseElements());
        nativeMessage.setForward(true);

        return v2TIMMessage;
    }

    @Override
    public String sendMessage(final V2TIMMessage v2TIMMessage,
                              String receiver,
                              String groupID,
                              int priority,
                              boolean onlineUserOnly,
                              V2TIMOfflinePushInfo offlinePushInfo,
                              final IMSendCallback<V2TIMMessage> sendCallback) {
        if (v2TIMMessage == null) {
            if (sendCallback != null) {
                sendCallback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "message is null");
            }
            return null;
        }
        if (TextUtils.isEmpty(receiver) && TextUtils.isEmpty(groupID)) {
            if (sendCallback != null) {
                sendCallback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "receiver and groupID cannot be empty at the same time!");
            }
            return null;
        }
        if (!TextUtils.isEmpty(receiver) && !TextUtils.isEmpty(groupID)) {
            if (sendCallback != null) {
                sendCallback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "receiver and groupID cannot set at the same time!");
            }
            return null;
        }
        Message message = v2TIMMessage.getMessage();
        if (!TextUtils.isEmpty(receiver)) {
            message.setMessageType(Message.MESSAGE_TYPE_C2C);
            message.setReceiverUserID(receiver);
        } else {
            message.setMessageType(Message.MESSAGE_TYPE_GROUP);
            message.setGroupID(groupID);
        }
        message.setPriority(priority);
        if (onlineUserOnly) {
            message.setLifeTime(0);
        }
        if (offlinePushInfo != null) {
            message.setOfflinePushInfo(offlinePushInfo.getMessageOfflinePushInfo());
        }
        message.setPlatform(Message.PLATFORM_ANDROID);

        final MessageUploadProgressCallback progressCallback = new MessageUploadProgressCallback() {
            @Override
            public void onUploadProgress(int elemIndex, final int currentSize, final int totalSize) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (sendCallback != null) {
                            int progress = 0;
                            if (totalSize > 0) {
                                float decimalProgress = (float) currentSize / totalSize;
                                progress = (int) (decimalProgress * 100);
                            }
                            sendCallback.onProgress(progress);
                        }
                    }
                });
            }
        };

        V2TIMValueCallback<Message> v2TIMValueCallback = new V2TIMValueCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                if (sendCallback != null) {
                    v2TIMMessage.setMessage(message);
                    sendCallback.onSuccess(v2TIMMessage);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (sendCallback != null) {
                    sendCallback.onError(code, desc);
                }
            }
        };

        String messageID = MessageCenter.getInstance().sendMessage(message, progressCallback, new XIMCallback(v2TIMValueCallback) {
            @Override
            public void success(Object data) {
                Message message = v2TIMMessage.getMessage();
                message.update((Message) data);
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage, Object data) {
                Message message = v2TIMMessage.getMessage();
                message.update((Message) data);
                super.fail(code, errorMessage, data);
            }
        });

        BaseManager.getInstance().checkTUIComponent(BaseManager.TUI_COMPONENT_CHAT);

        return messageID;
    }

    @Override
    public void setC2CReceiveMessageOpt(List<String> userIDList, int opt, V2TIMCallback callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid userIDList");
            }
            return;
        }

        int receive_option = UserInfo.USER_RECEIVE_MESSAGE_NATIVE;
        if (opt == V2TIMMessage.V2TIM_RECEIVE_MESSAGE) {
            receive_option = UserInfo.USER_RECEIVE_MESSAGE_NATIVE;
        } else if (opt == V2TIMMessage.V2TIM_NOT_RECEIVE_MESSAGE) {
            receive_option = UserInfo.USER_NOT_RECEIVE_MESSAGE_NATIVE;
        } else if (opt == V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE) {
            receive_option = UserInfo.USER_RECEIVE_NOT_NOTIFY_MESSAGE_NATIVE;
        } else {
            IMLog.e(TAG, "setC2CReceiveMessageOpt error opt = " + opt);
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "opt is error");
            }
            return;
        }

        RelationshipManager.getInstance().setC2CReceiveMessageOpt(userIDList, receive_option, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getC2CReceiveMessageOpt(List<String> userIDList, final V2TIMValueCallback<List<V2TIMReceiveMessageOptInfo>> callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid userIDList");
            }
            return;
        }

        V2TIMValueCallback<List<ReceiveMessageOptInfo>> v2Callback = new V2TIMValueCallback<List<ReceiveMessageOptInfo>>() {
            @Override
            public void onSuccess(List<ReceiveMessageOptInfo> userInfos) {
                if (callback != null) {
                    List<V2TIMReceiveMessageOptInfo> v2UserInfoList = new ArrayList<>();
                    for (ReceiveMessageOptInfo item : userInfos) {
                        V2TIMReceiveMessageOptInfo v2UserInfo = new V2TIMReceiveMessageOptInfo();
                        v2UserInfo.setUserID(item.getUserID());
                        v2UserInfo.setC2CReceiveMessageOpt(item.getC2CReceiveMessageOpt());
                        v2UserInfoList.add(v2UserInfo);
                    }
                    callback.onSuccess(v2UserInfoList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getC2CReceiveMessageOpt(userIDList, new XIMCallback<List<ReceiveMessageOptInfo>>(v2Callback) {
            @Override
            public void success(List<ReceiveMessageOptInfo> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void setGroupReceiveMessageOpt(String groupID, int opt, final V2TIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            IMLog.e(TAG, "setReceiveMessageOpt err, groupID is empty");
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty");
            }
            return;
        }

        int receive_option = GroupMemberInfo.MESSAGE_RECEIVE_OPTION_AUTO_RECEIVE;
        if (opt == V2TIMMessage.V2TIM_RECEIVE_MESSAGE) {
            receive_option = GroupMemberInfo.MESSAGE_RECEIVE_OPTION_AUTO_RECEIVE;
        } else if (opt == V2TIMMessage.V2TIM_NOT_RECEIVE_MESSAGE) {
            receive_option = GroupMemberInfo.MESSAGE_RECEIVE_OPTION_NOT_RECEIVE;
        } else if (opt == V2TIMMessage.V2TIM_RECEIVE_NOT_NOTIFY_MESSAGE) {
            receive_option = GroupMemberInfo.MESSAGE_RECEIVE_OPTION_RECEIVE_WITH_NO_OFFLINE_PUSH;
        } else {
            IMLog.e(TAG, "setReceiveMessageOpt error opt = " + opt);
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "opt is error");
            }
            return;
        }

        GroupManager.getInstance().setGroupReceiveMessageOpt(groupID, receive_option, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getC2CHistoryMessageList(String userID, int count, V2TIMMessage lastMsg, final V2TIMValueCallback<List<V2TIMMessage>> callback) {
        if (TextUtils.isEmpty(userID) || count == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID is empty or count is 0");
            }
            return;
        }

        V2TIMValueCallback<List<Message>> v2TIMValueCallback = new V2TIMValueCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<V2TIMMessage> v2TIMMessageList = new ArrayList<>();

                for (Message message : messageList) {
                    V2TIMMessage v2TIMMessage = new V2TIMMessage();
                    v2TIMMessage.setMessage(message);

                    v2TIMMessageList.add(v2TIMMessage);
                }

                if (callback != null) {
                    callback.onSuccess(v2TIMMessageList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }

        };

        MessageKey messageKey = null;
        if (lastMsg != null) {
            messageKey = lastMsg.getMessage().getMessageKey();
        }

        MessageListGetOption messageListGetOption = new MessageListGetOption();
        messageListGetOption.setCount(count);
        messageListGetOption.setToOlderMessage(true);
        messageListGetOption.setGetCloudMessage(true);
        messageListGetOption.setMessageKey(messageKey);

        MessageCenter.getInstance().getC2CHistoryMessageList(userID, messageListGetOption, new XIMCallback<List<Message>>(v2TIMValueCallback) {
            @Override
            public void success(List<Message> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getGroupHistoryMessageList(String groupID, int count, V2TIMMessage lastMsg, final V2TIMValueCallback<List<V2TIMMessage>> callback) {
        if (TextUtils.isEmpty(groupID) || count <= 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty or count is 0");
            }
            return;
        }
        V2TIMValueCallback<List<Message>> v2TIMValueCallback = new V2TIMValueCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<V2TIMMessage> v2TIMMessageList = new ArrayList<>();

                for (Message message : messageList) {
                    V2TIMMessage v2TIMMessage = new V2TIMMessage();
                    v2TIMMessage.setMessage(message);

                    v2TIMMessageList.add(v2TIMMessage);
                }

                if (callback != null) {
                    callback.onSuccess(v2TIMMessageList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }

        };

        MessageKey messageKey = null;
        if (lastMsg != null) {
            messageKey = lastMsg.getMessage().getMessageKey();
        }

        MessageListGetOption messageListGetOption = new MessageListGetOption();
        messageListGetOption.setCount(count);
        messageListGetOption.setToOlderMessage(true);
        messageListGetOption.setGetCloudMessage(true);
        messageListGetOption.setMessageKey(messageKey);

        MessageCenter.getInstance().getGroupHistoryMessageList(groupID, messageListGetOption, new XIMCallback<List<Message>>(v2TIMValueCallback) {
            @Override
            public void success(List<Message> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getHistoryMessageList(V2TIMMessageListGetOption option, final V2TIMValueCallback<List<V2TIMMessage>> callback) {
        if (option == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "option is null");
            }
            return;
        }
        if (TextUtils.isEmpty(option.getUserID()) && TextUtils.isEmpty(option.getGroupID())) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID and userID cannot be null at the same time");
            }
            return;
        }
        if (!TextUtils.isEmpty(option.getUserID()) && !TextUtils.isEmpty(option.getGroupID())) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID and userID cannot set at the same time");
            }
            return;
        }
        if (option.getCount() <= 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "count less than 0");
            }
            return;
        }

        MessageListGetOption messageListGetOption = new MessageListGetOption();
        messageListGetOption.setCount(option.getCount());
        messageListGetOption.setGetTimeBegin(option.getGetTimeBegin());
        messageListGetOption.setGetTimePeriod(option.getGetTimePeriod());

        switch (option.getGetType()) {
            case V2TIMMessageListGetOption.V2TIM_GET_LOCAL_NEWER_MSG:
                messageListGetOption.setToOlderMessage(false);
                messageListGetOption.setGetCloudMessage(false);
                break;
            case V2TIMMessageListGetOption.V2TIM_GET_LOCAL_OLDER_MSG:
                messageListGetOption.setToOlderMessage(true);
                messageListGetOption.setGetCloudMessage(false);
                break;
            case V2TIMMessageListGetOption.V2TIM_GET_CLOUD_NEWER_MSG:
                messageListGetOption.setToOlderMessage(false);
                messageListGetOption.setGetCloudMessage(true);
                break;
            case V2TIMMessageListGetOption.V2TIM_GET_CLOUD_OLDER_MSG:
                messageListGetOption.setToOlderMessage(true);
                messageListGetOption.setGetCloudMessage(true);
                break;
            default:
                if (callback != null) {
                    callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "getType is invalid");
                }
                return;
        }

        if (option.getLastMsg() != null) {
            messageListGetOption.setMessageKey(option.getLastMsg().getMessage().getMessageKey());
        } else {
            if (!TextUtils.isEmpty(option.getGroupID())) {
                if (option.getLastMsgSeq() > 0) {
                    MessageKey messageKey = new MessageKey();
                    messageKey.setSeq(option.getLastMsgSeq());
                    messageListGetOption.setMessageKey(messageKey);
                }
            }
        }

        V2TIMValueCallback<List<Message>> v2TIMValueCallback = new V2TIMValueCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<V2TIMMessage> v2TIMMessageList = new ArrayList<>();

                for (Message message : messageList) {
                    V2TIMMessage v2TIMMessage = new V2TIMMessage();
                    v2TIMMessage.setMessage(message);

                    v2TIMMessageList.add(v2TIMMessage);
                }

                if (callback != null) {
                    callback.onSuccess(v2TIMMessageList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }

        };

        MessageKey messageKey = null;
        if (option.getLastMsg() != null) {
            messageKey = option.getLastMsg().getMessage().getMessageKey();
        }
        if (!TextUtils.isEmpty(option.getUserID())) {
            MessageCenter.getInstance().getC2CHistoryMessageList(option.getUserID(), messageListGetOption, new XIMCallback<List<Message>>(v2TIMValueCallback) {
                @Override
                public void success(List<Message> data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        } else {
            MessageCenter.getInstance().getGroupHistoryMessageList(option.getGroupID(), messageListGetOption, new XIMCallback<List<Message>>(v2TIMValueCallback) {
                @Override
                public void success(List<Message> data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        }
    }

    @Override
    public void revokeMessage(V2TIMMessage v2TIMMessage, V2TIMCallback callback) {
        if (v2TIMMessage == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "msg is null");
            }
            return;
        }

        if (v2TIMMessage.getStatus() != V2TIMMessage.V2TIM_MSG_STATUS_SEND_SUCC) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "msg status must be V2TIM_MSG_STATUS_SEND_SUCC");
            }
            return;
        }

        MessageCenter.getInstance().revokeMessage(v2TIMMessage.getMessage().getMessageKey(), new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void markC2CMessageAsRead(String userID, V2TIMCallback callback) {
        if (TextUtils.isEmpty(userID)) {
            // 清空所有 c2c 会话的未读消息数
            ConversationManager.getInstance().clearUnreadMessage(true, false, new XIMCallback(callback) {
                @Override
                public void success(Object data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        } else {
            // 标记指定 C2C 会话已读
            MessageCenter.getInstance().setC2CMessageRead(userID, 0, new XIMCallback(callback) {
                @Override
                public void success(Object data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        }
    }

    @Override
    public void markGroupMessageAsRead(String groupID, V2TIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            // 清空所有 Group 会话的未读消息数
            ConversationManager.getInstance().clearUnreadMessage(false, true, new XIMCallback(callback) {
                @Override
                public void success(Object data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        } else {
            // 标记指定的 Group 会话已读
            MessageCenter.getInstance().setGroupMessageRead(groupID, 0, new XIMCallback(callback) {
                @Override
                public void success(Object data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });
        }
    }

    @Override
    public void markAllMessageAsRead(V2TIMCallback callback) {
        ConversationManager.getInstance().clearUnreadMessage(true, true, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteMessageFromLocalStorage(V2TIMMessage v2TIMMessage, V2TIMCallback callback) {
        if (v2TIMMessage == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "message is null");
            }
            return;
        }

        MessageCenter.getInstance().deleteLocalMessage(v2TIMMessage.getMessage().getMessageKey(), new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteMessages(List<V2TIMMessage> messages, V2TIMCallback callback) {
        if (messages == null || messages.isEmpty()) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "messages is invalid");
            }
            return;
        }

        List<MessageKey> messageKeyList = new ArrayList<>();
        for (V2TIMMessage v2TIMMessage : messages) {
            messageKeyList.add(v2TIMMessage.getMessage().getMessageKey());
        }

        MessageCenter.getInstance().deleteCloudMessageList(messageKeyList, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void clearC2CHistoryMessage(String userID, V2TIMCallback callback) {
        if (TextUtils.isEmpty(userID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID is empty");
            }
            return;
        }

        MessageCenter.getInstance().clearC2CHistoryMessage(userID, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void clearGroupHistoryMessage(String groupID, V2TIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty");
            }
            return;
        }

        MessageCenter.getInstance().clearGroupHistoryMessage(groupID, new XIMCallback(callback) {
            @Override
            public void success(Object data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public String insertGroupMessageToLocalStorage(final V2TIMMessage v2TIMMessage, String groupID, String sender, final V2TIMValueCallback<V2TIMMessage> callback) {
        if (v2TIMMessage == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "message is null!");
            }
            return "";
        }
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty");
            }
            return "";
        }
        if (TextUtils.isEmpty(sender)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "sender is empty");
            }
            return "";
        }
        Message message = v2TIMMessage.getMessage();
        message.setMessageType(Message.MESSAGE_TYPE_GROUP);
        message.setSenderUserID(sender);
        message.setGroupID(groupID);

        V2TIMValueCallback<Message> v2TIMValueCallback = new V2TIMValueCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                if (callback != null) {
                    v2TIMMessage.setMessage(message);
                    callback.onSuccess(v2TIMMessage);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        String messageID = MessageCenter.getInstance().insertLocalMessage(message, new XIMCallback(v2TIMValueCallback) {
            @Override
            public void success(Object data) {
                Message message = v2TIMMessage.getMessage();
                message.update((Message) data);
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        return messageID;
    }

    @Override
    public String insertC2CMessageToLocalStorage(final V2TIMMessage v2TIMMessage, String userID, String sender, final V2TIMValueCallback<V2TIMMessage> callback) {
        if (v2TIMMessage == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "message is null!");
            }
            return "";
        }
        if (TextUtils.isEmpty(userID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID is empty");
            }
            return "";
        }
        if (TextUtils.isEmpty(sender)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "sender is empty");
            }
            return "";
        }
        Message message = v2TIMMessage.getMessage();
        message.setMessageType(Message.MESSAGE_TYPE_C2C);
        message.setSenderUserID(sender);
        message.setReceiverUserID(userID);

        V2TIMValueCallback<Message> v2TIMValueCallback = new V2TIMValueCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                if (callback != null) {
                    v2TIMMessage.setMessage(message);
                    callback.onSuccess(v2TIMMessage);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        String messageID = MessageCenter.getInstance().insertLocalMessage(message, new XIMCallback(v2TIMValueCallback) {
            @Override
            public void success(Object data) {
                Message message = v2TIMMessage.getMessage();
                message.update((Message) data);
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        return messageID;
    }

    @Override
    public void findMessages(List<String> messageIDList, final V2TIMValueCallback<List<V2TIMMessage>> callback) {
        if (messageIDList == null || messageIDList.isEmpty()) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "messages is empty");
            }
            return;
        }
        V2TIMValueCallback<List<Message>> v2TIMValueCallback = new V2TIMValueCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messageList) {
                List<V2TIMMessage> v2TIMMessageList = new ArrayList<>();

                for (Message message : messageList) {
                    V2TIMMessage v2TIMMessage = new V2TIMMessage();
                    v2TIMMessage.setMessage(message);

                    v2TIMMessageList.add(v2TIMMessage);
                }

                if (callback != null) {
                    callback.onSuccess(v2TIMMessageList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }

        };
        MessageCenter.getInstance().findMessageByMessageId(messageIDList, new XIMCallback<List<Message>>(v2TIMValueCallback) {
            @Override
            public void success(List<Message> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void searchLocalMessages(V2TIMMessageSearchParam param,
                                    final V2TIMValueCallback<V2TIMMessageSearchResult> callback) {
        if (param.getSearchTimePosition() < 0 || param.getSearchTimePeriod() < 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "time < 0");
            }
            return;
        }

        if (param.getPageIndex() < 0 || param.getPageSize() < 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "pageIndex or pageSize is invalid");
            }
            return;
        }

        V2TIMValueCallback<MessageSearchResult> v2TIMValueCallback = new V2TIMValueCallback<MessageSearchResult>() {
            @Override
            public void onSuccess(MessageSearchResult messageSearchResult) {
                V2TIMMessageSearchResult v2TIMMessageSearchResult = new V2TIMMessageSearchResult();
                v2TIMMessageSearchResult.setMessageSearchResult(messageSearchResult);

                if (callback != null) {
                    callback.onSuccess(v2TIMMessageSearchResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        MessageCenter.getInstance().findMessageBySearchKey(param.getMessageSearchParam(), new XIMCallback<MessageSearchResult>(v2TIMValueCallback) {
            @Override
            public void success(MessageSearchResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        BaseManager.getInstance().checkTUIComponent(BaseManager.TUI_COMPONENT_SEARCH);
    }

}