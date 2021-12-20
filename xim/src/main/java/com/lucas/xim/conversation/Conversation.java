package com.lucas.xim.conversation;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:16 下午
 */

import com.lucas.xim.message.IMMsg;

import java.io.Serializable;

public class Conversation implements Serializable {
    private static final int CONVERSATION_TYPE_C2C = 1;
    private static final int CONVERSATION_TYPE_GROUP = 2;

    private String conversationId;// 会话唯一标识  CONVERSATION_TYPE_C2C即为uid  CONVERSATION_TYPE_GROUP即为groupid
    // 会话 Key
    private ConversationKey conversationKey;
    // 会话类型
    private int conversationType;
    // c2c
    private String c2cUserID;
    private String c2cRemark;
    private String c2cNickname;
    private String c2cFaceUrl;
    private long c2cReceiptTimestamp;
    // group
    private String groupID;
    private String groupName;
    private String groupType;
    private String groupFaceUrl;

    // 群最新一条消息 Sequence
    private long groupLastSequence;
    // 群消息已读 sequence
    private long groupReadSequence;
    // 群消息接收选项
    private int groupMessageReceiveOption;
    // C2C 消息接收选项
    private int userMessageReceiveOption;

    // 会话排序键值
    private long orderKey;
    // 未读消息数
    private long unreadMessageCount;
    // 最后一条消息
    private IMMsg lastMessage;

    // 置顶
    private boolean pinned = false;

    public ConversationKey getConversationKey() {
        ConversationKey conversationKey = new ConversationKey();
        conversationKey.setConversationType(conversationType);
        if (CONVERSATION_TYPE_C2C == conversationType) {
            conversationKey.setConversationID(c2cUserID);
        } else if (CONVERSATION_TYPE_GROUP == conversationType) {
            conversationKey.setConversationID(groupID);
        }
        return conversationKey;
    }

    public void setConversationType(int conversationType) {
        this.conversationType = conversationType;
    }

    public int getConversationType() {
        return conversationType;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getC2cUserID() {
        return c2cUserID;
    }

    public String getC2cRemark() {
        return c2cRemark;
    }

    public String getC2cNickname() {
        return c2cNickname;
    }

    public String getC2cFaceUrl() {
        return c2cFaceUrl;
    }

    public long getC2cReceiptTimestamp() {
        return c2cReceiptTimestamp;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public String getGroupFaceUrl() {
        return groupFaceUrl;
    }

    public long getGroupLastSequence() {
        return groupLastSequence;
    }

    public long getGroupReadSequence() {
        return groupReadSequence;
    }

    public int getGroupMessageReceiveOption() {
        return groupMessageReceiveOption;
    }

    public int getUserMessageReceiveOption() {
        return userMessageReceiveOption;
    }

    public long getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public IMMsg getLastMessage() {
        return lastMessage;
    }


    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public long getOrderKey() {
        return orderKey;
    }
}
