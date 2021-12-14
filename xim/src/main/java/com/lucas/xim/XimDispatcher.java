package com.lucas.xim;

import com.lucas.xim.ximcore.client.console.ConsoleCommandManager;
import com.lucas.xim.ximcore.client.console.LoginConsoleCommand;
import com.lucas.xim.ximcore.util.SessionUtil;

import java.util.Scanner;

import io.netty.channel.Channel;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/14  3:39 下午
 */
public class XimDispatcher {

    public Channel channel;
    public static final XimDispatcher ourInstance = new XimDispatcher();

    public static XimDispatcher getInstance() {
        return ourInstance;
    }

    public XimDispatcher() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
