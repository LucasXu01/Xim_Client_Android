package com.lucas.ximdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lucas.xim.config.CommunicationProtocol;
import com.lucas.xim.config.IMOptions;
import com.lucas.xim.config.ImplementationMode;
import com.lucas.xim.config.TransportProtocol;
import com.lucas.xim.v1.IMManager;
import com.lucas.xim.v1.IMSDKListener;
import com.lucas.xim.v1.IMUserFullInfo;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:44 下午
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);

        IMManager.getInstance().addIMSDKListener(new IMSDKListener() {
            @Override
            public void onConnecting() {

            }

            @Override
            public void onConnectSuccess() {

            }

            @Override
            public void onConnectFailed(int code, String error) {

            }

            @Override
            public void onKickedOffline() {
                // TODO: 2021/12/10 被踢下线
            }

            @Override
            public void onUserSigExpired() {

            }

            @Override
            public void onSelfInfoUpdated(IMUserFullInfo info) {

            }
        });

        IMOptions options = new IMOptions.Builder()
                .setHost("192.168.1.7")
                .setMAX_RETRY(5)
                .setImplementationMode(ImplementationMode.Netty)
                .setCommunicationProtocol(CommunicationProtocol.TCP)
                .setTransportProtocol(TransportProtocol.Json)
                .build();
        Boolean isInitSec = IMManager.getInstance().initSDK(this, options, 123456);

    }
}
