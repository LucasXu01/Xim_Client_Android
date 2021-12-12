package com.lucas.ximdemo.xim.ximcore.codec;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;
import com.lucas.ximdemo.xim.ximcore.protocal.PacketCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodec.INSTANCE.encode(out, packet);
    }
}
