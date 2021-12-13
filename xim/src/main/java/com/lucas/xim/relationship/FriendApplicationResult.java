package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/13  11:31 上午
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendApplicationResult implements Serializable {
    private int unreadCount;
    private List<FriendApplication> friendApplicationList = new ArrayList<>();

    public int getUnreadCount() {
        return unreadCount;
    }

    public List<FriendApplication> getFriendApplicationList() {
        return friendApplicationList;
    }
}

