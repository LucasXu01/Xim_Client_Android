package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:15 下午
 */
import java.io.Serializable;

public class IMGroupChangeInfo implements Serializable {
    public static final int V2TIM_GROUP_INFO_INVALID = 0;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_NAME = 1;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_INTRODUCTION = 2;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_NOTIFICATION = 3;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_FACE_URL = 4;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_OWNER = 5;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_CUSTOM = 6;
    public static final int V2TIM_GROUP_INFO_CHANGE_TYPE_SHUT_UP_ALL = 8;
    private GroupInfoChangeItem groupInfoChangeItem = new GroupInfoChangeItem();

    public IMGroupChangeInfo() {
    }

    public int getType() {
        return this.groupInfoChangeItem.getGroupInfoChangeType();
    }

    public String getValue() {
        return this.groupInfoChangeItem.getValueChanged();
    }

    public boolean getBoolValue() {
        return this.groupInfoChangeItem.getBoolValueChanged();
    }

    public String getKey() {
        return this.groupInfoChangeItem.getCustomInfoKey();
    }

    void setGroupInfoChangeItem(GroupInfoChangeItem groupInfoChangeItem) {
        this.groupInfoChangeItem = groupInfoChangeItem;
    }

    GroupInfoChangeItem getGroupInfoChangeItem() {
        return this.groupInfoChangeItem;
    }
}

