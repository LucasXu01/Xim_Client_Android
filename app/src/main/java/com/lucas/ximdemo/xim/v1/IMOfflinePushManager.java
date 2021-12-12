package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:15 下午
 */
public abstract class IMOfflinePushManager {
    public IMOfflinePushManager() {
    }

    static IMOfflinePushManager getInstance() {
        return V2TIMOfflinePushManagerImpl.getInstance();
    }

    public abstract void setOfflinePushConfig(V2TIMOfflinePushConfig var1, V2TIMCallback var2);

    public abstract void doBackground(int var1, V2TIMCallback var2);

    public abstract void doForeground(V2TIMCallback var1);
}
