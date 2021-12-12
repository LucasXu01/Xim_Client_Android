package com.lucas.ximdemo.xim.conversation;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:16 下午
 */

import com.lucas.ximdemo.xim.v1.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {
    private static final int CONVERSATION_TYPE_C2C = 1;
    private static final int CONVERSATION_TYPE_GROUP = 2;

    // 会话 Key
    private ConversationKey conversationKey;
    // 会话类型
    private int conversationType;
    // c2c
    private String c2cUserID;
    private String c2cRemark;
    private String c2cNickname;
    private String c2cFaceUrl;
    private long c2cReadTimestamp;
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
    // 群消息撤回 TimeStamp，单位：微妙
    private long groupRevokeTimestamp;
    // 群消息接收选项
    private int groupMessageReceiveOption;
    // C2C 消息接收选项
    private int userMessageReceiveOption;

    // 会话排序键值
    private long orderKey;
    // 未读消息数
    private long unreadMessageCount;
    // 最后一条消息
    private Message lastMessage;
    // 群 at 信息
    private List<ConversationAtInfo> conversationAtInfoList = new ArrayList<>();
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

    public int getConversationType() {
        return conversationType;
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

    public long getC2cReadTimestamp() {
        return c2cReadTimestamp;
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

    public long getGroupRevokeTimestamp() {
        return groupRevokeTimestamp;
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

    public Message getLastMessage() {
        return lastMessage;
    }

    public List<ConversationAtInfo> getConversationAtInfoList() {
        return conversationAtInfoList;
    }

    protected void addConversationAtInfoList(ConversationAtInfo conversationAtInfo) {
        this.conversationAtInfoList.add(conversationAtInfo);
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
