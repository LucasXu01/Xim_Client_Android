package com.lucas.ximdemo.xim.v1;


import android.content.Context;


import com.lucas.ximdemo.xim.config.IMOptions;

import java.util.List;

public abstract class IMManager {
    public static final int V2TIM_STATUS_LOGINED = 1;
    public static final int V2TIM_STATUS_LOGINING = 2;
    public static final int V2TIM_STATUS_LOGOUT = 3;
    public static final String GROUP_TYPE_WORK = "Work";
    public static final String GROUP_TYPE_PUBLIC = "Public";
    public static final String GROUP_TYPE_MEETING = "Meeting";
    public static final String GROUP_TYPE_AVCHATROOM = "AVChatRoom";
    public static final String GROUP_TYPE_COMMUNITY = "Community";

    public IMManager() {
    }

    public static IMManager getInstance() {
        return IMManagerImpl.getInstance();
    }

    public abstract boolean initSDK(Context var1, IMOptions options, int var2);

    public abstract void unInitSDK();

    public abstract void addIMSDKListener(IMSDKListener var1);

    public abstract void removeIMSDKListener(IMSDKListener var1);

    public abstract String getVersion();

    public abstract long getServerTime();

    public abstract void login(String var1, String var2, XIMCallback var3);

    public abstract void logout(XIMCallback var1);

    public abstract String getLoginUser();

    public abstract int getLoginStatus();

    public abstract void addSimpleMsgListener(IMSimpleMsgListener var1);

    public abstract void removeSimpleMsgListener(IMSimpleMsgListener var1);

    public abstract String sendC2CTextMessage(String var1, String var2, IMValueCallback<IMMessage> var3);

    public abstract String sendC2CCustomMessage(byte[] var1, String var2, IMValueCallback<IMMessage> var3);

    public abstract String sendGroupTextMessage(String var1, String var2, int var3, IMValueCallback<IMMessage> var4);

    public abstract String sendGroupCustomMessage(byte[] var1, String var2, int var3, IMValueCallback<IMMessage> var4);

    /** @deprecated */
    @Deprecated
    public abstract void setGroupListener(IMGroupListener var1);

    public abstract void addGroupListener(IMGroupListener var1);

    public abstract void removeGroupListener(IMGroupListener var1);

    public abstract void createGroup(String var1, String var2, String var3, IMValueCallback<String> var4);

    public abstract void joinGroup(String var1, String var2, XIMCallback var3);

    public abstract void quitGroup(String var1, XIMCallback var2);

    public abstract void dismissGroup(String var1, XIMCallback var2);

    public abstract void getUsersInfo(List<String> var1, IMValueCallback<List<IMUserFullInfo>> var2);

    public abstract void setSelfInfo(IMUserFullInfo var1, XIMCallback var2);

    public static IMMessageManager getMessageManager() {
        return IMMessageManagerImpl.getInstance();
    }

    public static IMGroupManager getGroupManager() {
        return IMGroupManager.getInstance();
    }

    public static IMConversationManager getConversationManager() {
        return IMConversationManagerImpl.getInstance();
    }

    public static IMFriendshipManager getFriendshipManager() {
        return IMFriendshipManagerImpl.getInstance();
    }

    public static IMSignalingManager getSignalingManager() {
        return IMSignalingManager.getInstance();
    }

    public abstract void callExperimentalAPI(String var1, Object var2, IMValueCallback<Object> var3);
}
