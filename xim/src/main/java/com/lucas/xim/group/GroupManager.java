package com.lucas.xim.group;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:36 下午
 */

import com.lucas.xim.BaseConstants;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.BaseManager;
import com.lucas.xim.v1.GroupInfoChangeItem;
import com.lucas.xim.v1.GroupListener;
import com.lucas.xim.v1.GroupMemberInfo;
import com.lucas.xim.v1.GroupMemberInfoChangeItem;
import com.lucas.xim.v1.IMContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupManager {
    private GroupListener mGroupInternalListener;
    private GroupListener mGroupListener;

    private static class GroupManagerHolder {
        private static final GroupManager groupManager = new GroupManager();
    }

    public static GroupManager getInstance() {
        return GroupManager.GroupManagerHolder.groupManager;
    }

    public void init() {
        initGroupListener();
    }

    private void initGroupListener() {
        // 统一转到 主线程 抛出去
        if (mGroupInternalListener == null) {
            mGroupInternalListener = new GroupListener() {
                @Override
                public void onMemberEnter(final String groupID, final List<GroupMemberInfo> memberList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onMemberEnter(groupID, memberList);
                            }
                        }
                    });
                }

                @Override
                public void onMemberLeave(final String groupID, final GroupMemberInfo member) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onMemberLeave(groupID, member);
                            }
                        }
                    });
                }

                @Override
                public void onMemberInvited(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onMemberInvited(groupID, opUser, memberList);
                            }
                        }
                    });
                }

                @Override
                public void onMemberKicked(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onMemberKicked(groupID, opUser, memberList);
                            }
                        }
                    });
                }

                @Override
                public void onMemberInfoChanged(final String groupID, final List<GroupMemberInfoChangeItem> v2TIMGroupMemberChangeInfoList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onMemberInfoChanged(groupID, v2TIMGroupMemberChangeInfoList);
                            }
                        }
                    });
                }

                @Override
                public void onGroupCreated(final String groupID) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGroupCreated(groupID);
                            }
                        }
                    });
                }

                @Override
                public void onGroupDismissed(final String groupID, final GroupMemberInfo opUser) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGroupDismissed(groupID, opUser);
                            }
                        }
                    });
                }

                @Override
                public void onGroupRecycled(final String groupID, final GroupMemberInfo opUser) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGroupRecycled(groupID, opUser);
                            }
                        }
                    });
                }

                @Override
                public void onGroupInfoChanged(final String groupID, final List<GroupInfoChangeItem> changeInfos) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGroupInfoChanged(groupID, changeInfos);
                            }
                        }
                    });
                }

                @Override
                public void onReceiveJoinApplication(final String groupID, final GroupMemberInfo member, final String opReason) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onReceiveJoinApplication(groupID, member, opReason);
                            }
                        }
                    });
                }

                @Override
                public void onApplicationProcessed(final String groupID, final GroupMemberInfo opUser, final boolean isAgreeJoin, final String opReason) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onApplicationProcessed(groupID, opUser, isAgreeJoin, opReason);
                            }
                        }
                    });
                }

                @Override
                public void onGrantAdministrator(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGrantAdministrator(groupID, opUser, memberList);
                            }
                        }
                    });
                }

                @Override
                public void onRevokeAdministrator(final String groupID, final GroupMemberInfo opUser, final List<GroupMemberInfo> memberList) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onRevokeAdministrator(groupID, opUser, memberList);
                            }
                        }
                    });
                }

                @Override
                public void onQuitFromGroup(final String groupID) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onQuitFromGroup(groupID);
                            }
                        }
                    });
                }

                @Override
                public void onReceiveRESTCustomData(final String groupID, final byte[] customData) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onReceiveRESTCustomData(groupID, customData);
                            }
                        }
                    });
                }

                @Override
                public void onGroupAttributeChanged(final String groupID, final Map<String, String> groupAttributeMap) {
                    IMContext.getInstance().runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mGroupListener != null) {
                                mGroupListener.onGroupAttributeChanged(groupID, groupAttributeMap);
                            }
                        }
                    });
                }
            };
        }
    }

    public void setGroupListener(GroupListener listener) {
        mGroupListener = listener;
    }

    public void createGroup(String groupType, String groupID, String groupName, final IMCallback<String> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupID(groupID);
        groupInfo.setGroupType(groupType);
        groupInfo.setGroupName(groupName);

        nativeCreateGroup(groupInfo, null, callback);
    }

    public void createGroup(GroupInfo info, List<GroupMemberInfo> memberList, IMCallback<String> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeCreateGroup(info, memberList, callback);
    }

    public void joinGroup(String groupID, String message, final IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeJoinGroup(groupID, message, callback);
    }

    public void quitGroup(String groupID, final IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeQuitGroup(groupID, callback);
    }

    public void dismissGroup(String groupID, final IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeDismissGroup(groupID, callback);
    }

    public void getJoinedGroupList(IMCallback<List<GroupInfo>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetJoinedGroupList(callback);
    }

    public void getGroupsInfo(List<String> groupIDList, IMCallback<List<GroupInfoGetResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupsInfo(groupIDList, callback);
    }


    public void setGroupInfo(GroupInfoModifyParam param, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetGroupInfo(param, callback);
    }

    public void setGroupReceiveMessageOpt(String groupID, int opt, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetGroupMessageReceiveOption(groupID, opt, callback);
    }

    public void initGroupAttributes(String groupID, HashMap<String, String> attributes, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeInitGroupAttributes(groupID, attributes, callback);
    }

    public void setGroupAttributes(String groupID, HashMap<String, String> attributes, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeUpdateGroupAttributes(groupID, attributes, callback);
    }

    public void deleteGroupAttributes(String groupID, List<String> keys, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeDeleteGroupAttributes(groupID, keys, callback);
    }

    public void getGroupAttributes(String groupID, List<String> keys, IMCallback<Map<String, String>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupAttributes(groupID, keys, callback);
    }

    public void getGroupOnlineMemberCount(String groupID, IMCallback<Integer> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupOnlineMemberCount(groupID, callback);
    }

    public void getGroupMemberList(String groupID, int filter, long nextSeq, IMCallback<GroupMemberInfoResult> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupMemberList(groupID, filter, nextSeq, callback);
    }

    public void getGroupMembersInfo(String groupID, List<String> memberList, IMCallback<List<GroupMemberInfo>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeGetGroupMembersInfo(groupID, memberList, callback);
    }


    public void kickGroupMember(String groupID, List<String> memberList, String reason, IMCallback<List<GroupMemberOperationResult>> callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeDeleteGroupMembers(groupID, memberList, reason, callback);
    }

    public void setGroupMemberRole(String groupID, String userID, int role, IMCallback callback) {
        if (!BaseManager.getInstance().isInited()) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeSetGroupMemberRole(groupID, userID, role, callback);
    }





    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected native void nativeSetGroupListener(GroupListener listener);

    protected native void nativeCreateGroup(GroupInfo groupInfo, List<GroupMemberInfo> memberInfoList, IMCallback<String> callback);

    protected native void nativeJoinGroup(String groupID, String applyMessage, final IMCallback callback);

    protected native void nativeQuitGroup(String groupID, IMCallback callback);

    protected native void nativeDismissGroup(String groupID, final IMCallback callback);

    protected native void nativeDeleteGroupMembers(String groupID, List<String> memberList, String deleteMessage, IMCallback callback);

    protected native void nativeGetJoinedGroupList(IMCallback callback);

    protected native void nativeGetGroupsInfo(List<String> groupIDList, IMCallback callback);

    protected native void nativeSetGroupInfo(GroupInfoModifyParam param, IMCallback callback);

    protected native void nativeGetGroupMemberList(String groupID, int filterFlag, long lastSequence, IMCallback callback);

    protected native void nativeGetGroupMembersInfo(String groupID, List<String> memberList, IMCallback callback);

    protected native void nativeSetGroupMemberRole(String groupID, String userID, int role, IMCallback callback);

    protected native void nativeSetGroupMessageReceiveOption(String groupID, int opt, IMCallback callback);

    protected native void nativeInitGroupAttributes(String groupID, Map<String, String> groupAttributes, IMCallback callback);

    protected native void nativeUpdateGroupAttributes(String groupID, Map<String, String> groupAttributes, IMCallback callback);

    protected native void nativeDeleteGroupAttributes(String groupID, List<String> groupAttributeKeys, IMCallback callback);

    protected native void nativeGetGroupAttributes(String groupID, List<String> groupAttributeKeys, IMCallback callback);

    protected native void nativeGetGroupOnlineMemberCount(String groupID, IMCallback callback);
}
