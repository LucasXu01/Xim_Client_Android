package com.lucas.ximdemo.xim.message;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:04 下午
 */

import com.lucas.ximdemo.xim.v1.MessageKey;

import java.io.Serializable;

public class MessageListGetOption implements Serializable {
    private MessageKey messageKey;
    private boolean toOlderMessage;
    private boolean getCloudMessage;
    private int count;
    private long getTimeBegin;
    private long getTimePeriod;

    public MessageKey getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(MessageKey messageKey) {
        this.messageKey = messageKey;
    }

    public boolean isToOlderMessage() {
        return toOlderMessage;
    }

    public void setToOlderMessage(boolean toOlderMessage) {
        this.toOlderMessage = toOlderMessage;
    }

    public boolean isGetCloudMessage() {
        return getCloudMessage;
    }

    public void setGetCloudMessage(boolean getCloudMessage) {
        this.getCloudMessage = getCloudMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getGetTimeBegin() {
        return getTimeBegin;
    }

    public void setGetTimeBegin(long getTimeBegin) {
        this.getTimeBegin = getTimeBegin;
    }

    public long getGetTimePeriod() {
        return getTimePeriod;
    }

    public void setGetTimePeriod(long getTimePeriod) {
        this.getTimePeriod = getTimePeriod;
    }
}

