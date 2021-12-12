package com.lucas.ximdemo.xim.v1;

import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:17 下午
 */
public abstract class IMFriendshipManager {
    public IMFriendshipManager() {
    }

    static IMFriendshipManager getInstance() {
        return IMFriendshipManagerImpl.getInstance();
    }

    /** @deprecated */
    @Deprecated
    public abstract void setFriendListener(V2TIMFriendshipListener var1);

    public abstract void addFriendListener(V2TIMFriendshipListener var1);

    public abstract void removeFriendListener(V2TIMFriendshipListener var1);

    public abstract void getFriendList(V2TIMValueCallback<List<V2TIMFriendInfo>> var1);

    public abstract void getFriendsInfo(List<String> var1, V2TIMValueCallback<List<V2TIMFriendInfoResult>> var2);

    public abstract void setFriendInfo(V2TIMFriendInfo var1, V2TIMCallback var2);

    public abstract void searchFriends(V2TIMFriendSearchParam var1, V2TIMValueCallback<List<V2TIMFriendInfoResult>> var2);

    public abstract void addFriend(V2TIMFriendAddApplication var1, V2TIMValueCallback<V2TIMFriendOperationResult> var2);

    public abstract void deleteFromFriendList(List<String> var1, int var2, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var3);

    public abstract void checkFriend(List<String> var1, int var2, V2TIMValueCallback<List<V2TIMFriendCheckResult>> var3);

    public abstract void getFriendApplicationList(V2TIMValueCallback<V2TIMFriendApplicationResult> var1);

    public abstract void acceptFriendApplication(V2TIMFriendApplication var1, int var2, V2TIMValueCallback<V2TIMFriendOperationResult> var3);

    public abstract void refuseFriendApplication(V2TIMFriendApplication var1, V2TIMValueCallback<V2TIMFriendOperationResult> var2);

    public abstract void deleteFriendApplication(V2TIMFriendApplication var1, V2TIMCallback var2);

    public abstract void setFriendApplicationRead(V2TIMCallback var1);

    public abstract void addToBlackList(List<String> var1, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var2);

    public abstract void deleteFromBlackList(List<String> var1, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var2);

    public abstract void getBlackList(V2TIMValueCallback<List<V2TIMFriendInfo>> var1);

    public abstract void createFriendGroup(String var1, List<String> var2, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var3);

    public abstract void getFriendGroups(List<String> var1, V2TIMValueCallback<List<V2TIMFriendGroup>> var2);

    public abstract void deleteFriendGroup(List<String> var1, V2TIMCallback var2);

    public abstract void renameFriendGroup(String var1, String var2, V2TIMCallback var3);

    public abstract void addFriendsToFriendGroup(String var1, List<String> var2, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var3);

    public abstract void deleteFriendsFromFriendGroup(String var1, List<String> var2, V2TIMValueCallback<List<V2TIMFriendOperationResult>> var3);
}

