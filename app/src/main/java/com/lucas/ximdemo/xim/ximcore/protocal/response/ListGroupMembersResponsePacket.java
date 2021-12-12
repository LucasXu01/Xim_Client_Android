package com.lucas.ximdemo.xim.ximcore.protocal.response;

import com.lucas.ximdemo.xim.ximcore.protocal.Packet;
import com.lucas.ximdemo.xim.ximcore.session.Session;

import lombok.Data;

import java.util.List;

import static com.lucas.ximdemo.xim.ximcore.protocal.command.Command.LIST_GROUP_MEMBERS_RESPONSE;


@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
