package com.lucas.xim.ximcore.client.console;

import com.lucas.xim.common.IMLog;
import com.lucas.xim.message.IMMsg;
import com.lucas.xim.ximcore.protocal.request.MessageRequestPacket;

import io.netty.channel.Channel;

public class SendToUserConsoleCommand {
    public static  String TAG =  SendToUserConsoleCommand.class.getSimpleName();
    public void exec(Channel channel, IMMsg message) {
        IMLog.d(TAG, "发送消息给用户：" + message.getReceiver() );
        channel.writeAndFlush(new MessageRequestPacket(message));
    }
}
