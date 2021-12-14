package com.lucas.xim.ximcore.client.console;

import com.lucas.xim.ximcore.protocal.request.LoginRequestPacket;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Scanner;

public class LoginConsoleCommand {

    public void exec(String mobile,String pwd, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setUsername(mobile);
        loginRequestPacket.setPassword(pwd);

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
