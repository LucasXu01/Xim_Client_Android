package com.lucas.xim.ximcore.protocal.response;


import com.lucas.xim.ximcore.protocal.Packet;

import static com.lucas.xim.ximcore.protocal.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
