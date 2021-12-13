package com.lucas.xim.ximcore.attribute;

import com.lucas.xim.ximcore.session.Session;

import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}


