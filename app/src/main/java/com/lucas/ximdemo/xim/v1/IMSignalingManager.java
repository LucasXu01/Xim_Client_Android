package com.lucas.ximdemo.xim.v1;

import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:26 下午
 */
public abstract class IMSignalingManager {
    public IMSignalingManager() {
    }

    static IMSignalingManager getInstance() {
        return V2TIMSignalingManagerImpl.getInstance();
    }

    public abstract void addSignalingListener(V2TIMSignalingListener var1);

    public abstract void removeSignalingListener(V2TIMSignalingListener var1);

    public abstract String invite(String var1, String var2, boolean var3, V2TIMOfflinePushInfo var4, int var5, V2TIMCallback var6);

    public abstract String inviteInGroup(String var1, List<String> var2, String var3, boolean var4, int var5, V2TIMCallback var6);

    public abstract void cancel(String var1, String var2, V2TIMCallback var3);

    public abstract void accept(String var1, String var2, V2TIMCallback var3);

    public abstract void reject(String var1, String var2, V2TIMCallback var3);

    public abstract V2TIMSignalingInfo getSignalingInfo(V2TIMMessage var1);

    public abstract void addInvitedSignaling(V2TIMSignalingInfo var1, V2TIMCallback var2);
}

