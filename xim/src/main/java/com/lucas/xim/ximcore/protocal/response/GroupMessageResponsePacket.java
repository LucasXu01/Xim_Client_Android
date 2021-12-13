package com.lucas.xim.ximcore.protocal.response;

import com.lucas.xim.ximcore.protocal.Packet;
import com.lucas.xim.ximcore.session.Session;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.GROUP_MESSAGE_RESPONSE;


@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
