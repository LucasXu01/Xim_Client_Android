package com.lucas.xim;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:27 下午
 */

import android.content.Context;
import android.util.Log;

import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.conversation.ConversationManager;
import com.lucas.xim.message.MessageCenter;
import com.lucas.xim.v1.IMValueCallback;
import com.lucas.xim.v1.UserInfo;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.config.IMOptions;
import com.lucas.xim.v1.IMContext;
import com.lucas.xim.common.IMLog;
import com.lucas.xim.ximcore.client.console.GetOnlineMembersConsoleCommand;
import com.lucas.xim.ximcore.client.console.LoginConsoleCommand;
import com.lucas.xim.ximcore.client.console.SendToGroupConsoleCommand;
import com.lucas.xim.ximcore.client.console.SendToUserConsoleCommand;
import com.lucas.xim.ximcore.client.handler.CreateGroupResponseHandler;
import com.lucas.xim.ximcore.client.handler.GetOnlineMembersResponseHandler;
import com.lucas.xim.ximcore.client.handler.GroupMessageResponseHandler;
import com.lucas.xim.ximcore.client.handler.HeartBeatTimerHandler;
import com.lucas.xim.ximcore.client.handler.JoinGroupResponseHandler;
import com.lucas.xim.ximcore.client.handler.ListGroupMembersResponseHandler;
import com.lucas.xim.ximcore.client.handler.LoginResponseHandler;
import com.lucas.xim.ximcore.client.handler.LogoutResponseHandler;
import com.lucas.xim.ximcore.client.handler.MessageResponseHandler;
import com.lucas.xim.ximcore.client.handler.QuitGroupResponseHandler;
import com.lucas.xim.ximcore.codec.PacketDecoder;
import com.lucas.xim.ximcore.codec.PacketEncoder;
import com.lucas.xim.ximcore.codec.Spliter;
import com.lucas.xim.ximcore.handler.IMIdleStateHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.lucas.xim.BaseConstants.ERROR_INIT_MAX_TRY;


public class BaseManager {
    private static final String TAG = BaseManager.class.getSimpleName();

    public Channel channel; // 和客户端的连接channel，客户端只有一个，服务端需要池化
    List<Integer> mTUIComponentList = new ArrayList<>();
    private HashMap<Integer, Integer> mTUIComponentCheckCountMap = new HashMap<>();
    private WeakReference<SDKListener> sdkListenerWeakReference;
    private WeakReference<IMCallback<List<String>>> getOnlineMembersCallback;
    private WeakReference<IMCallback> loginCallback;
    public boolean isInit = false;
    public boolean isLogin = false;

    public String myUid = "";




    private static class BaseManagerHolder {
        private static final BaseManager baseManager = new BaseManager();
    }

    public static BaseManager getInstance() {
        return BaseManager.BaseManagerHolder.baseManager;
    }

    public Channel getChannel() {
        if (channel == null) {
            IMLog.e(TAG, "channel为null！无与服务器的链接channel");
        }
        return channel;
    }

    public boolean initSDK(Context context, IMOptions options, SDKListener listener) {

        if (null == context) {
            IMLog.e(TAG, "null context");
            return false;
        }

        if (isInit) {
            IMLog.w(TAG, "Has initSDK");
            return true;
        }

        IMContext.getInstance().init(context.getApplicationContext());

        MessageCenter.getInstance().init();
//        GroupManager.getInstance().init();
        ConversationManager.getInstance().init();
//        RelationshipManager.getInstance().init();

//        sdkConfig.sdkInitPath = SystemUtil.getSDKInitPath();
//        sdkConfig.sdkInstanceType = SystemUtil.getInstanceType();
//        sdkConfig.testEnvironment = isTestEnvironment;
//        sdkConfig.deviceInfo.deviceType = SystemUtil.getDeviceType();
//        sdkConfig.deviceInfo.deviceId = SystemUtil.getDeviceID();
//        sdkConfig.deviceInfo.deviceBrand = SystemUtil.getInstanceType();
//        sdkConfig.deviceInfo.systemVersion = SystemUtil.getSystemVersion();
//        sdkConfig.networkInfo.networkType = NetworkInfoCenter.getInstance().getNetworkType();
//        sdkConfig.networkInfo.ipType = NetworkInfoCenter.getInstance().getIPType();
//        sdkConfig.networkInfo.networkId = NetworkInfoCenter.getInstance().getNetworkID();
//        sdkConfig.networkInfo.networkConnected = NetworkInfoCenter.getInstance().isNetworkConnected();
//        sdkConfig.logSetting.enableConsoleLog = true;
//        sdkConfig.logSetting.logFilePath = SystemUtil.getSDKLogPath();
//        sdkConfig.uiPlatform = getUIPlatform();
//
//        mLastNetworkInfo = sdkConfig.networkInfo;

        nativeInitSDK(options, listener);

        sdkListenerWeakReference = new WeakReference<>(listener);

        isInit = true;
        return true;
    }


    public void unInitSDK() {
        nativeUninitSDK();
        isInit = false;
        mTUIComponentList.clear();
        mTUIComponentCheckCountMap.clear();
    }


    // TODO: 2021/12/13 获取服务器时间  这里暂时用本地时间代替 
    public long getServerTime() {
        return System.currentTimeMillis();
    }

    public void getOnlineMembers(IMCallback<List<String>> callback) {
        IMLog.d(TAG, "getOnlineMembers()");
        getOnlineMembersCallback = new WeakReference<>(callback);
        GetOnlineMembersConsoleCommand getOnlineMembersConsoleCommand = new GetOnlineMembersConsoleCommand();
        getOnlineMembersConsoleCommand.exec(getChannel());
    }


    public void login(final String uid, final String token, IMCallback callback) {
        if (!isInit) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        myUid = uid;
        loginCallback = new WeakReference<>(callback);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        loginConsoleCommand.exec(uid, token, getChannel());
    }

    public void logout(final IMCallback callback) {
        if (!isInit) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeLogout(callback);
    }


    public String getLoginUser() {
        if (!isInit) {
            Log.e(TAG, "sdk not init");
            return null;
        }
        return nativeGetLoginUser();
    }

    public int getLoginStatus() {
        if (!isInit) {
            Log.e(TAG, "sdk not init");
            return 0;
        }
        return nativeGetLoginStatus();
    }

    public String getVersion() {
        if (!isInit) {
            Log.e(TAG, "sdk not init");
            return null;
        }
        return nativeGetSDKVersion();
    }

    public boolean isInited() {
        return isInit;
    }

    public long getClockTickInHz() {
        if (!isInit) {
            Log.e(TAG, "sdk not init");
            return 0;
        }
        return nativeGetClockTickInHz();
    }

    public long getTimeTick() {
        if (!isInit) {
            Log.e(TAG, "sdk not init");
            return 0;
        }
        return nativeGetTimeTick();
    }


    public void onRecMsg(String from_uid, String msg) {
    }


    public void notifySelfInfoUpdated(UserInfo selfInfo) {
        if (sdkListenerWeakReference != null) {
            SDKListener listener = sdkListenerWeakReference.get();
            if (listener != null) {
                listener.onSelfInfoUpdated(selfInfo);
            }
        }
    }

    public void notifyGetMemberOnline(List<String> userList) {
        if (getOnlineMembersCallback != null) {
            IMCallback<List<String>> imCallback = getOnlineMembersCallback.get();
            if (imCallback != null) {
                imCallback.success(userList);
            }
        }
    }

    /**
     * 登陆成功的回调
     * @param secLogin
     */
    public void notifyLogin(int secLogin) {
        isLogin = true;
        if (loginCallback != null) {
            IMCallback imCallback = loginCallback.get();
            if (imCallback != null) {
                imCallback.success(secLogin);
            }
        }
    }



    private void connect(Bootstrap bootstrap, String host, int port, int retry, int maxRetry, SDKListener sdkListener) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                IMLog.d(TAG, new Date() + ": 连接成功，启动控制台线程……");
                if (sdkListener != null) {
                    sdkListener.onConnectSuccess();
                }
                channel = ((ChannelFuture) future).channel();
            } else if (retry == 0) {
                IMLog.d(TAG, "重试次数已用完，放弃连接！");
                if (sdkListener != null) {
                    sdkListener.onConnectFailed(ERROR_INIT_MAX_TRY, "xim connect 连接失败！重试次数已用完，放弃连接！");
                }
            } else {
                // 第几次重连
                int order = (maxRetry - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                IMLog.d(TAG, new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1, maxRetry, sdkListener), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void nativeInitSDK(IMOptions imOptions, SDKListener sdkListener) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        // 粘包
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录响应处理器
                        ch.pipeline().addLast(new LoginResponseHandler());
                        // my 获取群在线人数
                        ch.pipeline().addLast(new GetOnlineMembersResponseHandler());
                        // 收消息处理器
                        ch.pipeline().addLast(new MessageResponseHandler());
                        // 创建群响应处理器
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        // 加群响应处理器
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        // 退群响应处理器
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        // 获取群成员响应处理器
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // 群消息响应
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        // 登出响应处理器
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });
        connect(bootstrap, imOptions.getHost(), imOptions.getPort(), imOptions.getMAX_RETRY(), imOptions.getMAX_RETRY(), sdkListener);

    }

    protected native void nativeUninitSDK();



    public void send2Group(String uid, String msg) {
        IMLog.d(TAG, "send2User()");
        SendToGroupConsoleCommand sendToGroupConsoleCommand = new SendToGroupConsoleCommand();
        sendToGroupConsoleCommand.exec(getChannel(), uid, msg);
    }

    protected native void nativeLogout(IMCallback callBack);

    protected native String nativeGetLoginUser();

    protected native int nativeGetLoginStatus();

    protected native String nativeGetSDKVersion();

    protected native long nativeGetClockTickInHz();

    protected native long nativeGetTimeTick();


}