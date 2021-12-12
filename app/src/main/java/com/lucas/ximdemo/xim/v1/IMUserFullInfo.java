package com.lucas.ximdemo.xim.v1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:48 上午
 */
public class IMUserFullInfo extends IMUserInfo {
    public static final String PROFILE_TYPE_KEY_NICK = "Tag_Profile_IM_Nick";
    public static final String PROFILE_TYPE_KEY_FACEURL = "Tag_Profile_IM_Image";
    public static final String PROFILE_TYPE_KEY_ALLOWTYPE = "Tag_Profile_IM_AllowType";
    public static final String PROFILE_TYPE_KEY_GENDER = "Tag_Profile_IM_Gender";
    public static final String PROFILE_TYPE_KEY_SELF_SIGNATURE = "Tag_Profile_IM_SelfSignature";
    public static final String PROFILE_TYPE_KEY_CUSTOM_PREFIX = "Tag_Profile_Custom_";
    public static final int V2TIM_FRIEND_ALLOW_ANY = 0;
    public static final int V2TIM_FRIEND_NEED_CONFIRM = 1;
    public static final int V2TIM_FRIEND_DENY_ANY = 2;
    public static final int V2TIM_GENDER_UNKNOWN = 0;
    public static final int V2TIM_GENDER_MALE = 1;
    public static final int V2TIM_GENDER_FEMALE = 2;

    public V2TIMUserFullInfo() {
    }

    public void setNickname(String nickname) {
        super.setNickName(nickname);
    }

    public void setFaceUrl(String faceUrl) {
        super.setFaceUrl(faceUrl);
    }

    public String getSelfSignature() {
        return this.userInfo.getSignature();
    }

    public void setSelfSignature(String selfSignature) {
        this.userInfo.setSignature(selfSignature);
        this.modifyParams.put("Tag_Profile_IM_SelfSignature", selfSignature);
    }

    public int getGender() {
        int gender = this.userInfo.getGender();
        if (gender == 1) {
            return 1;
        } else {
            return gender == 2 ? 2 : 0;
        }
    }

    public void setGender(int gender) {
        if (1 == gender) {
            this.userInfo.setGender(1);
            this.modifyParams.put("Tag_Profile_IM_Gender", "Gender_Type_Male");
        } else if (2 == gender) {
            this.userInfo.setGender(2);
            this.modifyParams.put("Tag_Profile_IM_Gender", "Gender_Type_Female");
        } else {
            this.userInfo.setGender(0);
            this.modifyParams.put("Tag_Profile_IM_Gender", "Gender_Type_Unknown");
        }

    }

    public int getRole() {
        return this.userInfo.getRole();
    }

    public void setRole(int role) {
        this.userInfo.setRole(role);
        this.modifyParams.put("Tag_Profile_IM_Role", role);
    }

    public int getLevel() {
        return this.userInfo.getLevel();
    }

    public void setLevel(int level) {
        this.userInfo.setLevel(level);
        this.modifyParams.put("Tag_Profile_IM_Level", level);
    }

    public int getAllowType() {
        int type = this.userInfo.getAllowType();
        if (type == 2) {
            return 2;
        } else if (type == 1) {
            return 1;
        } else {
            return type == 0 ? 0 : 1;
        }
    }

    public void setAllowType(int allowType) {
        if (0 == allowType) {
            this.userInfo.setAllowType(0);
            this.modifyParams.put("Tag_Profile_IM_AllowType", "AllowType_Type_AllowAny");
        } else if (2 == allowType) {
            this.userInfo.setAllowType(2);
            this.modifyParams.put("Tag_Profile_IM_AllowType", "AllowType_Type_DenyAny");
        } else if (1 == allowType) {
            this.userInfo.setAllowType(1);
            this.modifyParams.put("Tag_Profile_IM_AllowType", "AllowType_Type_NeedConfirm");
        } else {
            this.userInfo.setAllowType(1);
            this.modifyParams.put("Tag_Profile_IM_AllowType", "AllowType_Type_NeedConfirm");
        }

    }

    public void setCustomInfo(HashMap<String, byte[]> customHashMap) {
        if (customHashMap != null && customHashMap.size() != 0) {
            Iterator iter = customHashMap.entrySet().iterator();

            while(iter.hasNext()) {
                Map.Entry<String, byte[]> entry = (Map.Entry)iter.next();
                if (((String)entry.getKey()).contains("Tag_Profile_Custom_")) {
                    this.modifyParams.put(entry.getKey(), entry.getValue());
                } else {
                    this.modifyParams.put("Tag_Profile_Custom_" + (String)entry.getKey(), new String((byte[])entry.getValue()));
                }
            }

        }
    }

    public HashMap<String, byte[]> getCustomInfo() {
        return this.userInfo.getCustomUserInfoString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("V2TIMUserFullInfo--->");
        Map<String, byte[]> userCustomMap = this.getCustomInfo();
        StringBuilder userCustomInfoStringBuild = new StringBuilder();
        if (userCustomMap != null) {
            userCustomInfoStringBuild.append("\n");
            Iterator iter = userCustomMap.entrySet().iterator();

            while(iter.hasNext()) {
                Map.Entry<String, byte[]> entry = (Map.Entry)iter.next();
                userCustomInfoStringBuild.append(" |key:" + (String)entry.getKey() + ", value:" + new String((byte[])entry.getValue())).append("\n");
            }
        }

        stringBuilder.append("userID:").append(this.getUserID()).append(", nickName:").append(this.getNickName()).append(", gender:").append(this.getGender()).append(", faceUrl:").append(this.getFaceUrl()).append(", selfSignature:").append(this.getSelfSignature()).append(", allowType:").append(this.getAllowType()).append(", customInfo:").append(userCustomInfoStringBuild.toString());
        return stringBuilder.toString();
    }
}
