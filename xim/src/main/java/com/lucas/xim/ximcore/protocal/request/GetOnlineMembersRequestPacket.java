package com.lucas.xim.ximcore.protocal.request;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import java.util.List;

import static com.lucas.xim.ximcore.protocal.command.Command.GET_ONLINE_MEMBERS_REQUEST;


@Data
public class GetOnlineMembersRequestPacket extends Packet {

    @Override
    public Byte getCommand() {

        return GET_ONLINE_MEMBERS_REQUEST;
    }
}
