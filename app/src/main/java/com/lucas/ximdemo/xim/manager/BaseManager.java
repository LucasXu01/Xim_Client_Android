package com.lucas.ximdemo.xim.manager;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  1:27 下午
 */

import android.content.Context;
import android.util.Log;


import com.lucas.ximdemo.xim.BaseConstants;
import com.lucas.ximdemo.xim.common.IMCallback;
import com.lucas.ximdemo.xim.config.IMOptions;
import com.lucas.ximdemo.xim.conversation.ConversationManager;
import com.lucas.ximdemo.xim.group.GroupManager;
import com.lucas.ximdemo.xim.message.MessageCenter;
import com.lucas.ximdemo.xim.relationship.RelationshipManager;
import com.lucas.ximdemo.xim.v1.UserInfo;
import com.lucas.ximdemo.xim.v1.XIMCallback;
import com.lucas.ximdemo.xim.v1.IMContext;
import com.lucas.ximdemo.xim.common.IMLog;
import com.lucas.ximdemo.xim.common.SystemUtil;
import com.lucas.ximdemo.xim.ximcore.client.console.ConsoleCommandManager;
import com.lucas.ximdemo.xim.ximcore.client.console.LoginConsoleCommand;
import com.lucas.ximdemo.xim.ximcore.client.handler.CreateGroupResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.GroupMessageResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.HeartBeatTimerHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.JoinGroupResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.ListGroupMembersResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.LoginResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.LogoutResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.MessageResponseHandler;
import com.lucas.ximdemo.xim.ximcore.client.handler.QuitGroupResponseHandler;
import com.lucas.ximdemo.xim.ximcore.codec.PacketDecoder;
import com.lucas.ximdemo.xim.ximcore.codec.PacketEncoder;
import com.lucas.ximdemo.xim.ximcore.codec.Spliter;
import com.lucas.ximdemo.xim.ximcore.handler.IMIdleStateHandler;
import com.lucas.ximdemo.xim.ximcore.util.SessionUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.lucas.ximdemo.xim.BaseConstants.ERROR_INIT_MAX_TRY;

public class BaseManager {
    private static final String TAG = BaseManager.class.getSimpleName();

    public static final int TUI_COMPONENT_UNKNOWN = 0;
    public static final int TUI_COMPONENT_CORE = 1;
    public static final int TUI_COMPONENT_CONVERSATION = 2;
    public static final int TUI_COMPONENT_CHAT = 3;
    public static final int TUI_COMPONENT_CONTACT = 4;
    public static final int TUI_COMPONENT_GROUP = 5;
    public static final int TUI_COMPONENT_SEARCH = 6;

    // 检查 TUI 组件的最大次数限制
    private static final int TUI_COMPONENT_CHECK_COUNT_LIMIT = 5;
    // 检查 TUI 组件的堆栈遍历深度
    private static final int TUI_COMPONENT_STACK_LAYER_LIMIT = 10;

    private boolean mInvokeFromTUIKit = false;
    private boolean mInvokeFromTUICore = false;

    List<Integer> mTUIComponentList = new ArrayList<>();
    private HashMap<Integer, Integer> mTUIComponentCheckCountMap = new HashMap<>();

    private SDKConfig.NetworkInfo mLastNetworkInfo = new SDKConfig.NetworkInfo();

    private WeakReference<SDKListener> sdkListenerWeakReference;

    private static boolean mLoadLibrarySuccess = false;

    static {
        try {
            mLoadLibrarySuccess = SystemUtil.loadIMLibrary();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private boolean isInit = false;
    private boolean isTestEnvironment = false;

    private static class BaseManagerHolder {
        private static final BaseManager baseManager = new BaseManager();
    }

    public static BaseManager getInstance() {
        return BaseManager.BaseManagerHolder.baseManager;
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
        GroupManager.getInstance().init();
        ConversationManager.getInstance().init();
        RelationshipManager.getInstance().init();

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
        isTestEnvironment = false;
        mLastNetworkInfo.clean();
        mInvokeFromTUIKit = false;
        mInvokeFromTUICore = false;
        mTUIComponentList.clear();
        mTUIComponentCheckCountMap.clear();
    }

    private String getUIPlatform() {
        mInvokeFromTUIKit = isTUIKit();
        boolean has_flutter = isFlutter();
        boolean has_unity = isUnity();
        if (has_flutter) {
            if (mInvokeFromTUIKit) {
                return "tuikit&flutter";
            } else {
                return "flutter";
            }
        } else if (has_unity) {
            if (mInvokeFromTUIKit) {
                return "tuikit&unity";
            } else {
                return "unity";
            }
        } else if (mInvokeFromTUIKit) {
            return "tuikit";
        }

        return "";
    }

    private boolean isTUIKit() {
        try {
            Class classTUIKit = Class.forName("com.tencent.qcloud.tim.uikit.TUIKit");
            if (classTUIKit != null) {
                return true;
            }
        } catch (Exception e) {
            // 不含 TUIKit
        }

        try {
            Class classTUICore = Class.forName("com.tencent.qcloud.tuicore.TUICore");
            if (classTUICore != null) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            // 不含 TUIKit
        }

        // 如果包名被修改，再判断下调用栈的是否包含类的关键字
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String callName = "";
        for (int i = 0; i < stacks.length; i++) {
            if (i > 15) {
                return false;
            }
            callName = stacks[i].getClassName();
            String lowerCaseCallName = callName.toLowerCase();
            if (lowerCaseCallName.contains("tuikitimpl") || lowerCaseCallName.contains("tuicore")) {
                return true;
            }
        }

        return false;
    }

    private boolean isTUICore() {
        try {
            Class classTUICore = Class.forName("com.tencent.qcloud.tuicore.TUICore");
            if (classTUICore != null) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            // 不含 TUIKit
        }

        // 如果包名被修改，再判断下调用栈的是否包含类的关键字
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String callName = "";
        for (int i = 0; i < stacks.length; i++) {
            if (i > 15) {
                return false;
            }
            callName = stacks[i].getClassName();
            String lowerCaseCallName = callName.toLowerCase();
            if (lowerCaseCallName.contains("tuicore")) {
                return true;
            }
        }

        return false;
    }

    private boolean isFlutter() {
        try {
            Class c = Class.forName("com.qq.qcloud.tencent_im_sdk_plugin.tencent_im_sdk_plugin");
            if (c != null) {
                return true;
            }
        } catch (Exception e) {
            // 不含 Flutter
        }
        return false;
    }

    private boolean isUnity() {
        try {
            Class c = Class.forName("com.qcloud.tencentimsdk.TencentImSDKPluginUnity");
            if (c != null) {
                return true;
            }
        } catch (Exception e) {
            // 不含 Unity
        }
        return false;
    }

    public void checkTUIComponent(int componentType) {
        if (false == mInvokeFromTUICore) {
            return;
        }

        if (mTUIComponentList.contains(componentType)) {
            return;
        }

        if (!mTUIComponentCheckCountMap.containsKey(componentType)) {
            return;
        }

        String keyCallName = "";
        if (componentType == TUI_COMPONENT_CONVERSATION) {
            keyCallName = "conversationprovider";
        } else if (componentType == TUI_COMPONENT_CHAT) {
            keyCallName = "chatprovider";
        } else if (componentType == TUI_COMPONENT_GROUP) {
            keyCallName = "groupInfoprovider";
        } else if (componentType == TUI_COMPONENT_CONTACT) {
            keyCallName = "contactprovider";
        } else if (componentType == TUI_COMPONENT_SEARCH) {
            keyCallName = "searchdataprovider";
        } else {
            IMLog.e(TAG, "unknown tui component type:" + componentType);
            return;
        }

        int checkIndex = mTUIComponentCheckCountMap.get(componentType);
        if (checkIndex < TUI_COMPONENT_CHECK_COUNT_LIMIT) {
            checkIndex++;
            mTUIComponentCheckCountMap.put(componentType, checkIndex);

            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            String callName = "";
            // 限制调用栈查找层级
            for (int i = 0; i < stacks.length; i++) {
                if (i >= TUI_COMPONENT_STACK_LAYER_LIMIT) {
                    return;
                }

                callName = stacks[i].getClassName();
                String lowerCaseCallName = callName.toLowerCase();
                if (lowerCaseCallName.contains(keyCallName)) {
                    List<Integer> tuiComponentList = new ArrayList<>();
                    tuiComponentList.add(componentType);
                    nativeReportTUIComponentUsage(tuiComponentList);

                    mTUIComponentList.add(componentType);
                    break;
                }
            }
        }
    }


    public void login(final String userID, final String userSig, final IMCallback callback) {
        if (!isInit) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }
        nativeLogin(userID, userSig, callback);
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

    public boolean setLibraryPath(String libraryPath) {
        mLoadLibrarySuccess = SystemUtil.loadIMLibrary(libraryPath);
        return mLoadLibrarySuccess;
    }

    public void setTestEnvironment(boolean testEnvironment) {
        isTestEnvironment = testEnvironment;
    }

    public void setCustomServerInfo(CustomServerInfo customServerInfo) {
        nativeSetCustomServerInfo(customServerInfo);
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


    public void initLocalStorage(String userID, IMCallback callback) {
        if (!isInit) {
            if (callback != null) {
                callback.fail(BaseConstants.ERR_SDK_NOT_INITIALIZED, "sdk not init");
            }
            return;
        }

        nativeInitLocalStorage(userID, callback);
    }


    public void notifySelfInfoUpdated(UserInfo selfInfo) {
        if (sdkListenerWeakReference != null) {
            SDKListener listener = sdkListenerWeakReference.get();
            if (listener != null) {
                listener.onSelfInfoUpdated(selfInfo);
            }
        }
    }


    private void connect(Bootstrap bootstrap, String host, int port, int retry, int maxRetry, SDKListener sdkListener) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                IMLog.d(TAG,new Date() + ": 连接成功，启动控制台线程……");
                if (sdkListener!=null){
                    sdkListener.onConnectSuccess();
                }
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                IMLog.d(TAG,"重试次数已用完，放弃连接！");
                if (sdkListener!=null){
                    sdkListener.onConnectFailed(ERROR_INIT_MAX_TRY, "xim connect 连接失败！重试次数已用完，放弃连接！");
                }
            } else {
                // 第几次重连
                int order = (maxRetry - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                IMLog.d(TAG,new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1, maxRetry, sdkListener), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
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


    protected native void nativeLogin(String identifier, String userSig, IMCallback callBack);

    protected native void nativeLogout(XIMCallback callBack);

    protected native void nativeSetCustomServerInfo(CustomServerInfo customServerInfo);

    protected native String nativeGetLoginUser();

    protected native int nativeGetLoginStatus();

    protected native String nativeGetSDKVersion();

    protected native long nativeGetClockTickInHz();

    protected native long nativeGetTimeTick();

    protected native void nativeInitLocalStorage(String identifier, XIMCallback callBack);

}