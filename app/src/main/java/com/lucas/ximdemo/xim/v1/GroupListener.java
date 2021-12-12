package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  12:01 下午
 */
import java.util.List;
import java.util.Map;

public abstract class GroupListener {

    /**
     * 有用户加入群（全员能够收到）
     *
     * @param groupID    群 ID
     * @param memberList 加入的成员
     */
    public void onMemberEnter(String groupID, List<GroupMemberInfo> memberList) {
    }

    /**
     * 有用户离开群（全员能够收到）
     *
     * @param groupID 群 ID
     * @param member  离开的成员
     */
    public void onMemberLeave(String groupID, GroupMemberInfo member) {
    }

    /**
     * 某些人被拉入某群（全员能够收到）
     *
     * @param groupID    群 ID
     * @param opUser     处理人
     * @param memberList 被拉进群成员
     */
    public void onMemberInvited(String groupID, GroupMemberInfo opUser, List<GroupMemberInfo> memberList) {
    }

    /**
     * 某些人被踢出某群（全员能够收到）
     *
     * @param groupID    群 ID
     * @param opUser     处理人
     * @param memberList 被踢成员
     */
    public void onMemberKicked(String groupID, GroupMemberInfo opUser, List<GroupMemberInfo> memberList) {
    }

    /**
     * 群成员信息被修改（全员能收到）
     *
     * @param groupID 群 ID
     * @param v2TIMGroupMemberChangeInfoList 被修改的群成员信息
     */
    public void onMemberInfoChanged(String groupID, List<GroupMemberInfoChangeItem> v2TIMGroupMemberChangeInfoList) {
    }

    /**
     * 创建群（主要用于多端同步）
     *
     * @param groupID 群 ID
     */
    public void onGroupCreated(String groupID) {
    }

    /**
     * 群被解散了（全员能收到）
     *
     * @param groupID 群 ID
     * @param opUser  处理人
     */
    public void onGroupDismissed(String groupID, GroupMemberInfo opUser) {
    }

    /**
     * 群被回收（全员能收到）
     *
     * @param groupID 群 ID
     * @param opUser  处理人
     */
    public void onGroupRecycled(String groupID, GroupMemberInfo opUser) {
    }

    /**
     * 群信息被修改（全员能收到）
     *
     * @param changeInfos 修改的群信息
     */
    public void onGroupInfoChanged(String groupID, List<GroupInfoChangeItem> changeInfos) {
    }

    /**
     * 有新的加群请求（只有群主或管理员会收到）
     *
     * @param groupID  群 ID
     * @param member   申请人
     * @param opReason 申请原因
     */
    public void onReceiveJoinApplication(String groupID, GroupMemberInfo member, String opReason) {
    }

    /**
     * 加群请求已经被群主或管理员处理了（只有申请人能够收到）
     *
     * @param groupID     群 ID
     * @param opUser      处理人
     * @param isAgreeJoin 是否同意加群
     * @param opReason    处理原因
     */
    public void onApplicationProcessed(String groupID, GroupMemberInfo opUser, boolean isAgreeJoin, String opReason) {
    }

    /**
     * 指定管理员身份
     *
     * @param groupID    群 ID
     * @param opUser     处理人
     * @param memberList 被处理的群成员
     */
    public void onGrantAdministrator(String groupID, GroupMemberInfo opUser, List<GroupMemberInfo> memberList) {
    }

    /**
     * 取消管理员身份
     *
     * @param groupID    群 ID
     * @param opUser     处理人
     * @param memberList 被处理的群成员
     */
    public void onRevokeAdministrator(String groupID, GroupMemberInfo opUser, List<GroupMemberInfo> memberList) {
    }

    /**
     * 主动退出群组（主要用于多端同步，直播群（AVChatRoom）不支持）
     *
     * @param groupID 群 ID
     */
    public void onQuitFromGroup(String groupID) {
    }

    /**
     * 收到 RESTAPI 下发的自定义系统消息
     *
     * @param groupID    群 ID
     * @param customData 自定义数据
     */
    public void onReceiveRESTCustomData(String groupID, byte[] customData) {
    }

    /**
     * 收到群属性更新的回调
     *
     * @param groupID           群 ID
     * @param groupAttributeMap 群的所有属性
     */
    public void onGroupAttributeChanged(String groupID, Map<String, String> groupAttributeMap) {
    }
}

