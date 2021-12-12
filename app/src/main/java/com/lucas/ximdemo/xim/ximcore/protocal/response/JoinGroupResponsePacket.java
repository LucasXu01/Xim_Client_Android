package com.lucas.ximdemo.xim.ximcore.protocal.response;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.JOIN_GROUP_RESPONSE;


@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}
