package com.lucas.xim.config;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/09/16:03 下午
 */
public class IMOptions {

    private ImplementationMode implementationMode;// 实现方式
    private CommunicationProtocol communicationProtocol;// 通信协议
    private TransportProtocol transportProtocol;// 传输协议
    private int port;// 端口号
    private  int MAX_RETRY = 5;
    private  String HOST = "127.0.0.1";

    private IMOptions(Builder builder) {
        if (builder == null) {
            return;
        }

        this.implementationMode = builder.implementationMode;
        this.communicationProtocol = builder.communicationProtocol;
        this.transportProtocol = builder.transportProtocol;
        this.MAX_RETRY = builder.MAX_RETRY;
        this.HOST = builder.HOST;
        this.port = builder.port;
    }

    public ImplementationMode getImplementationMode() {
        return implementationMode;
    }

    public CommunicationProtocol getCommunicationProtocol() {
        return communicationProtocol;
    }

    public TransportProtocol getTransportProtocol() {
        return transportProtocol;
    }

    public int getPort() {
        return port;
    }

    public int getMAX_RETRY() {
        return MAX_RETRY;
    }

    public String getHost(){
        return  HOST;
    }

    @Override
    public String toString() {
        return "IMSOptions\n{" +
                "\n\timplementationMode=" + implementationMode +
                "\n\tcommunicationProtocol=" + communicationProtocol +
                "\n\ttransportProtocol=" + transportProtocol +
                "\n\tport=" + port +
                "\n}";
    }

    public static class Builder {

        private ImplementationMode implementationMode;// 实现方式
        private CommunicationProtocol communicationProtocol;// 通信协议
        private TransportProtocol transportProtocol;// 传输协议
        private int port;// 端口号
        private  int MAX_RETRY = 5;
        private  String HOST = "127.0.0.1";

        public Builder() {
            this.implementationMode = ImplementationMode.Netty;
        }

        public Builder setImplementationMode(ImplementationMode implementationMode) {
            this.implementationMode = implementationMode;
            return this;
        }

        public Builder setHost(String host) {
            this.HOST = host;
            return this;
        }

        public Builder setMAX_RETRY(int MAX_RETRY) {
            this.MAX_RETRY = MAX_RETRY;
            return this;
        }

        public Builder setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
            this.communicationProtocol = communicationProtocol;
            if(this.port == 0) {
                switch (communicationProtocol) {
                    case TCP:
                        this.port = IMConfig.TCP_PORT;
                        break;

                    case WebSocket:
                        this.port = IMConfig.WEBSOCKET_PORT;
                        break;
                }
            }
            return this;
        }

        public Builder setTransportProtocol(TransportProtocol transportProtocol) {
            this.transportProtocol = transportProtocol;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public IMOptions build() {
            return new IMOptions(this);
        }
    }
}
