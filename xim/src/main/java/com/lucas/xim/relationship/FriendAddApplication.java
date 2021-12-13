package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:29 上午
 */

import java.io.Serializable;

public class FriendAddApplication implements Serializable {
    private String userID;
    private String remark;
    private String groupName;
    private String addWording;
    private String addSource;
    private int addType;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAddWording(String addWording) {
        this.addWording = addWording;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }

    public void setAddType(int addType) {
        this.addType = addType;
    }
}
