package com.lucas.xim.group;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/12  11:14 下午
 */
import java.io.Serializable;

public class GroupMemberOperationResult implements Serializable {

    public static int OPERATION_RESULT_FAIL = 1;
    public static int OPERATION_RESULT_SUCCESS = 2;
    public static int OPERATION_RESULT_INVALID = 3;
    public static int OPERATION_RESULT_PENDING = 4;
    public static int OPERATION_RESULT_OVERLIMIT = 5;

    private String userID;
    private int status;

    public String getUserID() {
        return userID;
    }

    public int getStatus() {
        return status;
    }
}
