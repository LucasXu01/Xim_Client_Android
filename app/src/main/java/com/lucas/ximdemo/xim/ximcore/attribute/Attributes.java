package com.lucas.ximdemo.xim.ximcore.attribute;

import com.lucas.ximdemo.xim.ximcore.session.Session;

import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}


