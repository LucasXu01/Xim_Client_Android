package com.lucas.xim.ximcore.protocal.response;

import com.lucas.xim.ximcore.protocal.Packet;

import lombok.Data;

import java.util.List;
import java.util.Set;

import static com.lucas.xim.ximcore.protocal.command.Command.GET_ONLINE_MEMBERS_RESPONSE;


@Data
public class GetOnlineMembersResponsePacket extends Packet {
    private boolean success;

    private Set<String> uidSet;

    @Override
    public Byte getCommand() {

        return GET_ONLINE_MEMBERS_RESPONSE;
    }
}
