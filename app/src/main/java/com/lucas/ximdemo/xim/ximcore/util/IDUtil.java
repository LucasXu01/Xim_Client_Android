package com.lucas.ximdemo.xim.ximcore.util;

import java.util.UUID;

public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
