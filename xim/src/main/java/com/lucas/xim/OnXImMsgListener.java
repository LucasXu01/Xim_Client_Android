package com.lucas.xim;


import com.lucas.xim.v1.UserInfo;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/17  16:19 下午
 */

public interface OnXImMsgListener {
    public void onRevMsg(String uid, String msg);
}

