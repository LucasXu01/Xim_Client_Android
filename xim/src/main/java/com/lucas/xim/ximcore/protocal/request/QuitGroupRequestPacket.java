package com.lucas.xim.ximcore.protocal.request;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.QUIT_GROUP_REQUEST;


@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
