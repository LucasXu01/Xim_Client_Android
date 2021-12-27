package com.lucas.ximdemo.xim;

import com.lucas.ximdemo.xim.bean.User;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/16  5:02 下午
 */
public class MyUtils {
    private User user;
    private static final MyUtils ourInstance = new MyUtils();

    public static MyUtils getInstance() {
        return ourInstance;
    }

    private MyUtils() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
