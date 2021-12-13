package com.lucas.xim.group;

import com.lucas.xim.v1.GroupMemberInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:06 下午
 */

public class GroupInfo implements Serializable {

    public static int GROUP_ADD_OPTION_FORBID_ANY = 1;
    public static int GROUP_ADD_OPTION_NEED_AUTHENTICATION = 2;
    public static int GROUP_ADD_OPTION_ALLOW_ANY = 3;

    public static long GROUP_INFO_MODIFY_FLAG_NONE = 0;
    public static long GROUP_INFO_MODIFY_FLAG_NAME = 0x1;
    public static long GROUP_INFO_MODIFY_FLAG_NOTIFICATION = (0x1 << 1);
    public static long GROUP_INFO_MODIFY_FLAG_INTRODUCTION = (0x1 << 2);
    public static long GROUP_INFO_MODIFY_FLAG_FACE_URL = (0x1 << 3);
    public static long GROUP_INFO_MODIFY_FLAG_ADD_OPTION = (0x1 << 4);
    public static long GROUP_INFO_MODIFY_FLAG_MAX_MEMBER_NUM = (0x1 << 5);
    public static long GROUP_INFO_MODIFY_FLAG_MEMBER_VISIBLE = (0x1 << 6);
    public static long GROUP_INFO_MODIFY_FLAG_GROUP_SEARCHABLE = (0x1 << 7);
    public static long GROUP_INFO_MODIFY_FLAG_SHUTUP_ALL = (0x1 << 8);
    public static long GROUP_INFO_MODIFY_FLAG_CUSTOM_INFO = (0x1 << 9);

    private String groupID;
    private String groupName;
    private String groupType;
    private String faceUrl;
    private String introduction;
    private String notification;

    private long createTime;
    private String ownerUserID;
    private long memberCount;
    private long memberOnlineCount;
    private long memberMaxCount;
    private int addOption;
    private boolean isAllShutUp;
    private boolean isGroupSearchable;
    private boolean isGroupMemberVisible;
    private long groupInfoTimestamp;
    private long lastMessageTimestamp;
    private Map<String, byte[]> customInfo = new HashMap<>();

    private GroupMemberInfo groupSelfInfo = new GroupMemberInfo();

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(String ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public long getMemberOnlineCount() {
        return memberOnlineCount;
    }

    public void setMemberOnlineCount(long memberOnlineCount) {
        this.memberOnlineCount = memberOnlineCount;
    }

    public long getMemberMaxCount() {
        return memberMaxCount;
    }

    public void setMemberMaxCount(long memberMaxCount) {
        this.memberMaxCount = memberMaxCount;
    }

    public int getAddOption() {
        return addOption;
    }

    public void setAddOption(int addOption) {
        this.addOption = addOption;
    }

    public boolean isAllShutUp() {
        return isAllShutUp;
    }

    public void setAllShutUp(boolean allShutUp) {
        isAllShutUp = allShutUp;
    }

    public boolean isGroupSearchable() {
        return isGroupSearchable;
    }

    public void setGroupSearchable(boolean groupSearchable) {
        isGroupSearchable = groupSearchable;
    }

    public boolean isGroupMemberVisible() {
        return isGroupMemberVisible;
    }

    public void setGroupMemberVisible(boolean groupMemberVisible) {
        isGroupMemberVisible = groupMemberVisible;
    }

    public long getGroupInfoTimestamp() {
        return groupInfoTimestamp;
    }

    public void setGroupInfoTimestamp(long groupInfoTimestamp) {
        this.groupInfoTimestamp = groupInfoTimestamp;
    }

    public long getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(long lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public Map<String, byte[]> getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(Map<String, byte[]> customInfo) {
        this.customInfo = customInfo;
    }

    public GroupMemberInfo getGroupSelfInfo() {
        return groupSelfInfo;
    }

    public void setGroupSelfInfo(GroupMemberInfo groupSelfInfo) {
        this.groupSelfInfo = groupSelfInfo;
    }
}
