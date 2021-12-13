package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:22 下午
 */
import java.io.Serializable;

public class FriendApplication implements Serializable {
    private String userID;
    private String nickName;
    private String faceUrl;
    private long addTime;
    private String addSource;
    private String addWording;
    private int applicationType;

    public String getUserID() {
        return userID;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public long getAddTime() {
        return addTime;
    }

    public String getAddSource() {
        return addSource;
    }

    public String getAddWording() {
        return addWording;
    }

    public int getApplicationType() {
        return applicationType;
    }
}

