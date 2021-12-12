package com.lucas.ximdemo.xim.v1;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  12:01 下午
 */
public class MessageKey implements Serializable {
    private String messageID;
    private int messageType;
    private boolean isMessageSender;
    private String senderUserID;
    private String receiverUserID;
    private String groupID;
    private long clientTime;
    private long serverTime;
    private long seq;
    private long random;

    public String getMessageID() {
        return messageID;
    }

    public int getMessageType() {
        return messageType;
    }

    public boolean isMessageSender() {
        return isMessageSender;
    }

    public void setIsMessageSender(boolean isMessageSender) {
        this.isMessageSender = isMessageSender;
    }

    public String getSenderUserID() {
        return senderUserID;
    }

    public String getReceiverUserID() {
        return receiverUserID;
    }

    public String getGroupID() {
        return groupID;
    }

    public long getClientTime() {
        return clientTime;
    }

    public long getServerTime() {
        return serverTime;
    }

    public long getSeq() {
        return seq;
    }

    public long getRandom() {
        return random;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setSenderUserID(String senderUserID) {
        this.senderUserID = senderUserID;
    }

    public void setReceiverUserID(String receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setClientTime(long clientTime) {
        this.clientTime = clientTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public void setRandom(long random) {
        this.random = random;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("MessageKey--->")
                .append("messageID:").append(messageID).append(", messageType:").append(messageType)
                .append(", isMessageSender:").append(isMessageSender).append(", senderUserID:").append(senderUserID)
                .append(", receiverUserID:").append(receiverUserID).append(", groupID:").append(groupID)
                .append(", clientTime:").append(clientTime).append(", serverTime:").append(serverTime)
                .append(", seq:").append(seq).append(", random:").append(random);

        return stringBuilder.toString();
    }
