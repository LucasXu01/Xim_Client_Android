package com.lucas.xim.ximcore.client.handler;

import com.lucas.xim.BaseManager;
import com.lucas.xim.common.IMLog;
import com.lucas.xim.ximcore.protocal.response.GetOnlineMembersResponsePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GetOnlineMembersResponseHandler extends SimpleChannelInboundHandler<GetOnlineMembersResponsePacket> {

    private static final String TAG = GetOnlineMembersResponseHandler.class.getSimpleName();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetOnlineMembersResponsePacket getOnlineMembersResponsePacket) {

        IMLog.d(TAG,"获取服务器在线人数成功！");

        Set<String> set = getOnlineMembersResponsePacket.getUidSet();
        List<String> set2List = new ArrayList<>();
        set2List.addAll(set);

        BaseManager.getInstance().notifyGetMemberOnline(set2List);
    }
}
