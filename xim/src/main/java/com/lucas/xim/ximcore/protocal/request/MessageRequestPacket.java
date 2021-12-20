package com.lucas.xim.ximcore.protocal.request;

import com.lucas.xim.message.IMMsg;
import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lucas.xim.ximcore.protocal.command.Command.MESSAGE_REQUEST;


@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private IMMsg message;


    public MessageRequestPacket(IMMsg message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
