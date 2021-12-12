package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  12:02 下午
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GroupMemberInfo implements Serializable {

    public static int MEMBER_ROLE_MEMBER = 200;
    public static int MEMBER_ROLE_ADMINISTRATOR = 300;
    public static int MEMBER_ROLE_OWNER = 400;

    public static int MESSAGE_RECEIVE_OPTION_AUTO_RECEIVE = 1;
    public static int MESSAGE_RECEIVE_OPTION_NOT_RECEIVE = 2;
    public static int MESSAGE_RECEIVE_OPTION_RECEIVE_WITH_NO_OFFLINE_PUSH = 3;

    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_NONE = 0;
    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_MESSAGE_RECEIVE_FLAG = 1;
    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_MEMBER_ROLE = 2;
    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_SHUTUP_TIME = 4;
    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_NAME_CARD = 8;
    public static long GROUP_MEMBER_INFO_MODIFY_FLAG_CUSTOM_INFO = 16;

    public static int GROUP_MEMBER_FILTER_FLAG_ALL = 1;
    public static int GROUP_MEMBER_FILTER_FLAG_OWNER = 2;
    public static int GROUP_MEMBER_FILTER_FLAG_ADMINISTRATOR = 3;
    public static int GROUP_MEMBER_FILTER_FLAG_MEMBER = 4;


    private String groupID;
    private String userID;
    private String nameCard;
    private String nickname;
    private String friendRemark;
    private String faceUrl;
    private long joinTime;
    private int role;
    private long shutUpTime;
    private int messageReceiveOption;
    private long messageReadSequence;
    private Map<String, byte[]> customInfo = new HashMap<>();

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getShutUpTime() {
        return shutUpTime;
    }

    public void setShutUpTime(long shutUpTime) {
        this.shutUpTime = shutUpTime;
    }

    public int getMessageReceiveOption() {
        return messageReceiveOption;
    }

    public void setMessageReceiveOption(int messageReceiveOption) {
        this.messageReceiveOption = messageReceiveOption;
    }

    public long getMessageReadSequence() {
        return messageReadSequence;
    }

    public void setMessageReadSequence(long messageReadSequence) {
        this.messageReadSequence = messageReadSequence;
    }

    public Map<String, byte[]> getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(Map<String, byte[]> customInfo) {
        this.customInfo = customInfo;
    }

    private void addCustomInfo(String key, byte[]value) {
        this.customInfo.put(key, value);
    }
}
