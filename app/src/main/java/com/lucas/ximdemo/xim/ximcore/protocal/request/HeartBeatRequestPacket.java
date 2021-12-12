package com.lucas.ximdemo.xim.ximcore.protocal.request;


import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
