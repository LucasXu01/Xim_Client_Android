package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  11:56 上午
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    public static int MESSAGE_TYPE_UNKNOWN      = 0x0;
    public static int MESSAGE_TYPE_C2C          = 0x1;
    public static int MESSAGE_TYPE_GROUP        = 0x2;
    public static int MESSAGE_TYPE_MULTI_SYNC   = 0x3;

    public static int PLATFORM_OTHER = 0;
    public static int PLATFORM_WINDOWS = 1;
    public static int PLATFORM_ANDROID = 2;
    public static int PLATFORM_IOS = 3;
    public static int PLATFORM_MAC = 4;
    public static int PLATFORM_SIMULATOR = 5;

    public static final int V2TIM_MSG_STATUS_SENDING = 1;
    public static final int V2TIM_MSG_STATUS_SUCCESS = 2;
    public static final int V2TIM_MSG_STATUS_SEND_FAILED = 3;
    public static final int V2TIM_MSG_STATUS_DELETED = 4;
    public static final int V2TIM_MSG_STATUS_LOCAL_IMPORTED = 5;
    public static final int V2TIM_MSG_STATUS_REVOKED = 6;

    private String msgID = "";
    private int messageType;
    private long clientTime;
    private long serverTime;
    private String senderUserID;
    private long senderTinyID;
    private String receiverUserID;
    private long receiverTinyID;
    private String nickName;
    private String friendRemark;
    private String faceUrl;
    private String nameCard;
    private String groupID;
    private boolean isForward;
    private boolean isMessageSender = true;
    private boolean isSelfRead;
    private boolean isPeerRead;
    private long random;
    private long seq;
    private int lifeTime = -1;
    private int messageStatus;
    private int priority;
    private MessageOfflinePushInfo offlinePushInfo;
    private int localCustomNumber;
    private String localCustomString;
    private String cloudCustomString;
    private List<MessageBaseElement> messageBaseElements = new ArrayList<>();
    private int platform;
    private List<MessageAtInfo> messageGroupAtInfoList = new ArrayList<>();
    private boolean excludedFromUnreadCount = false; // true - 不计入未读数，false - 计入未读数
    private boolean excludedFromLastMessage = false; // true - 不更新会话最后一条消息，false - 更新会话最后一条消息

    public String getMsgID() {
        return msgID;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getTimestamp() {
        if (serverTime > 0) {
            return serverTime;
        }
        return clientTime;
    }

    public long getClientTime() {
        return clientTime;
    }

    public void setClientTime(long clientTime) {
        this.clientTime = clientTime;
    }

    public String getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getReceiverUserID() {
        return receiverUserID;
    }

    public void setReceiverUserID(String receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public String getNameCard() {
        return nameCard;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setForward(boolean forward) {
        isForward = forward;
    }

    public boolean isMessageSender() {
        return isMessageSender;
    }

    public void setIsMessageSender(boolean isMessageSender) {
        this.isMessageSender = isMessageSender;
    }

    public boolean isSelfRead() {
        if (isSelfRead) {
            return true;
        } else {
            isSelfRead = MessageCenter.getInstance().isMessageSelfRead(getMessageKey());
            return isSelfRead;
        }
    }

    public boolean isPeerRead() {
        if (isPeerRead) {
            return true;
        } else {
            isPeerRead = MessageCenter.getInstance().isMessagePeerRead(getMessageKey());
            return isPeerRead;
        }
    }

    public long getRandom() {
        return random;
    }

    public long getSeq() {
        return seq;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int status) {
        messageStatus = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MessageOfflinePushInfo getOfflinePushInfo() {
        return offlinePushInfo;
    }

    public void setOfflinePushInfo(MessageOfflinePushInfo offlinePushInfo) {
        this.offlinePushInfo = offlinePushInfo;
    }

    public int getLocalCustomNumber() {
        return localCustomNumber;
    }

    public void setLocalCustomNumber(int customNumberInfo) {
        this.localCustomNumber = customNumberInfo;
        MessageCenter.getInstance().setLocalCustomNumber(this, customNumberInfo);
    }

    public String getLocalCustomString() {
        return localCustomString;
    }

    public void setLocalCustomString(String customStringInfo) {
        this.localCustomString = customStringInfo;
        MessageCenter.getInstance().setLocalCustomString(this, customStringInfo);
    }

    public String getCloudCustomString() {
        return cloudCustomString;
    }

    public void setCloudCustomString(String cloudCustomData) {
        this.cloudCustomString = cloudCustomData;
    }

    public void addElement(MessageBaseElement messageBaseElement) {
        if (messageBaseElement == null) {
            return;
        }
        messageBaseElements.add(messageBaseElement);
    }

    public List<MessageBaseElement> getMessageBaseElements() {
        return messageBaseElements;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setMessageBaseElements(List<MessageBaseElement> messageBaseElements) {
        this.messageBaseElements = messageBaseElements;
    }

    protected void addMessageGroupAtInfo(MessageAtInfo messageAtInfo) {
        this.messageGroupAtInfoList.add(messageAtInfo);
    }

    public void setMessageGroupAtInfoList(List<MessageAtInfo> messageGroupAtInfoList) {
        this.messageGroupAtInfoList = messageGroupAtInfoList;
    }

    public List<MessageAtInfo> getMessageGroupAtInfoList() {
        return messageGroupAtInfoList;
    }

    public boolean isExcludedFromUnreadCount() {
        return excludedFromUnreadCount;
    }

    public void setExcludedFromUnreadCount(boolean excludedFromUnreadCount) {
        this.excludedFromUnreadCount = excludedFromUnreadCount;
    }

    public boolean isExcludedFromLastMessage() {
        return excludedFromLastMessage;
    }

    public void setExcludedFromLastMessage(boolean excludedFromLastMessage) {
        this.excludedFromLastMessage = excludedFromLastMessage;
    }

    public MessageKey getMessageKey() {
        MessageKey messageKey = new MessageKey();
        messageKey.setMessageID(msgID);
        messageKey.setMessageType(messageType);
        messageKey.setIsMessageSender(isMessageSender);
        messageKey.setSenderUserID(senderUserID);
        messageKey.setReceiverUserID(receiverUserID);
        messageKey.setGroupID(groupID);
        messageKey.setClientTime(clientTime);
        messageKey.setServerTime(serverTime);
        messageKey.setSeq(seq);
        messageKey.setRandom(random);

        return messageKey;
    }

    public void update(Message message) {
        msgID = message.msgID;
        messageType = message.messageType;
        isMessageSender = message.isMessageSender;
        senderUserID = message.senderUserID;
        receiverUserID = message.receiverUserID;
        groupID = message.groupID;
        clientTime = message.clientTime;
        serverTime = message.serverTime;
        seq = message.seq;
        random = message.random;
        messageStatus = message.messageStatus;

        for (MessageBaseElement element : messageBaseElements) {
            for (MessageBaseElement newElem : message.messageBaseElements) {
                if (element.update(newElem)) {
                    break;
                }
            }
        }
    }
}