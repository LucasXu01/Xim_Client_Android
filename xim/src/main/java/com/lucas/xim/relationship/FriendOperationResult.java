package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:29 上午
 */
import java.io.Serializable;

public class FriendOperationResult implements Serializable {
    private String userID;
    private int resultCode;
    private String resultInfo;

    public String getUserID() {
        return userID;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }
}

