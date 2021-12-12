package com.lucas.ximdemo.xim.ximcore.protocal.response;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;
import com.lucas.ximdemo.xim.ximcore.session.Session;

import lombok.Data;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.GROUP_MESSAGE_RESPONSE;


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
