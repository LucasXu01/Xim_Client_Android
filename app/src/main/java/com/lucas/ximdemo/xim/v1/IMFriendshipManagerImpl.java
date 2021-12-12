package com.lucas.ximdemo.xim.v1;

import android.text.TextUtils;

import com.lucas.ximdemo.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:18 下午
 */
public class IMFriendshipManagerImpl extends IMFriendshipManager {
    private final String TAG = "V2TIMFriendshipManagerImpl";

    private static class V2TIMFriendshipManagerImplHolder {
        private static final IMFriendshipManagerImpl v2TIMFriendshipManagerImpl = new IMFriendshipManagerImpl();
    }

    static IMFriendshipManagerImpl getInstance() {
        return V2TIMFriendshipManagerImplHolder.v2TIMFriendshipManagerImpl;
    }

    private FriendshipListener mFriendshipInternalListener;
    private V2TIMFriendshipListener mV2TIMFriendshipListener;
    private final List<V2TIMFriendshipListener> mFriendshipListenerList = new ArrayList<>();

    private IMFriendshipManagerImpl() {
        initFriendshipListener();
    }

    @Override
    public void setFriendListener(final V2TIMFriendshipListener v2TIMFriendshipListener) {
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mV2TIMFriendshipListener != null) {
                    mFriendshipListenerList.remove(mV2TIMFriendshipListener);
                }
                if (v2TIMFriendshipListener != null) {
                    mFriendshipListenerList.add(v2TIMFriendshipListener);
                }
                mV2TIMFriendshipListener = v2TIMFriendshipListener;
            }
        });
    }

    @Override
    public void addFriendListener(final V2TIMFriendshipListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mFriendshipListenerList.contains(listener)) {
                    return;
                }
                mFriendshipListenerList.add(listener);
            }
        });
    }

    @Override
    public void removeFriendListener(final V2TIMFriendshipListener listener) {
        if (listener == null) {
            return;
        }
        IMContext.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mFriendshipListenerList.remove(listener);
            }
        });
    }

    @Override
    public void getFriendList(final V2TIMValueCallback<List<V2TIMFriendInfo>> callback) {
        V2TIMValueCallback<List<FriendInfo>> v2Callback = new V2TIMValueCallback<List<FriendInfo>>() {
            @Override
            public void onSuccess(List<FriendInfo> friendInfoList) {
                if (callback != null) {
                    List<V2TIMFriendInfo> v2TIMFriendInfoList = new ArrayList<>();
                    for (FriendInfo friendInfo : friendInfoList) {
                        V2TIMFriendInfo v2TIMFriendInfo = new V2TIMFriendInfo();
                        v2TIMFriendInfo.setFriendInfo(friendInfo);
                        v2TIMFriendInfoList.add(v2TIMFriendInfo);
                    }
                    callback.onSuccess(v2TIMFriendInfoList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getFriendList(new XIMCallback<List<FriendInfo>>(v2Callback) {
            @Override
            public void success(List<FriendInfo> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        BaseManager.getInstance().checkTUIComponent(BaseManager.TUI_COMPONENT_CONTACT);
    }

    @Override
    public void getFriendsInfo(List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendInfoResult>> callback) {
        if (userIDList == null || userIDList.isEmpty()) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList is empty");
            }
            return;
        }
        V2TIMValueCallback<List<FriendInfoResult>> v2Callback = new V2TIMValueCallback<List<FriendInfoResult>>() {
            @Override
            public void onSuccess(List<FriendInfoResult> friendInfoResultList) {
                if (callback != null) {
                    List<V2TIMFriendInfoResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendInfoResult friendInfoResult : friendInfoResultList) {
                        V2TIMFriendInfoResult v2TIMFriendInfoResult = new V2TIMFriendInfoResult();
                        v2TIMFriendInfoResult.setFriendInfoResult(friendInfoResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendInfoResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getFriendsInfo(userIDList, new XIMCallback<List<FriendInfoResult>>(v2Callback) {
            @Override
            public void success(List<FriendInfoResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void setFriendInfo(V2TIMFriendInfo info, final V2TIMCallback callback) {
        if (info == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "info is null");
            }
            return;
        }
        RelationshipManager.getInstance().setFriendInfo(info.getUserID(), info.getModifyFriendInfo(), new XIMCallback(callback) {
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
    public void searchFriends(V2TIMFriendSearchParam searchParam, final V2TIMValueCallback<List<V2TIMFriendInfoResult>> callback) {
        if (searchParam == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "searchParam is null");
            }
            return;
        }
        if (searchParam.getKeywordList() == null || searchParam.getKeywordList().size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "keywordList is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendInfoResult>> v2Callback = new V2TIMValueCallback<List<FriendInfoResult>>() {
            @Override
            public void onSuccess(List<FriendInfoResult> friendInfoResultList) {
                if (callback != null) {
                    List<V2TIMFriendInfoResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendInfoResult friendInfoResult : friendInfoResultList) {
                        V2TIMFriendInfoResult v2TIMFriendInfoResult = new V2TIMFriendInfoResult();
                        v2TIMFriendInfoResult.setFriendInfoResult(friendInfoResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendInfoResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };
        RelationshipManager.getInstance().searchFriends(searchParam.getFriendSearchParam(), new XIMCallback<List<FriendInfoResult>>(v2Callback) {
            @Override
            public void success(List<FriendInfoResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });

        BaseManager.getInstance().checkTUIComponent(BaseManager.TUI_COMPONENT_SEARCH);
    }

    @Override
    public void addFriend(V2TIMFriendAddApplication application, final V2TIMValueCallback<V2TIMFriendOperationResult> callback) {
        if (application == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "v2TIMFriendApplication is null");
            }
            return;
        }

        V2TIMValueCallback<FriendOperationResult> v2TIMValueCallback = new V2TIMValueCallback<FriendOperationResult>() {
            @Override
            public void onSuccess(FriendOperationResult friendOperationResult) {
                if (callback != null) {
                    V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                    v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                    callback.onSuccess(v2TIMFriendOperationResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().addFriend(application.getFriendAddApplication(), new XIMCallback<FriendOperationResult>(v2TIMValueCallback) {
            @Override
            public void success(FriendOperationResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteFromFriendList(List<String> userIDList, int deleteType, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList maybe empty");
            }
            return;
        }

        if (deleteType != V2TIMFriendInfo.V2TIM_FRIEND_TYPE_SINGLE && deleteType != V2TIMFriendInfo.V2TIM_FRIEND_TYPE_BOTH) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "deleteType is invalid : " + deleteType);
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResults) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResults) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().deleteFromFriendList(userIDList, deleteType, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void checkFriend(List<String> userIDList, int checkType, final V2TIMValueCallback<List<V2TIMFriendCheckResult>> callback){
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList maybe empty");
            }
            return;
        }
        if (checkType != V2TIMFriendInfo.V2TIM_FRIEND_TYPE_SINGLE && checkType != V2TIMFriendInfo.V2TIM_FRIEND_TYPE_BOTH) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "checkType is invalid : " + checkType);
            }
            return;
        }

        V2TIMValueCallback<List<FriendCheckResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendCheckResult>>() {
            @Override
            public void onSuccess(List<FriendCheckResult> friendCheckResults) {
                if (callback != null) {
                    List<V2TIMFriendCheckResult> v2FriendCheckResultList = new ArrayList<>();
                    for (FriendCheckResult friendCheckResult : friendCheckResults) {
                        V2TIMFriendCheckResult v2TIMFriendCheckResult = new V2TIMFriendCheckResult();
                        v2TIMFriendCheckResult.setFriendCheckResult(friendCheckResult);
                        v2FriendCheckResultList.add(v2TIMFriendCheckResult);
                    }

                    callback.onSuccess(v2FriendCheckResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().checkFriend(userIDList, checkType, new XIMCallback<List<FriendCheckResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendCheckResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getFriendApplicationList(final V2TIMValueCallback<V2TIMFriendApplicationResult> callback) {

        V2TIMValueCallback<FriendApplicationResult> v2TIMValueCallback = new V2TIMValueCallback<FriendApplicationResult>() {
            @Override
            public void onSuccess(FriendApplicationResult friendApplicationResult) {
                if (callback != null) {
                    V2TIMFriendApplicationResult v2TIMFriendApplicationResult = new V2TIMFriendApplicationResult();
                    v2TIMFriendApplicationResult.setFriendApplicationResult(friendApplicationResult);
                    callback.onSuccess(v2TIMFriendApplicationResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getFriendApplicationList(new XIMCallback<FriendApplicationResult>(v2TIMValueCallback) {
            @Override
            public void success(FriendApplicationResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void acceptFriendApplication(V2TIMFriendApplication application, int responseType, final V2TIMValueCallback<V2TIMFriendOperationResult> callback) {
        if (responseType != V2TIMFriendApplication.V2TIM_FRIEND_ACCEPT_AGREE && responseType != V2TIMFriendApplication.V2TIM_FRIEND_ACCEPT_AGREE_AND_ADD) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "responseType is invalid : " + responseType);
            }
            return;
        }
        if (application == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "v2TIMFriendApplication is null");
            }
            return;
        }

        V2TIMValueCallback<FriendOperationResult> v2TIMValueCallback = new V2TIMValueCallback<FriendOperationResult>() {
            @Override
            public void onSuccess(FriendOperationResult friendOperationResult) {
                if (callback != null) {
                    V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                    v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                    callback.onSuccess(v2TIMFriendOperationResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        FriendResponse friendResponse = new FriendResponse();
        friendResponse.setUserID(application.getUserID());
        int internalResponseType;
        if (V2TIMFriendApplication.V2TIM_FRIEND_ACCEPT_AGREE == responseType) {
            internalResponseType = FriendResponse.RESPONSE_AGREE;
        } else {
            internalResponseType = FriendResponse.RESPONSE_AGREE_AND_ADD;
        }
        friendResponse.setResponseType(internalResponseType);
//        friendResponse.setRemark(); // 为了精简暂时去掉，如果想加好友备注再去调用修改好友吧
        RelationshipManager.getInstance().responseFriendApplication(friendResponse, new XIMCallback<FriendOperationResult>(v2TIMValueCallback) {
            @Override
            public void success(FriendOperationResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void refuseFriendApplication(V2TIMFriendApplication application, final V2TIMValueCallback<V2TIMFriendOperationResult> callback) {
        if (application == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "application is null");
            }
            return;
        }

        V2TIMValueCallback<FriendOperationResult> v2TIMValueCallback = new V2TIMValueCallback<FriendOperationResult>() {
            @Override
            public void onSuccess(FriendOperationResult friendOperationResult) {
                if (callback != null) {
                    V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                    v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                    callback.onSuccess(v2TIMFriendOperationResult);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        FriendResponse friendResponse = new FriendResponse();
        friendResponse.setUserID(application.getUserID());
        friendResponse.setResponseType(FriendResponse.RESPONSE_REJECT);
        RelationshipManager.getInstance().responseFriendApplication(friendResponse, new XIMCallback<FriendOperationResult>(v2TIMValueCallback) {
            @Override
            public void success(FriendOperationResult data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteFriendApplication(V2TIMFriendApplication application, final V2TIMCallback callback) {
        if (application == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "application is null");
            }
            return;
        }
        if (TextUtils.isEmpty(application.getUserID())) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "application userID is empty");
            }
            return;
        }

        RelationshipManager.getInstance().deleteFriendApplication(application.getType(), application.getUserID(), new XIMCallback(callback) {
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
    public void setFriendApplicationRead(final V2TIMCallback callback) {
        RelationshipManager.getInstance().setFriendApplicationRead(new XIMCallback(callback) {
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
    public void addToBlackList(List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResultList) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResultList) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().addToBlackList(userIDList, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteFromBlackList(List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResultList) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResultList) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().deleteFromBlackList(userIDList, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getBlackList(final V2TIMValueCallback<List<V2TIMFriendInfo>> callback) {
        V2TIMValueCallback<List<FriendInfo>> v2Callback = new V2TIMValueCallback<List<FriendInfo>>() {
            @Override
            public void onSuccess(List<FriendInfo> friendInfoList) {
                if (callback != null) {
                    List<V2TIMFriendInfo> v2TIMFriendInfoList = new ArrayList<>();
                    for (FriendInfo friendInfo : friendInfoList) {
                        V2TIMFriendInfo v2TIMFriendInfo = new V2TIMFriendInfo();
                        v2TIMFriendInfo.setFriendInfo(friendInfo);
                        v2TIMFriendInfoList.add(v2TIMFriendInfo);
                    }
                    callback.onSuccess(v2TIMFriendInfoList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getBlackList(new XIMCallback<List<FriendInfo>>(v2Callback) {
            @Override
            public void success(List<FriendInfo> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void createFriendGroup(String groupName, List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (groupName == null) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupNames is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResultList) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResultList) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().createFriendGroup(groupName, userIDList, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void getFriendGroups(List<String> groupNameList, final V2TIMValueCallback<List<V2TIMFriendGroup>> callback) {
        if (groupNameList != null && groupNameList.size() == 0) {
            groupNameList = null;
        }

        if (callback == null) {
            return;
        }

        V2TIMValueCallback<List<FriendGroup>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendGroup>>() {
            @Override
            public void onSuccess(List<FriendGroup> friendGroupList) {
                if (callback != null) {
                    List<V2TIMFriendGroup> v2TIMFriendGroupList = new ArrayList<>();
                    for (FriendGroup friendGroup : friendGroupList) {
                        V2TIMFriendGroup v2TIMFriendGroup = new V2TIMFriendGroup();
                        v2TIMFriendGroup.setFriendGroup(friendGroup);
                        v2TIMFriendGroupList.add(v2TIMFriendGroup);
                    }
                    callback.onSuccess(v2TIMFriendGroupList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().getFriendGroups(groupNameList, new XIMCallback<List<FriendGroup>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendGroup> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteFriendGroup(List<String> groupNameList, final V2TIMCallback callback) {
        if (groupNameList == null || groupNameList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupNames is empty");
            }
            return;
        }

        RelationshipManager.getInstance().deleteFriendGroup(groupNameList, new XIMCallback(callback) {
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
    public void renameFriendGroup(String oldName, String newName, final V2TIMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        if (TextUtils.isEmpty(newName)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "newName is empty");
            }
            return;
        }
        if (TextUtils.isEmpty(oldName)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "oldName is empty");
            }
            return;
        }

        RelationshipManager.getInstance().renameFriendGroup(oldName, newName, new XIMCallback(callback) {
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
    public void addFriendsToFriendGroup(String groupName, List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (TextUtils.isEmpty(groupName)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupName is empty");
            }
            return;
        }
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResultList) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResultList) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().addFriendsToFriendGroup(groupName, userIDList, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    @Override
    public void deleteFriendsFromFriendGroup(String groupName, List<String> userIDList, final V2TIMValueCallback<List<V2TIMFriendOperationResult>> callback) {
        if (TextUtils.isEmpty(groupName)) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "groupName is empty");
            }
            return;
        }
        if (userIDList == null || userIDList.size() == 0) {
            if (callback != null) {
                callback.onError(BaseConstants.ERR_INVALID_PARAMETERS, "userIDList is empty");
            }
            return;
        }

        V2TIMValueCallback<List<FriendOperationResult>> v2TIMValueCallback = new V2TIMValueCallback<List<FriendOperationResult>>() {
            @Override
            public void onSuccess(List<FriendOperationResult> friendOperationResultList) {
                if (callback != null) {
                    List<V2TIMFriendOperationResult> v2TIMFriendInfoResultList = new ArrayList<>();
                    for (FriendOperationResult friendOperationResult : friendOperationResultList) {
                        V2TIMFriendOperationResult v2TIMFriendOperationResult = new V2TIMFriendOperationResult();
                        v2TIMFriendOperationResult.setFriendOperationResult(friendOperationResult);
                        v2TIMFriendInfoResultList.add(v2TIMFriendOperationResult);
                    }
                    callback.onSuccess(v2TIMFriendInfoResultList);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (callback != null) {
                    callback.onError(code, desc);
                }
            }
        };

        RelationshipManager.getInstance().deleteFrendsFromFriendGroup(groupName, userIDList, new XIMCallback<List<FriendOperationResult>>(v2TIMValueCallback) {
            @Override
            public void success(List<FriendOperationResult> data) {
                super.success(data);
            }

            @Override
            public void fail(int code, String errorMessage) {
                super.fail(code, errorMessage);
            }
        });
    }

    private void initFriendshipListener() {
        mFriendshipInternalListener = new FriendshipListener() {
            V2TIMFriendInfo convertToV2FriendInfo(FriendInfo friendInfo) {
                V2TIMFriendInfo v2TIMFriendInfo = new V2TIMFriendInfo();
                if (friendInfo != null) {
                    v2TIMFriendInfo.setFriendInfo(friendInfo);
                }

                return v2TIMFriendInfo;
            }

            List<V2TIMFriendInfo> convertToV2FriendInfoList(List<FriendInfo> friendInfoList) {
                List<V2TIMFriendInfo> v2TIMFriendInfoList = new ArrayList<>();
                for (FriendInfo friendInfo : friendInfoList) {
                    v2TIMFriendInfoList.add(convertToV2FriendInfo(friendInfo));
                }

                return v2TIMFriendInfoList;
            }

            @Override
            public void OnSelfInfoUpdated(UserInfo userInfo) {
                // 该回调是在 SDKListener 中实现的，这里不做处理。
            }

            @Override
            public void OnFriendInfoChanged(final List<FriendInfo> friendInfoList) {
                List<V2TIMFriendInfo> groupMemberInfoList = convertToV2FriendInfoList(friendInfoList);
                List<V2TIMFriendInfo> unmodifiableList = Collections.unmodifiableList(groupMemberInfoList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendInfoChanged(unmodifiableList);
                }
            }

            @Override
            public void OnFriendListAdded(final List<FriendInfo> friendInfoList) {
                List<V2TIMFriendInfo> v2TIMFriendInfoList = convertToV2FriendInfoList(friendInfoList);
                List<V2TIMFriendInfo> unmodifiableList = Collections.unmodifiableList(v2TIMFriendInfoList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendListAdded(unmodifiableList);
                }
            }

            @Override
            public void OnFriendListDeleted(List<String> userIDList) {
                List<String> unmodifiableList = Collections.unmodifiableList(userIDList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendListDeleted(unmodifiableList);
                }
            }

            @Override
            public void OnBlackListAdded(List<FriendInfo> friendInfoList) {
                List<V2TIMFriendInfo> v2TIMFriendInfoList = convertToV2FriendInfoList(friendInfoList);
                List<V2TIMFriendInfo> unmodifiableList = Collections.unmodifiableList(v2TIMFriendInfoList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onBlackListAdd(unmodifiableList);
                }
            }

            @Override
            public void OnBlackListDeleted(List<String> userIDList) {
                List<String> unmodifiableList = Collections.unmodifiableList(userIDList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onBlackListDeleted(unmodifiableList);
                }
            }

            @Override
            public void OnFriendApplicationListAdded(List<FriendApplication> applicationList) {
                List<V2TIMFriendApplication> v2TIMFriendApplicationList = new ArrayList<>();
                for (FriendApplication friendApplication : applicationList) {
                    V2TIMFriendApplication v2TIMFriendApplication = new V2TIMFriendApplication();
                    v2TIMFriendApplication.setFriendApplication(friendApplication);
                    v2TIMFriendApplicationList.add(v2TIMFriendApplication);
                }
                List<V2TIMFriendApplication> unmodifiableList = Collections.unmodifiableList(v2TIMFriendApplicationList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendApplicationListAdded(unmodifiableList);
                }
            }

            @Override
            public void OnFriendApplicationListDelete(List<String> userIDList) {
                List<String> unmodifiableList = Collections.unmodifiableList(userIDList);
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendApplicationListDeleted(unmodifiableList);
                }
            }

            @Override
            public void OnFriendApplicationListRead() {
                for (V2TIMFriendshipListener listener : mFriendshipListenerList) {
                    listener.onFriendApplicationListRead();
                }
            }
        };
        RelationshipManager.getInstance().setFriendshipListener(mFriendshipInternalListener);
    }

}
