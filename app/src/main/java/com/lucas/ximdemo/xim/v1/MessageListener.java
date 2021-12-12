package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  11:55 上午
 */
import java.util.List;

public abstract class MessageListener {
    public void onReceiveNewMessage(List<Message> messageList) {}

    public void onReceiveMessageReceipt(List<MessageReceipt> receiptList) {}

    public void onReceiveMessageRevoked(List<MessageKey> keyList) {}

    public void onReceiveMessageModified(List<Message> messageList) {}
}