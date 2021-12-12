package com.lucas.ximdemo.xim.v1;

import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:16 下午
 */
public abstract class IMConversationManager {
    public IMConversationManager() {
    }

    static IMConversationManager getInstance() {
        return IMConversationManagerImpl.getInstance();
    }

    /** @deprecated */
    @Deprecated
    public abstract void setConversationListener(V2TIMConversationListener var1);

    public abstract void addConversationListener(V2TIMConversationListener var1);

    public abstract void removeConversationListener(V2TIMConversationListener var1);

    public abstract void getConversationList(long var1, int var3, V2TIMValueCallback<V2TIMConversationResult> var4);

    public abstract void getConversation(String var1, V2TIMValueCallback<V2TIMConversation> var2);

    public abstract void getConversationList(List<String> var1, V2TIMValueCallback<List<V2TIMConversation>> var2);

    public abstract void deleteConversation(String var1, V2TIMCallback var2);

    public abstract void setConversationDraft(String var1, String var2, V2TIMCallback var3);

    public abstract void pinConversation(String var1, boolean var2, V2TIMCallback var3);

    public abstract void getTotalUnreadMessageCount(V2TIMValueCallback<Long> var1);
}
