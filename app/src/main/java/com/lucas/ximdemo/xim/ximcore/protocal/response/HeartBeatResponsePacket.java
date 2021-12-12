package com.lucas.ximdemo.xim.ximcore.protocal.response;


import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
