package com.lucas.xim.ximcore.client.handler;

import com.lucas.xim.BaseManager;
import com.lucas.xim.common.IMLog;
import com.lucas.xim.ximcore.protocal.response.LoginResponsePacket;
import com.lucas.xim.ximcore.session.Session;
import com.lucas.xim.ximcore.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.lucas.xim.BaseConstants.SEC_LOGIN;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    private static final String TAG = LoginResponseHandler.class.getSimpleName();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        String userId = loginResponsePacket.getUserId();
        String mobile = loginResponsePacket.getMobile();

        if (loginResponsePacket.isSuccess()) {
            IMLog.d(TAG,"[" + mobile + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId, mobile), ctx.channel());
            BaseManager.getInstance().notifyLogin(SEC_LOGIN);
        } else {
            IMLog.d(TAG,"[" + mobile + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
