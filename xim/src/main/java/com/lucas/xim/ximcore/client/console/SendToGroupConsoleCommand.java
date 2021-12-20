package com.lucas.xim.ximcore.client.console;

import com.lucas.xim.ximcore.protocal.request.GroupMessageRequestPacket;

import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand {

    public void exec(Channel channel, String uid, String msg) {
        System.out.print("发送消息给某个某个群组：");

//        String toGroupId = scanner.next();
//        String message = scanner.next();
//        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));

    }
}
