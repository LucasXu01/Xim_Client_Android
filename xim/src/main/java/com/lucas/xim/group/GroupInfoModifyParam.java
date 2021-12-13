package com.lucas.xim.group;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:12 下午
 */
import java.io.Serializable;

public class GroupInfoModifyParam implements Serializable {
    private long modifyFlag;
    private GroupInfo groupInfo;

    public long getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(long modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}

