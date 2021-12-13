package com.lucas.xim.relationship;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:30 上午
 */
public class FriendCheckResult implements Serializable {
    private String userID;
    private int resultCode;
    private String resultInfo;
    private int relationType;

    public String getUserID() {
        return userID;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public int getRelationType() {
        return relationType;
    }
}
