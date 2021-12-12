package com.lucas.ximdemo.xim.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  2:14 下午
 */
public class CustomServerInfo {

    public List<ServerAddress> longconnectionAddressList = new ArrayList<>();
    public List<ServerAddress> shortconnectionAddressList = new ArrayList<>();
    public String serverPublicKey = "";

    public static class ServerAddress {
        public String ip = "";
        public int port = 0;
        public boolean isIPv6 = false;
    }
}