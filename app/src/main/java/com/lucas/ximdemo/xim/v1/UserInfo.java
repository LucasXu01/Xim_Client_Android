package com.lucas.ximdemo.xim.v1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:49 上午
 */
public class UserInfo implements Serializable {

    public static final String USERINFO_KEY_NICK = "Tag_Profile_IM_Nick";
    public static final String USERINFO_KEY_FACEURL = "Tag_Profile_IM_Image";
    public static final String USERINFO_KEY_ALLOWTYPE = "Tag_Profile_IM_AllowType";
    public static final String USERINFO_KEY_GENDER = "Tag_Profile_IM_Gender";
    public static final String USERINFO_KEY_SELF_SIGNATURE = "Tag_Profile_IM_SelfSignature";
    public static final String USERINFO_KEY_ROLE = "Tag_Profile_IM_Role";
    public static final String USERINFO_KEY_LEVEL = "Tag_Profile_IM_Level";
    public static final String USERINFO_KEY_CUSTOM_PREFIX = "Tag_Profile_Custom_";
    public static final String USERINFO_KEY_BIRTHDAY = "Tag_Profile_IM_BirthDay";

    public static final String USERINFO_GENDER_TYPE_UNKNOWN = "Gender_Type_Unknown";
    public static final String USERINFO_GENDER_TYPE_FEMALE = "Gender_Type_Female";
    public static final String USERINFO_GENDER_TYPE_MALE = "Gender_Type_Male";

    public static final String USERINFO_ALLOWTYPE_TYPE_ALLOWANY = "AllowType_Type_AllowAny";
    public static final String USERINFO_ALLOWTYPE_TYPE_NEEDCONFIRM = "AllowType_Type_NeedConfirm";
    public static final String USERINFO_ALLOWTYPE_TYPE_DENYANY = "AllowType_Type_DenyAny";

    public static final int USERINFO_GENDER_UNKNOWN = 0;
    public static final int USERINFO_GENDER_MALE = 1;
    public static final int USERINFO_GENDER_FEMALE = 2;

    public static final int USERINFO_ALLOWTYPE_ALLOWANY = 0;
    public static final int USERINFO_ALLOWTYPE_NEEDCONFIRM = 1;
    public static final int USERINFO_ALLOWTYPE_DENYANY = 2;

    /**
     * ## 在线正常接收消息，离线时会进行离线推送
     */
    public static final int USER_RECEIVE_MESSAGE_NATIVE = 1;

    /**
     * ## 不会接收到消息
     */
    public static final int USER_NOT_RECEIVE_MESSAGE_NATIVE = 2;

    /**
     * ## 在线正常接收消息，离线不会有推送通知
     */
    public static final int USER_RECEIVE_NOT_NOTIFY_MESSAGE_NATIVE = 3;

    private String userID;
    private String nickname;
    private String faceUrl;
    private int gender;
    private long birthday;
    private long language;
    private String location;
    private String signature;
    private int level;
    private int role;
    private int allowType;
    private HashMap<String, byte[]> customUserInfoString = new HashMap<>();
    private HashMap<String, Long> customUserInfoNumber = new HashMap<>();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAllowType() {
        return allowType;
    }

    public void setAllowType(int allowType) {
        this.allowType = allowType;
    }

    public HashMap<String, byte[]> getCustomUserInfoString() {
        return customUserInfoString;
    }

    protected void addCustomUserInfoBytes(String key, byte[] value) {
        this.customUserInfoString.put(key, value);
    }

    public Map<String, Long> getCustomUserInfoNumber() {
        return customUserInfoNumber;
    }

    protected void addCustomUserInfoNumber(String key, long value) {
        this.customUserInfoNumber.put(key, value);
    }
}
