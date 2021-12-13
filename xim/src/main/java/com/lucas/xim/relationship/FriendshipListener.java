package com.lucas.xim.relationship;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:21 下午
 */
import com.lucas.xim.v1.UserInfo;

import java.util.List;

public abstract class FriendshipListener {
    public void OnSelfInfoUpdated(UserInfo userInfo) {}

    public void OnFriendInfoChanged(List<FriendInfo> friendInfoList) {}

    public void OnFriendListAdded(List<FriendInfo> friendInfoList) {}

    public void OnFriendListDeleted(List<String> userIDList) {}

    public void OnBlackListAdded(List<FriendInfo> friendInfoList) {}

    public void OnBlackListDeleted(List<String> userIDList) {}

    public void OnFriendApplicationListAdded(List<FriendApplication> applicationList) {}

    public void OnFriendApplicationListDelete(List<String> userIDList) {}

    public void OnFriendApplicationListRead() {}
}

