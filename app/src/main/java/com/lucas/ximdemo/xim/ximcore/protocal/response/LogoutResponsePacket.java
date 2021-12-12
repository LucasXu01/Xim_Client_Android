package com.lucas.ximdemo.xim.ximcore.protocal.response;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.LOGOUT_RESPONSE;


@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
