package com.lucas.ximdemo.xim.v1;

import com.lucas.ximdemo.UserInfo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:48 上午
 */
class IMUserInfo implements Serializable {
    UserInfo userInfo = new UserInfo();
    HashMap<String, Object> modifyParams = new HashMap();

    public IMUserInfo() {
    }

    public String getUserID() {
        return this.userInfo.getUserID();
    }

    public String getNickName() {
        return this.userInfo.getNickname();
    }

    public String getFaceUrl() {
        return this.userInfo.getFaceUrl();
    }

    public long getBirthday() {
        return this.userInfo.getBirthday();
    }

    public void setBirthday(long birthday) {
        this.userInfo.setBirthday(birthday);
        this.modifyParams.put("Tag_Profile_IM_BirthDay", birthday);
    }

    protected void setUserID(String userID) {
        this.userInfo.setUserID(userID);
    }

    protected void setNickName(String nickName) {
        this.userInfo.setNickname(nickName);
        this.modifyParams.put("Tag_Profile_IM_Nick", nickName);
    }

    protected void setFaceUrl(String faceUrl) {
        this.userInfo.setFaceUrl(faceUrl);
        this.modifyParams.put("Tag_Profile_IM_Image", faceUrl);
    }

    UserInfo getUserInfo() {
        return this.userInfo;
    }

    void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    HashMap<String, Object> getModifyParams() {
        return this.modifyParams;
    }
}
