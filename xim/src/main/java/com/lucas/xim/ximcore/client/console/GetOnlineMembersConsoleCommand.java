package com.lucas.xim.ximcore.client.console;

import com.lucas.xim.common.IMLog;
import com.lucas.xim.ximcore.protocal.request.CreateGroupRequestPacket;
import com.lucas.xim.ximcore.protocal.request.GetOnlineMembersRequestPacket;
import com.lucas.xim.ximcore.protocal.request.LoginRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

import io.netty.channel.Channel;

public class GetOnlineMembersConsoleCommand {
    public static final String TAG = GetOnlineMembersConsoleCommand.class.getSimpleName();

    public void exec(Channel channel) {
        GetOnlineMembersRequestPacket getOnlineMembersRequestPacket = new GetOnlineMembersRequestPacket();
        // 发送登录数据包
        IMLog.d(TAG,"发送登录数据包()");
        channel.writeAndFlush(getOnlineMembersRequestPacket);

    }

}
