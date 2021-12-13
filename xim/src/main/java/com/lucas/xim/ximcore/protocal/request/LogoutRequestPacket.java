package com.lucas.xim.ximcore.protocal.request;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.xim.ximcore.protocal.command.Command.LOGOUT_REQUEST;


@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
