package com.lucas.ximdemo.xim.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:16 下午
 */
public abstract class IMGroupManager {
    public IMGroupManager() {
    }

    static IMGroupManager getInstance() {
        return V2TIMGroupManagerImpl.getInstance();
    }

    public abstract void createGroup(V2TIMGroupInfo var1, List<V2TIMCreateGroupMemberInfo> var2, V2TIMValueCallback<String> var3);

    public abstract void getJoinedGroupList(V2TIMValueCallback<List<V2TIMGroupInfo>> var1);

    public abstract void getGroupsInfo(List<String> var1, V2TIMValueCallback<List<V2TIMGroupInfoResult>> var2);

    public abstract void searchGroups(V2TIMGroupSearchParam var1, V2TIMValueCallback<List<V2TIMGroupInfo>> var2);

    public abstract void setGroupInfo(V2TIMGroupInfo var1, V2TIMCallback var2);

    public abstract void initGroupAttributes(String var1, HashMap<String, String> var2, V2TIMCallback var3);

    public abstract void setGroupAttributes(String var1, HashMap<String, String> var2, V2TIMCallback var3);

    public abstract void deleteGroupAttributes(String var1, List<String> var2, V2TIMCallback var3);

    public abstract void getGroupAttributes(String var1, List<String> var2, V2TIMValueCallback<Map<String, String>> var3);

    public abstract void getGroupOnlineMemberCount(String var1, V2TIMValueCallback<Integer> var2);

    public abstract void getGroupMemberList(String var1, int var2, long var3, V2TIMValueCallback<V2TIMGroupMemberInfoResult> var5);

    public abstract void getGroupMembersInfo(String var1, List<String> var2, V2TIMValueCallback<List<V2TIMGroupMemberFullInfo>> var3);

    public abstract void searchGroupMembers(V2TIMGroupMemberSearchParam var1, V2TIMValueCallback<HashMap<String, List<V2TIMGroupMemberFullInfo>>> var2);

    public abstract void setGroupMemberInfo(String var1, V2TIMGroupMemberFullInfo var2, V2TIMCallback var3);

    public abstract void muteGroupMember(String var1, String var2, int var3, V2TIMCallback var4);

    public abstract void inviteUserToGroup(String var1, List<String> var2, V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> var3);

    public abstract void kickGroupMember(String var1, List<String> var2, String var3, V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> var4);

    public abstract void setGroupMemberRole(String var1, String var2, int var3, V2TIMCallback var4);

    public abstract void transferGroupOwner(String var1, String var2, V2TIMCallback var3);

    public abstract void getGroupApplicationList(V2TIMValueCallback<V2TIMGroupApplicationResult> var1);

    public abstract void acceptGroupApplication(V2TIMGroupApplication var1, String var2, V2TIMCallback var3);

    public abstract void refuseGroupApplication(V2TIMGroupApplication var1, String var2, V2TIMCallback var3);

    public abstract void setGroupApplicationRead(V2TIMCallback var1);
}
