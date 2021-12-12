package com.lucas.ximdemo.xim.ximcore.protocal.request;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.GROUP_MESSAGE_REQUEST;


@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
