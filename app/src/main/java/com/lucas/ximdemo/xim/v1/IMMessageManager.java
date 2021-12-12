package com.lucas.ximdemo.xim.v1;

import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:12 下午
 */
public abstract class IMMessageManager {
    public IMMessageManager() {
    }

    static IMMessageManager getInstance() {
        return IMMessageManagerImpl.getInstance();
    }

    public abstract void addAdvancedMsgListener(V2TIMAdvancedMsgListener var1);

    public abstract void removeAdvancedMsgListener(V2TIMAdvancedMsgListener var1);

    public abstract V2TIMMessage createTextMessage(String var1);

    public abstract V2TIMMessage createTextAtMessage(String var1, List<String> var2);

    public abstract V2TIMMessage createCustomMessage(byte[] var1);

    public abstract V2TIMMessage createCustomMessage(byte[] var1, String var2, byte[] var3);

    public abstract V2TIMMessage createImageMessage(String var1);

    public abstract V2TIMMessage createSoundMessage(String var1, int var2);

    public abstract V2TIMMessage createVideoMessage(String var1, String var2, int var3, String var4);

    public abstract V2TIMMessage createFileMessage(String var1, String var2);

    public abstract V2TIMMessage createLocationMessage(String var1, double var2, double var4);

    public abstract V2TIMMessage createFaceMessage(int var1, byte[] var2);

    public abstract V2TIMMessage createMergerMessage(List<V2TIMMessage> var1, String var2, List<String> var3, String var4);

    public abstract V2TIMMessage createForwardMessage(V2TIMMessage var1);

    public abstract String sendMessage(IMMessage var1, String var2, String var3, int var4, boolean var5, V2TIMOfflinePushInfo var6, IMSendCallback<IMMessage> var7);

    public abstract void setC2CReceiveMessageOpt(List<String> var1, int var2, V2TIMCallback var3);

    public abstract void getC2CReceiveMessageOpt(List<String> var1, V2TIMValueCallback<List<V2TIMReceiveMessageOptInfo>> var2);

    public abstract void setGroupReceiveMessageOpt(String var1, int var2, V2TIMCallback var3);

    public abstract void getC2CHistoryMessageList(String var1, int var2, V2TIMMessage var3, V2TIMValueCallback<List<V2TIMMessage>> var4);

    public abstract void getGroupHistoryMessageList(String var1, int var2, V2TIMMessage var3, V2TIMValueCallback<List<V2TIMMessage>> var4);

    public abstract void getHistoryMessageList(V2TIMMessageListGetOption var1, V2TIMValueCallback<List<V2TIMMessage>> var2);

    public abstract void revokeMessage(V2TIMMessage var1, V2TIMCallback var2);

    public abstract void markC2CMessageAsRead(String var1, V2TIMCallback var2);

    public abstract void markGroupMessageAsRead(String var1, V2TIMCallback var2);

    public abstract void markAllMessageAsRead(V2TIMCallback var1);

    public abstract void deleteMessageFromLocalStorage(V2TIMMessage var1, V2TIMCallback var2);

    public abstract void deleteMessages(List<V2TIMMessage> var1, V2TIMCallback var2);

    public abstract void clearC2CHistoryMessage(String var1, V2TIMCallback var2);

    public abstract void clearGroupHistoryMessage(String var1, V2TIMCallback var2);

    public abstract String insertGroupMessageToLocalStorage(V2TIMMessage var1, String var2, String var3, V2TIMValueCallback<V2TIMMessage> var4);

    public abstract String insertC2CMessageToLocalStorage(V2TIMMessage var1, String var2, String var3, V2TIMValueCallback<V2TIMMessage> var4);

    public abstract void findMessages(List<String> var1, V2TIMValueCallback<List<V2TIMMessage>> var2);

    public abstract void searchLocalMessages(V2TIMMessageSearchParam var1, V2TIMValueCallback<V2TIMMessageSearchResult> var2);

}
