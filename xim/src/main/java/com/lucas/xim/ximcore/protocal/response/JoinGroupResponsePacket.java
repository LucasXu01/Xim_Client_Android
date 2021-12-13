package com.lucas.xim.ximcore.protocal.response;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.JOIN_GROUP_RESPONSE;


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
