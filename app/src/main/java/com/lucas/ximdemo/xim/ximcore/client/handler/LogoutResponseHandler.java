package com.lucas.ximdemo.xim.ximcore.client.handler;

import com.lucas.xim.ximcore.nettyimcore.protocal.response.LogoutResponsePacket;
import com.lucas.xim.ximcore.nettyimcore.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
