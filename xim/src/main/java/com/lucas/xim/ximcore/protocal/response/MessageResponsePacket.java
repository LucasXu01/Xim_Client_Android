package com.lucas.xim.ximcore.protocal.response;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.MESSAGE_RESPONSE;


@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
