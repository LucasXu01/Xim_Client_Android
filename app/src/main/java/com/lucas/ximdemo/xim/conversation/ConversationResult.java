package com.lucas.ximdemo.xim.conversation;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:20 下午
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationResult implements Serializable {
    private long nextSeq;
    private boolean isFinish;
    private List<Conversation> conversationList = new ArrayList<>();

    public long getNextSeq() {
        return nextSeq;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public List<Conversation> getConversationList() {
        return conversationList;
    }

    // 底层使用
    protected void addConversation(Conversation conversation) {
        conversationList.add(conversation);
    }
}

