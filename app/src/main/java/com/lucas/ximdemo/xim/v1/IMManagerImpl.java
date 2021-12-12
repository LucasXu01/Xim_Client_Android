package com.lucas.ximdemo.xim.v1;

import android.content.Context;
import android.text.TextUtils;

import com.lucas.ximdemo.xim.BaseConstants;
import com.lucas.ximdemo.xim.common.IMCallback;
import com.lucas.ximdemo.xim.common.SystemUtil;
import com.lucas.ximdemo.xim.config.IMOptions;
import com.lucas.ximdemo.xim.group.GroupManager;
import com.lucas.ximdemo.xim.manager.BaseManager;
import com.lucas.ximdemo.xim.manager.CustomServerInfo;
import com.lucas.ximdemo.xim.manager.SDKListener;
import com.lucas.ximdemo.xim.message.MessageCenter;
import com.lucas.ximdemo.xim.relationship.RelationshipManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  6:13 下午
 */
public class IMManagerImpl extends IMManager {
    private static final String TAG = IMManagerImpl.class.getSimpleName();
    private Object mLockObject = new Object();
    private MessageListener mMessageInternalListener;
    private List<IMSimpleMsgListener> mIMSimpleMsgListenerList;
    private GroupListener mGroupInternalListener;
    private IMSDKListener mIMSDKListener;
    private IMGroupListener mIMGroupListener;
    private final List<IMSDKListener> mIMSDKListenerList = new ArrayList<>();
    private final List<IMGroupListener> mGroupListenerList = new ArrayList<>();

    private static class IMManagerImplHolder {
        private static final IMManagerImpl v2TIMManagerImpl = new IMManagerImpl();
    }

    public static IMManagerImpl getInstance() {
        return IMManagerImpl.IMManagerImplHolder.v2TIMManagerImpl;
    }

    protected IMManagerImpl() {
        mIMSimpleMsgListenerList = new CopyOnWriteArrayList<>();
        initMessageListener();
        initGroupListener();
        IMMessageManagerImpl.getInstance().initListener();
    }

    @Override
    public boolean initSDK(Context context, IMOptions options, int sdkAppID) {
        return BaseManager.getInstance().initSDK(context, options, new SDKListener() {
            @Override
            public void onConnecting() {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onConnecting();
                        }
                    }
                });
            }

            @Override
            public void onConnectSuccess() {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onConnectSuccess();
                        }
                    }
                });
            }

            @Override
            public void onConnectFailed(final int code, final String error) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onConnectFailed(code, error);
                        }
                    }
                });
            }

            @Override
            public void onKickedOffline() {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onKickedOffline();
                        }
                    }
                });
            }

            @Override
            public void onUserSigExpired() {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            iMSDKListener.onUserSigExpired();
                        }
                    }
                });
            }

            @Override
            public void onSelfInfoUpdated(final UserInfo info) {
                IMContext.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        for (IMSDKListener iMSDKListener : mIMSDKListenerList) {
                            IMUserFullInfo v2UserInfo = new IMUserFullInfo();
                            v2UserInfo.setUserInfo(info);
                            iMSDKListener.onSelfInfoUpdated(v2UserInfo);
                        }
                    }
                });
            }

        });
    }


    @Override
    public void unInitSDK() {
        BaseManager.getInstance().unInitSDK();
    }


    @Override
    public void addIMSDKListener(final IMSDKListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mIMSDKListenerList.contains(listener)) {
                    return;
                }
                mIMSDKListenerList.add(listener);
            }
        });
    }

    @Override
    public void removeIMSDKListener(final IMSDKListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mIMSDKListenerList.remove(listener);
            }
        });
    }

    @Override
    public String getVersion() {
        return BaseManager.getInstance().getVersion();
    }

    @Override
    public long getServerTime() {
        return BaseManager.getInstance().getServerTime();
    }

    @Override
    public void login(final String userID, final String userSig, final XIMCallback callback) {
        if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(userSig)) {
            callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID or userSig is empty");
            return;
        }

        XIMCallback v2TIMCallback = new XIMCallback() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        BaseManager.getInstance().login(userID, userSig, new IMCallback(v2TIMCallback) {
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
    public void logout(final XIMCallback callback) {
        BaseManager.getInstance().logout(new IMCallback(callback) {
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
    public String getLoginUser() {
        return BaseManager.getInstance().getLoginUser();
    }

    @Override
    public int getLoginStatus() {
        return BaseManager.getInstance().getLoginStatus();
    }

    @Override
    public void addSimpleMsgListener(IMSimpleMsgListener v2TIMSimpleMsgListener) {
        if (v2TIMSimpleMsgListener == null) {
            return;
        }
        synchronized (mLockObject) {
            if (mIMSimpleMsgListenerList.contains(v2TIMSimpleMsgListener)) {
                return;
            }
            this.mIMSimpleMsgListenerList.add(v2TIMSimpleMsgListener);
        }
    }

    @Override
    public void removeSimpleMsgListener(IMSimpleMsgListener v2TIMSimpleMsgListener) {
        if (v2TIMSimpleMsgListener == null) {
            return;
        }
        synchronized (mLockObject) {
            mIMSimpleMsgListenerList.remove(v2TIMSimpleMsgListener);
        }
    }

    @Override
    public String sendC2CTextMessage(String text, String userID, final IMValueCallback<IMMessage> callback) {
        if (text == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "text is null");
            }
            return null;
        }
        if (TextUtils.isEmpty(userID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID is empty");
            }
            return null;
        }
        IMMessage v2TIMMessage = IMMessageManagerImpl.getInstance().createTextMessage(text);

        IMSendCallback<IMMessage> sendCallback = new IMSendCallback<IMMessage>() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(IMMessage v2TIMMessage) {
                if (callback != null) {
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

        IMMessageManager.getInstance().sendMessage(
                v2TIMMessage,
                userID,
                null,
                IMMessage.V2TIM_PRIORITY_NORMAL,
                false,
                new V2TIMOfflinePushInfo(),
                sendCallback);
        return v2TIMMessage.getMsgID();
    }

    @Override
    public String sendC2CCustomMessage(byte[] customData, String userID, final IMValueCallback<IMMessage> callback) {
        if (customData == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "customData is null");
            }
            return null;
        }
        if (TextUtils.isEmpty(userID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userID is empty");
            }
            return null;
        }
        IMMessage v2TIMMessage = IMMessageManagerImpl.getInstance().createCustomMessage(customData);

        IMSendCallback<IMMessage> sendCallback = new IMSendCallback<IMMessage>() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(IMMessage v2TIMMessage) {
                if (callback != null) {
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

        IMMessageManager.getInstance().sendMessage(
                v2TIMMessage,
                userID,
                null,
                IMMessage.V2TIM_PRIORITY_NORMAL,
                false,
                new V2TIMOfflinePushInfo(),
                sendCallback);

        return v2TIMMessage.getMsgID();
    }

    @Override
    public String sendGroupTextMessage(String text, String groupID, int priority, final IMValueCallback<IMMessage> callback) {
        if (text == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "text is null");
            }
            return null;
        }
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty");
            }
            return "";
        }

        IMMessage v2TIMMessage = IMMessageManagerImpl.getInstance().createTextMessage(text);
        int priorityInternal = IMMessage.V2TIM_PRIORITY_NORMAL;
        if (priority == IMMessage.V2TIM_PRIORITY_DEFAULT) {
            priorityInternal =  IMMessage.V2TIM_PRIORITY_NORMAL;
        } else if (priority == IMMessage.V2TIM_PRIORITY_HIGH || priority == IMMessage.V2TIM_PRIORITY_LOW) {
            priorityInternal = priority;
        }

        IMSendCallback<IMMessage> sendCallback = new IMSendCallback<IMMessage>() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(IMMessage v2TIMMessage) {
                if (callback != null) {
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

        IMMessageManager.getInstance().sendMessage(
                v2TIMMessage,
                null,
                groupID,
                priorityInternal,
                false,
                new V2TIMOfflinePushInfo(),
                sendCallback);

        return v2TIMMessage.getMsgID();
    }

    @Override
    public String sendGroupCustomMessage(byte[] customData, String groupID, int priority, final IMValueCallback<IMMessage> callback) {
        if (customData == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "customData is null");
            }
            return null;
        }
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupID is empty");
            }
            return "";
        }

        IMMessage v2TIMMessage = IMMessageManagerImpl.getInstance().createCustomMessage(customData);
        int priorityInternal = IMMessage.V2TIM_PRIORITY_NORMAL;
        if (priority == IMMessage.V2TIM_PRIORITY_DEFAULT) {
            priorityInternal =  IMMessage.V2TIM_PRIORITY_NORMAL;
        } else if (priority == IMMessage.V2TIM_PRIORITY_HIGH || priority == IMMessage.V2TIM_PRIORITY_LOW) {
            priorityInternal = priority;
        }

        IMSendCallback<IMMessage> sendCallback = new IMSendCallback<IMMessage>() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(IMMessage v2TIMMessage) {
                if (callback != null) {
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

        IMMessageManager.getInstance().sendMessage(
                v2TIMMessage,
                null,
                groupID,
                priorityInternal,
                false,
                new V2TIMOfflinePushInfo(),
                sendCallback);

        return v2TIMMessage.getMsgID();
    }

    @Override
    public void setGroupListener(final IMGroupListener listener) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mIMGroupListener != null) {
                    mGroupListenerList.remove(mIMGroupListener);
                }
                if (listener != null) {
                    mGroupListenerList.add(listener);
                }
                mIMGroupListener = listener;
            }
        });
    }

    @Override
    public void addGroupListener(final IMGroupListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mGroupListenerList.contains(listener)) {
                    return;
                }
                mGroupListenerList.add(listener);
            }
        });
    }

    @Override
    public void removeGroupListener(final IMGroupListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mGroupListenerList.remove(listener);
            }
        });
    }

    @Override
    public void createGroup(String groupType, String groupID, String groupName, final IMValueCallback<String> callback) {
        if (TextUtils.isEmpty(groupType)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupType is empty");
            }
            return;
        }
        if (TextUtils.isEmpty(groupName)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupName is empty");
            }
            return;
        }
        // 和老版本的群类型做个映射，针对大小写做下兼容
        if (groupType.equalsIgnoreCase(IMManager.GROUP_TYPE_WORK)) {
            groupType = "Private";
        } else if (groupType.equalsIgnoreCase(IMManager.GROUP_TYPE_MEETING)) {
            groupType = "ChatRoom";
        } else if (groupType.equalsIgnoreCase("Private")) {
            groupType = "Private";
        } else if (groupType.equalsIgnoreCase("ChatRoom")) {
            groupType = "ChatRoom";
        } else if (groupType.equalsIgnoreCase(IMManager.GROUP_TYPE_PUBLIC)) {
            groupType = IMManager.GROUP_TYPE_PUBLIC;
        } else if (groupType.equalsIgnoreCase(IMManager.GROUP_TYPE_AVCHATROOM)) {
            groupType = IMManager.GROUP_TYPE_AVCHATROOM;
        }

        GroupManager.getInstance().createGroup(groupType, groupID, groupName, new IMCallback<String>(callback) {
            @Override
            public void success(String data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void joinGroup(String groupID, String message, final XIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid groupID");
            }
            return;
        }

        GroupManager.getInstance().joinGroup(groupID, message, new IMCallback(callback) {
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
    public void quitGroup(String groupID, final XIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid groupID");
            }
            return;
        }

        GroupManager.getInstance().quitGroup(groupID, new IMCallback(callback) {
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
    public void dismissGroup(String groupID, final XIMCallback callback) {
        if (TextUtils.isEmpty(groupID)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid groupID");
            }
            return;
        }

        GroupManager.getInstance().dismissGroup(groupID, new IMCallback(callback) {
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
    public void getUsersInfo(List<String> userIDList, final IMValueCallback<List<IMUserFullInfo>> callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid userIDList");
            }
            return;
        }

        IMValueCallback<List<UserInfo>> v2Callback = new IMValueCallback<List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> userInfos) {
                if (callback != null) {
                    List<IMUserFullInfo> v2UserInfoList = new ArrayList<>();
                    for (UserInfo item : userInfos) {
                        IMUserFullInfo v2UserInfo = new IMUserFullInfo();
                        v2UserInfo.setUserInfo(item);
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

        RelationshipManager.getInstance().getUsersInfo(userIDList, new IMCallback<List<UserInfo>>(v2Callback) {
            @Override
            public void success(List<UserInfo> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void setSelfInfo(IMUserFullInfo v2TIMUserFullInfo, final XIMCallback callback) {
        if (v2TIMUserFullInfo == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "invalid params");
            }
            return;
        }

        HashMap<String, Object> modifyParams = v2TIMUserFullInfo.getModifyParams();
        if (modifyParams == null || modifyParams.isEmpty()) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "no changed info");
            }
            return;
        }

        RelationshipManager.getInstance().setSelfInfo(v2TIMUserFullInfo.getModifyParams(), new IMCallback(callback) {
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
    public void callExperimentalAPI(String api, Object param, IMValueCallback<Object> callback) {
        if (TextUtils.isEmpty(api)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "invalid api");
            return;
        }
        if (api.equals("setCustomServerInfo")) {
            setCustomServerInfo(param, callback);
        } else if (api.equals("setLibraryPath")) {
            setLibraryPath(param, callback);
        } else if (api.equals("initLocalStorage")) {
            initLocalStorage(param, callback);
        } else if (api.equals("setTestEnvironment")) {
            setTestEnvironment(param, callback);
        } else if (api.equals("setCosSaveRegionForConversation")) {
            setCosSaveRegion(param, callback);
        } else if (api.equals("setBuildInfo")) {
            setBuildInfo(param, callback);
        } else {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "unsupported api");
        }
    }

    private void setBuildInfo(Object param, IMValueCallback<Object> callback) {
        if (null == param || !(param instanceof String)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is not string");
            return;
        }

        String json = (String) param;
        if (TextUtils.isEmpty(json)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is empty");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            String buildBrand = jsonObject.optString("buildBrand");
            String buildManufacturer = jsonObject.optString("buildManufacturer");
            String buildModel = jsonObject.optString("buildModel");
            String buildVersionRelease = jsonObject.optString("buildVersionRelease");
            int buildVersionSDKInt = jsonObject.optInt("buildVersionSDKInt");

            SystemUtil.setBuildBrand(buildBrand);
            SystemUtil.setBuildManufacturer(buildManufacturer);
            SystemUtil.setBuildModel(buildModel);
            SystemUtil.setBuildVersionRelease(buildVersionRelease);
            SystemUtil.setBuildVersionSDKInt(buildVersionSDKInt);

            callbackOnSuccess(callback, null);
        } catch (JSONException e) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "convert param to json failed");
            e.printStackTrace();
        }
    }

    private void setCosSaveRegion(Object param, IMValueCallback<Object> callback) {
        if (null == param || !(param instanceof String)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is not string");
            return;
        }

        String json = (String) param;
        if (TextUtils.isEmpty(json)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is empty");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            String conversationID = jsonObject.optString("conversationID");
            String cosSaveRegion = jsonObject.optString("cosSaveRegion");
            if (TextUtils.isEmpty(conversationID) || TextUtils.isEmpty(cosSaveRegion)) {
                callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "invalid param");
                return;
            }

            ConversationKey conversationKey = V2TIMConversationManagerImpl.getInstance().getConversationKey(conversationID);
            ConversationManager.getInstance().setCosSaveRegionForConversation(conversationKey, cosSaveRegion, new XIMCallback(callback) {
                @Override
                public void success(Object data) {
                    super.success(data);
                }

                @Override
                public void fail(int code, String errorMessage) {
                    super.fail(code, errorMessage);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callbackOnError(IMValueCallback<Object> callback, int code, String desc) {
        if (callback != null) {
            callback.onError(code, desc);
        }
    }

    private void callbackOnSuccess(IMValueCallback<Object> callback, Object result) {
        if (callback != null) {
            callback.onSuccess(result);
        }
    }

    private void setLibraryPath(Object param, IMValueCallback<Object> callback) {
        if (!(param instanceof String)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is not string");
            return;
        }
        String path = (String) param;
        boolean isSuccess = BaseManager.getInstance().setLibraryPath(path);
        if (isSuccess) {
            callbackOnSuccess(callback, null);
        } else {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "system load so library failed");
        }
    }

    private void initLocalStorage(Object param, IMValueCallback<Object> callback) {
        if (!(param instanceof String)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is not string");
            return;
        }

        String userID = (String) param;
        if (TextUtils.isEmpty(userID)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "invalid userID");
            return;
        }

        BaseManager.getInstance().initLocalStorage(userID, new IMCallback(callback) {
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

    private void setCustomServerInfo(Object param, IMValueCallback<Object> callback) {
        if (null == param || !(param instanceof String)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is not string");
            return;
        }

        String json = (String) param;
        if (TextUtils.isEmpty(json)) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "param is empty");
            return;
        }

        CustomServerInfo customServerInfo = new CustomServerInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray longconnectionAddressList = jsonObject.optJSONArray("longconnectionAddressList");
            if (longconnectionAddressList != null && longconnectionAddressList.length() > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < longconnectionAddressList.length(); i++) {
                    JSONObject address = longconnectionAddressList.getJSONObject(i);
                    CustomServerInfo.ServerAddress serverAddress = new CustomServerInfo.ServerAddress();
                    serverAddress.ip = address.optString("ip");
                    serverAddress.port = address.optInt("port");
                    serverAddress.isIPv6 = (address.has("isIPv6") ? address.optBoolean("isIPv6") : false);

                    list.add(serverAddress);
                }
                customServerInfo.longconnectionAddressList = list;
            }

            JSONArray shortconnectionAddressList = jsonObject.optJSONArray("shortconnectionAddressList");
            if (shortconnectionAddressList != null && shortconnectionAddressList.length() > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < shortconnectionAddressList.length(); i++) {
                    JSONObject address = shortconnectionAddressList.getJSONObject(i);
                    CustomServerInfo.ServerAddress serverAddress = new CustomServerInfo.ServerAddress();
                    serverAddress.ip = address.optString("ip");
                    serverAddress.port = address.optInt("port");
                    serverAddress.isIPv6 = (address.has("isIPv6") ? address.optBoolean("isIPv6") : false);

                    list.add(serverAddress);
                }
                customServerInfo.shortconnectionAddressList = list;
            }

            customServerInfo.serverPublicKey = jsonObject.optString("serverPublicKey");

            BaseManager.getInstance().setCustomServerInfo(customServerInfo);

            callbackOnSuccess(callback, null);
        } catch (JSONException e) {
            callbackOnError(callback, BaseConstants.ERR_INVALID_PARAMETERS, "convert param to json failed");
            e.printStackTrace();
        }
    }

    private void setTestEnvironment(Object param, IMValueCallback<Object> callback) {
        if (param != null && param instanceof Boolean) {
            BaseManager.getInstance().setTestEnvironment(((Boolean)param).booleanValue());
        }

        callbackOnSuccess(callback, null);
    }

    private void initMessageListener() {
        mMessageInternalListener = new MessageListener() {
            @Override
            public void onReceiveNewMessage(List<Message> messageList) {
                if (messageList == null || messageList.isEmpty()) {
                    return;
                }
                Message message = messageList.get(0);
                int messageType = message.getMessageType();
                IMMessage v2TIMMessage = new IMMessage();
                v2TIMMessage.setMessage(message);

                int elementType = v2TIMMessage.getElemType();
                IMUserInfo v2TIMUserInfo = null;
                IMGroupMemberInfo v2TIMGroupMemberInfo = null;
                if (messageType == Message.MESSAGE_TYPE_C2C) {
                    v2TIMUserInfo = new IMUserInfo();
                    v2TIMUserInfo.setUserID(v2TIMMessage.getSender());
                    v2TIMUserInfo.setNickName(v2TIMMessage.getNickName());
                    v2TIMUserInfo.setFaceUrl(v2TIMMessage.getFaceUrl());
                } else if (messageType == Message.MESSAGE_TYPE_GROUP) {
                    v2TIMGroupMemberInfo = new IMGroupMemberInfo();
                    GroupMemberInfo groupMemberInfo = new GroupMemberInfo();
                    groupMemberInfo.setGroupID(message.getGroupID());
                    groupMemberInfo.setUserID(message.getSenderUserID());
                    groupMemberInfo.setNickname(message.getNickName());
                    groupMemberInfo.setNameCard(message.getNameCard());
                    groupMemberInfo.setFaceUrl(message.getFaceUrl());
                    groupMemberInfo.setFriendRemark(message.getFriendRemark());
                    v2TIMGroupMemberInfo.setGroupMemberInfo(groupMemberInfo);
                }
                if (elementType == IMMessage.V2TIM_ELEM_TYPE_TEXT) {
                    V2TIMTextElem v2TIMTextElem = v2TIMMessage.getTextElem();
                    synchronized (mLockObject) {
                        for (IMSimpleMsgListener v2TIMSimpleMsgListener : mIMSimpleMsgListenerList) {
                            if (messageType == Message.MESSAGE_TYPE_C2C) {
                                v2TIMSimpleMsgListener.onRecvC2CTextMessage(v2TIMMessage.getMsgID(), v2TIMUserInfo, v2TIMTextElem.getText());
                            } else if (messageType == Message.MESSAGE_TYPE_GROUP) {
                                v2TIMSimpleMsgListener.onRecvGroupTextMessage(
                                        v2TIMMessage.getMsgID(), message.getGroupID(), v2TIMGroupMemberInfo, v2TIMTextElem.getText());
                            }
                        }
                    }
                } else if (elementType == IMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
                    V2TIMCustomElem v2TIMCustomElem = v2TIMMessage.getCustomElem();
                    synchronized (mLockObject) {
                        for (IMSimpleMsgListener v2TIMSimpleMsgListener : mIMSimpleMsgListenerList) {
                            if (messageType == Message.MESSAGE_TYPE_C2C) {
                                v2TIMSimpleMsgListener.onRecvC2CCustomMessage(v2TIMMessage.getMsgID(), v2TIMUserInfo, v2TIMCustomElem.getData());
                            } else if (messageType == Message.MESSAGE_TYPE_GROUP) {
                                v2TIMSimpleMsgListener.onRecvGroupCustomMessage(
                                        v2TIMMessage.getMsgID(), v2TIMMessage.getGroupID(), v2TIMGroupMemberInfo, v2TIMCustomElem.getData());
                            }
                        }
                    }
                }
            }

            @Override
            public void onReceiveMessageReceipt(List<MessageReceipt> receiptList) {
            }

            @Override
            public void onReceiveMessageRevoked(List<MessageKey> keyList) {
            }

            @Override
            public void onReceiveMessageModified(List<Message> messageList) {
            }
        };
        MessageCenter.getInstance().addMessageListener(mMessageInternalListener);
    }

    private void initGroupListener() {
        mGroupInternalListener = new GroupListener() {
            IMGroupMemberInfo convertToV2GroupMemberInfo(GroupMemberInfo memberInfo) {
                IMGroupMemberInfo v2MemberInfo = new IMGroupMemberInfo();
                if (memberInfo != null) {
                    v2MemberInfo.setGroupMemberInfo(memberInfo);
                }

                return v2MemberInfo;
            }

            List<IMGroupMemberInfo> convertToV2GroupMemberInfoList(List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> v2MemberList = new ArrayList<>();
                for (GroupMemberInfo item : memberList) {
                    v2MemberList.add(convertToV2GroupMemberInfo(item));
                }

                return v2MemberList;
            }

            @Override
            public void onMemberEnter(final String groupID, final List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> groupMemberInfoList = convertToV2GroupMemberInfoList(memberList);
                List<IMGroupMemberInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onMemberEnter(groupID, unmodifiableList);
                }
            }

            @Override
            public void onMemberLeave(final String groupID, final GroupMemberInfo member) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onMemberLeave(groupID, convertToV2GroupMemberInfo(member));
                }
            }

            @Override
            public void onMemberInvited(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> groupMemberInfoList = convertToV2GroupMemberInfoList(memberList);
                List<IMGroupMemberInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onMemberInvited(groupID, convertToV2GroupMemberInfo(opUser), unmodifiableList);
                }
            }

            @Override
            public void onMemberKicked(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> groupMemberInfoList = convertToV2GroupMemberInfoList(memberList);
                List<IMGroupMemberInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onMemberKicked(groupID, convertToV2GroupMemberInfo(opUser), unmodifiableList);
                }
            }

            @Override
            public void onMemberInfoChanged(final String groupID, final List<GroupMemberInfoChangeItem> groupMemberChangeInfoList) {
                List<IMGroupMemberChangeInfo> v2GroupMemberChangeInfoList = new ArrayList<>();
                for (GroupMemberInfoChangeItem item : groupMemberChangeInfoList) {
                    IMGroupMemberChangeInfo v2GroupMemberChangeInfo = new IMGroupMemberChangeInfo();
                    v2GroupMemberChangeInfo.setGroupMemberInfoChangeItem(item);
                    v2GroupMemberChangeInfoList.add(v2GroupMemberChangeInfo);
                }
                List<IMGroupMemberChangeInfo> unmodifiableList = Collections.unmodifiableList(v2GroupMemberChangeInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onMemberInfoChanged(groupID, unmodifiableList);
                }
            }

            @Override
            public void onGroupCreated(final String groupID) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGroupCreated(groupID);
                }
            }

            @Override
            public void onGroupDismissed(final String groupID, final GroupMemberInfo opUser) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGroupDismissed(groupID, convertToV2GroupMemberInfo(opUser));
                }
            }

            @Override
            public void onGroupRecycled(final String groupID, final GroupMemberInfo opUser) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGroupRecycled(groupID, convertToV2GroupMemberInfo(opUser));
                }
            }

            @Override
            public void onGroupInfoChanged(final String groupID, final List<GroupInfoChangeItem> changeInfos) {
                List<IMGroupChangeInfo> v2GroupChangeInfoList = new ArrayList<>();
                for (GroupInfoChangeItem item : changeInfos) {
                    IMGroupChangeInfo v2GroupChangeInfo = new IMGroupChangeInfo();
                    v2GroupChangeInfo.setGroupInfoChangeItem(item);
                    v2GroupChangeInfoList.add(v2GroupChangeInfo);
                }
                List<IMGroupChangeInfo> unmodifiableList = Collections.unmodifiableList(v2GroupChangeInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGroupInfoChanged(groupID, unmodifiableList);
                }
            }

            @Override
            public void onReceiveJoinApplication(final String groupID, final GroupMemberInfo member, final String opReason) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onReceiveJoinApplication(groupID, convertToV2GroupMemberInfo(member), opReason);
                }
            }

            @Override
            public void onApplicationProcessed(final String groupID, final GroupMemberInfo opUser, final boolean isAgreeJoin, final String opReason) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onApplicationProcessed(groupID, convertToV2GroupMemberInfo(opUser), isAgreeJoin, opReason);
                }
            }

            @Override
            public void onGrantAdministrator(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> groupMemberInfoList = convertToV2GroupMemberInfoList(memberList);
                List<IMGroupMemberInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGrantAdministrator(groupID, convertToV2GroupMemberInfo(opUser), unmodifiableList);
                }
            }

            @Override
            public void onRevokeAdministrator(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                List<IMGroupMemberInfo> groupMemberInfoList = convertToV2GroupMemberInfoList(memberList);
                List<IMGroupMemberInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onRevokeAdministrator(groupID, convertToV2GroupMemberInfo(opUser), unmodifiableList);
                }
            }

            @Override
            public void onQuitFromGroup(final String groupID) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onQuitFromGroup(groupID);
                }
            }

            @Override
            public void onReceiveRESTCustomData(final String groupID, final byte[] customData) {
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onReceiveRESTCustomData(groupID, customData.clone());
                }
            }

            @Override
            public void onGroupAttributeChanged(final String groupID, final Map<String, String> groupAttributeMap) {
                Map<String, String> unmodifiableMap = Collections.unmodifiableMap(groupAttributeMap);
                for (IMGroupListener groupListener : mGroupListenerList) {
                    groupListener.onGroupAttributeChanged(groupID, unmodifiableMap);
                }
            }
        };
        GroupManager.getInstance().setGroupListener(mGroupInternalListener);
    }
}

