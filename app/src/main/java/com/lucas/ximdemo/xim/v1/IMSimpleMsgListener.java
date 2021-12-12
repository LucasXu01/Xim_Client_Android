package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  11:39 上午
 */
public abstract class IMSimpleMsgListener {
    public IMSimpleMsgListener() {
    }

    public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
    }

    public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
    }

    public void onRecvGroupTextMessage(String msgID, String groupID, IMGroupMemberInfo sender, String text) {
    }

    public void onRecvGroupCustomMessage(String msgID, String groupID, IMGroupMemberInfo sender, byte[] customData) {
    }
}
