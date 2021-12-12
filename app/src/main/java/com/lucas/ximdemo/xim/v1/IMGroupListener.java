package com.lucas.ximdemo.xim.v1;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:46 上午
 */
public abstract class IMGroupListener {
    public IMGroupListener() {
    }

    public void onMemberEnter(String groupID, List<IMGroupMemberInfo> memberList) {
    }

    public void onMemberLeave(String groupID, IMGroupMemberInfo member) {
    }

    public void onMemberInvited(String groupID, IMGroupMemberInfo opUser, List<IMGroupMemberInfo> memberList) {
    }

    public void onMemberKicked(String groupID, IMGroupMemberInfo opUser, List<IMGroupMemberInfo> memberList) {
    }

    public void onMemberInfoChanged(String groupID, List<IMGroupMemberChangeInfo> v2TIMGroupMemberChangeInfoList) {
    }

    public void onGroupCreated(String groupID) {
    }

    public void onGroupDismissed(String groupID, IMGroupMemberInfo opUser) {
    }

    public void onGroupRecycled(String groupID, IMGroupMemberInfo opUser) {
    }

    public void onGroupInfoChanged(String groupID, List<IMGroupChangeInfo> changeInfos) {
    }

    public void onReceiveJoinApplication(String groupID, IMGroupMemberInfo member, String opReason) {
    }

    public void onApplicationProcessed(String groupID, IMGroupMemberInfo opUser, boolean isAgreeJoin, String opReason) {
    }

    public void onGrantAdministrator(String groupID, IMGroupMemberInfo opUser, List<IMGroupMemberInfo> memberList) {
    }

    public void onRevokeAdministrator(String groupID, IMGroupMemberInfo opUser, List<IMGroupMemberInfo> memberList) {
    }

    public void onQuitFromGroup(String groupID) {
    }

    public void onReceiveRESTCustomData(String groupID, byte[] customData) {
    }

    public void onGroupAttributeChanged(String groupID, Map<String, String> groupAttributeMap) {
    }
}
