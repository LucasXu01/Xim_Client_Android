package com.lucas.ximdemo.xim.ximcore.protocal.response;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import lombok.Data;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.LOGIN_RESPONSE;


@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
