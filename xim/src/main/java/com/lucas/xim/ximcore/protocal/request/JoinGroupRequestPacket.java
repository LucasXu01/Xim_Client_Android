package com.lucas.xim.ximcore.protocal.request;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.JOIN_GROUP_REQUEST;


@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_REQUEST;
    }
}
