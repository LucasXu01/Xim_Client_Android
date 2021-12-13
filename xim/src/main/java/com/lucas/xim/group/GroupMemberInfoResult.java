package com.lucas.xim.group;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:12 下午
 */

import com.lucas.xim.v1.GroupMemberInfo;

import java.io.Serializable;
import java.util.List;

public class GroupMemberInfoResult implements Serializable {
    private long nextSeq;
    private List<GroupMemberInfo> groupMemberInfoList;

    public long getNextSeq() {
        return nextSeq;
    }

    public void setNextSeq(long nextSeq) {
        this.nextSeq = nextSeq;
    }

    public List<GroupMemberInfo> getGroupMemberInfoList() {
        return groupMemberInfoList;
    }

    public void setGroupMemberInfoList(List<GroupMemberInfo> groupMemberInfoList) {
        this.groupMemberInfoList = groupMemberInfoList;
    }
}
