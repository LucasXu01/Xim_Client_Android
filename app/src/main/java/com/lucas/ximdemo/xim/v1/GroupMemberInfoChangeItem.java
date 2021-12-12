package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  12:03 下午
 */
import java.io.Serializable;

public class GroupMemberInfoChangeItem implements Serializable {
    private String userID;
    private long shutUpTime;

    public String getUserID() {
        return userID;
    }

    public long getShutUpTime() {
        return shutUpTime;
    }
}

