package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:47 下午
 */

import com.lucas.xim.BaseConstants;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.BaseManager;
import com.lucas.xim.v1.IMContext;
import com.lucas.xim.v1.UserInfo;

import java.util.HashMap;
import java.util.List;

public class RelationshipManager {
    private FriendshipListener mFriendshipInternalListener;
    private FriendshipListener mFriendshipListener;

    private static class RelationshipManagerHolder {
        private static final RelationshipManager relationshipManager = new RelationshipManager();
    }

    public static RelationshipManager getInstance() {
        return RelationshipManager.RelationshipManagerHolder.relationshipManager;
    }

    public void init() {
        initFriendshipListener();
    }

    private void initFriendshipListener() {
        if (mFriendshipInternalListener == null) {
            mFriendshipInternalListener = new FriendshipListener() {
                @Override
                public void OnSelfInfoUpdated(UserInfo userInfo) {
                    BaseManager.getInstance().notifySelfInfoUpdated(userInfo);
                }

                @Override
                public void OnFriendInfoChanged(final List<FriendInfo> friendInfoList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendInfoChanged(friendInfoList);
                            }
                        }
                    });
                }

                @Override
                public void OnFriendListAdded(final List<FriendInfo> friendInfoList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendListAdded(friendInfoList);
                            }
                        }
                    });
                }

                @Override
                public void OnFriendListDeleted(final List<String> userIDList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendListDeleted(userIDList);
                            }
                        }
                    });
                }

                @Override
                public void OnBlackListAdded(final List<FriendInfo> friendInfoList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnBlackListAdded(friendInfoList);
                            }
                        }
                    });
                }

                @Override
                public void OnBlackListDeleted(final List<String> userIDList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnBlackListDeleted(userIDList);
                            }
                        }
                    });
                }

                @Override
                public void OnFriendApplicationListAdded(final List<FriendApplication> applicationList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendApplicationListAdded(applicationList);
                            }
                        }
                    });
                }

                @Override
                public void OnFriendApplicationListDelete(final List<String> userIDList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendApplicationListDelete(userIDList);
                            }
                        }
                    });
                }

                @Override
                public void OnFriendApplicationListRead() {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mFriendshipListener != null) {
                                mFriendshipListener.OnFriendApplicationListRead();
                            }
                        }
                    });
                }
            };
        }
    }

    public void setFriendshipListener(FriendshipListener friendshipListener) {
        this.mFriendshipListener = friendshipListener;
    }

    public void getUsersInfo(List<String> userIDList, IMCallback<List<UserInfo>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetUsersInfo(userIDList, callback);
    }

    public void setSelfInfo(HashMap<String, Object> modifyParams, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetSelfInfo(modifyParams, callback);
    }

    public void setC2CReceiveMessageOpt(List<String> userIDList, int opt, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetC2CReceiveMessageOpt(userIDList, opt, callback);
    }


    public void getFriendList(IMCallback<List<FriendInfo>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetFriendList(callback);
    }

    public void getFriendsInfo(List<String> userIDList, IMCallback<List<FriendInfoResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetFriendsInfo(userIDList, callback);
    }

    public void setFriendInfo(String userID, HashMap<String, Object> modifyParams, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetFriendInfo(userID, modifyParams, callback);
    }

    public void searchFriends(FriendSearchParam searchParam, IMCallback<List<FriendInfoResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSearchFriends(searchParam, callback);
    }


    public void addFriend(FriendAddApplication friendAddApplication, IMCallback<FriendOperationResult> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeAddFriend(friendAddApplication, callback);
    }

    public void deleteFromFriendList(List<String> userIDList, int deleteType, IMCallback<List<FriendOperationResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeDeleteFromFriendList(userIDList, deleteType, callback);
    }

    public void checkFriend(List<String> userIDList, int checkType, IMCallback<List<FriendCheckResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeCheckFriend(userIDList, checkType, callback);
    }

    // 获取好友申请列表
    public void getFriendApplicationList(IMCallback<FriendApplicationResult> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetFriendApplicationList(callback);
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected native void nativeGetUsersInfo(List<String> userIDList, final IMCallback<List<UserInfo>> callback);

    protected native void nativeSetSelfInfo(HashMap<String, Object> modifyParams, final IMCallback callback);

    protected native void nativeSetFriendshipListener(FriendshipListener friendshipListener);

    protected native void nativeGetFriendList(IMCallback callback);

    protected native void nativeGetFriendsInfo(List<String> userIDList, IMCallback callback);

    protected native void nativeSetFriendInfo(String userID, HashMap<String, Object> modifyParams, IMCallback callback);

    protected native void nativeSearchFriends(FriendSearchParam param, IMCallback callback);

    protected native void nativeAddFriend(FriendAddApplication friendAddApplication, IMCallback callback);

    protected native void nativeDeleteFromFriendList(List<String> userIDList, int deleteType, IMCallback callback);

    protected native void nativeCheckFriend(List<String> userIDList, int checkType, IMCallback callback);

    protected native void nativeGetFriendApplicationList(IMCallback callback);

    protected native void nativeSetC2CReceiveMessageOpt(List<String> userIDList, int opt, IMCallback callback);

}
