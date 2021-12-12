package com.lucas.ximdemo.xim.ximcore.protocal.request;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;

import lombok.Data;

import java.util.List;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.CREATE_GROUP_REQUEST;


@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
