package com.lucas.ximdemo.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:22 下午
 */

import android.text.TextUtils;

import com.lucas.ximdemo.xim.v1.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendInfo implements Serializable {
    private String remark;
    private long addTime;
    private String addSource;
    private String addWording;
    private int relationType;
    private List<String> friendGroups = new ArrayList<>();
    private HashMap<String, byte[]> friendCustomInfo = new HashMap<>();
    private UserInfo userInfo = new UserInfo();

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getAddSource() {
        return addSource;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }

    public String getAddWording() {
        return addWording;
    }

    public void setAddWording(String addWording) {
        this.addWording = addWording;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public List<String> getFriendGroups() {
        return friendGroups;
    }

    public HashMap<String, byte[]> getFriendCustomInfo() {
        return friendCustomInfo;
    }

    protected void addFriendGroup(String friendGroup) {
        if (!TextUtils.isEmpty(friendGroup)) {
            this.friendGroups.add(friendGroup);
        }
    }

    protected void addFriendCustomInfo(String key, byte[] value) {
        this.friendCustomInfo.put(key, value);
    }

    protected void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}

