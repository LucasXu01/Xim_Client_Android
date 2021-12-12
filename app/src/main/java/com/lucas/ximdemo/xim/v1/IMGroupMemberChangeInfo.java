package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:15 下午
 */
import java.io.Serializable;

public class IMGroupMemberChangeInfo implements Serializable {
    private GroupMemberInfoChangeItem groupMemberInfoChangeItem = new GroupMemberInfoChangeItem();

    public IMGroupMemberChangeInfo() {
    }

    public String getUserID() {
        return this.groupMemberInfoChangeItem.getUserID();
    }

    public long getMuteTime() {
        return this.groupMemberInfoChangeItem.getShutUpTime();
    }

    GroupMemberInfoChangeItem getGroupMemberInfoChangeItem() {
        return this.groupMemberInfoChangeItem;
    }

    void setGroupMemberInfoChangeItem(GroupMemberInfoChangeItem groupMemberInfoChangeItem) {
        this.groupMemberInfoChangeItem = groupMemberInfoChangeItem;
    }
}