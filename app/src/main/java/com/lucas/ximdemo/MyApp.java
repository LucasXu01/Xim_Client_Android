package com.lucas.ximdemo;

import android.app.Application;

import com.lucas.ximdemo.xim.config.CommunicationProtocol;
import com.lucas.ximdemo.xim.config.IMOptions;
import com.lucas.ximdemo.xim.config.ImplementationMode;
import com.lucas.ximdemo.xim.config.TransportProtocol;
import com.lucas.ximdemo.xim.v1.IMManager;
import com.lucas.ximdemo.xim.v1.IMSDKListener;
import com.lucas.ximdemo.xim.v1.IMUserFullInfo;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  5:44 下午
 */
class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

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
                .setHost("127.0.0.1")
                .setMAX_RETRY(5)
                .setImplementationMode(ImplementationMode.Netty)
                .setCommunicationProtocol(CommunicationProtocol.TCP)
                .setTransportProtocol(TransportProtocol.Json)
                .build();
        Boolean isInitSec = IMManager.getInstance().initSDK(this, options, 123456);

    }
}
