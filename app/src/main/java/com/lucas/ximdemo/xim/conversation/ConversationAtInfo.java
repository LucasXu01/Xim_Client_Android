package com.lucas.ximdemo.xim.conversation;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:18 下午
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationAtInfo implements Serializable {
    public static final String AT_ALL_TAG = "__kImSDK_MesssageAtALL__";

    /**
     *  错误状态
     */
    public static final int TIM_AT_UNKNOWN = 0;
    /**
     *  @ 我
     */
    public static final int TIM_AT_ME = 1;
    /**
     *  @ 群里所有人
     */
    public static final int TIM_AT_ALL = 2;
    /**
     *  @ 群里所有人并且单独 @ 我
     */
    public static final int TIM_AT_ALL_AT_ME = 3;

    private List<Integer> atTypes = new ArrayList<>();
    private long atMessageSequence;

    public int getAtType() {
        int atType = TIM_AT_UNKNOWN;
        for (int type : atTypes) {
            if (type == TIM_AT_ME) {
                atType = atType | 0x01;
            } else if (type == TIM_AT_ALL) {
                atType = atType | 0x02;
            }
        }

        return atType;
    }

    public long getAtMessageSequence() {
        return atMessageSequence;
    }

    protected void addAtType(int atType) {
        this.atTypes.add(atType);
    }
}
