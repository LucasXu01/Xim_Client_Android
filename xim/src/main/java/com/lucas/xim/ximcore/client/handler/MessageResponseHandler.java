package com.lucas.xim.ximcore.client.handler;

import com.lucas.xim.common.IMLog;
import com.lucas.xim.ximcore.protocal.response.MessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    public static String TAG = MessageResponseHandler.class.getSimpleName();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();

        IMLog.d(TAG, fromUserId + ":" + fromUserName + " -> " + messageResponsePacket
                .getMessage());

//        XimDispatcher.getInstance().getOnXImMsgListener().onRevMsg(fromUserId, messageResponsePacket
//                .getMessage());

    }
}
