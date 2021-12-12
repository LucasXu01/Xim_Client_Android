package com.lucas.ximdemo.xim.v1;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/11  11:59 上午
 */
import java.io.Serializable;

public class MessageReceipt implements Serializable {
    private String userID;
    private long receiptTimestamp;

    public String getUserID() {
        return userID;
    }

    public long getReceiptTimestamp() {
        return receiptTimestamp;
    }
}

