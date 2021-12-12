package com.lucas.ximdemo.xim.v1;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:11 下午
 */

public class IMGroupMemberInfo implements Serializable {
    GroupMemberInfo groupMemberInfo = new GroupMemberInfo();
    UserInfo userInfo;

    public IMGroupMemberInfo() {
    }

    public String getUserID() {
        if (!TextUtils.isEmpty(this.groupMemberInfo.getUserID())) {
            return this.groupMemberInfo.getUserID();
        } else {
            return this.userInfo != null ? this.userInfo.getUserID() : "";
        }
    }

    public String getNickName() {
        if (!TextUtils.isEmpty(this.groupMemberInfo.getNickname())) {
            return this.groupMemberInfo.getNickname();
        } else {
            return this.userInfo != null ? this.userInfo.getNickname() : "";
        }
    }

    public String getNameCard() {
        return this.groupMemberInfo.getNameCard();
    }

    public String getFriendRemark() {
        return this.groupMemberInfo.getFriendRemark();
    }

    public String getFaceUrl() {
        if (!TextUtils.isEmpty(this.groupMemberInfo.getFaceUrl())) {
            return this.groupMemberInfo.getFaceUrl();
        } else {
            return this.userInfo != null ? this.userInfo.getFaceUrl() : "";
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("V2TIMGroupMemberInfo--->");
        stringBuilder.append("userID:").append(this.getUserID()).append(", nickName:").append(this.getNickName()).append(", nameCard:").append(this.getNameCard()).append(", friendRemark:").append(this.getFriendRemark()).append(", faceUrl:").append(this.getFaceUrl());
        return stringBuilder.toString();
    }

    GroupMemberInfo getGroupMemberInfo() {
        return this.groupMemberInfo;
    }

    void setGroupMemberInfo(GroupMemberInfo groupMemberInfo) {
        this.groupMemberInfo = groupMemberInfo;
    }

    void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
