package com.lucas.xim.relationship;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:27 上午
 */
public class FriendInfoResult implements Serializable {
    private String userID;
    private int relationType;
    private int errorCode;
    private String errorMessage;
    private FriendInfo friendInfo;

    public String getUserID() {
        return userID;
    }

    public int getRelationType() {
        return relationType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public FriendInfo getFriendInfo() {
        return friendInfo;
    }
}