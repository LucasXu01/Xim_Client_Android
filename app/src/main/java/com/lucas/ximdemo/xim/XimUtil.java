package com.lucas.ximdemo.xim;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.lucas.ximdemo.xim.bean.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/26  10:46 下午
 */
public class XimUtil {
    public final String XIMUSER = "user";
    private User user;
    private static final XimUtil ourInstance = new XimUtil();

    public static XimUtil getInstance() {
        return ourInstance;
    }

    private XimUtil() {
    }

    public User getUser() {
        if (user == null && !pref_get(XIMUSER).equals("-1")){
            user = new Gson().fromJson( pref_get(XIMUSER),User.class);
        }
        return user;
    }


    public void pref_clear(String name) {
        SharedPreferences.Editor editor = Utils.getApp().getSharedPreferences("xim", MODE_PRIVATE).edit();
        editor.putString(name, "-1");
        editor.apply();
    }


    public static void pref_put(String name, String value) {
        SharedPreferences.Editor editor = Utils.getApp().getSharedPreferences("xim", MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }


    public static String pref_get(String name) {
        SharedPreferences pref = Utils.getApp().getSharedPreferences("xim", MODE_PRIVATE);
        return pref.getString(name, "-1");
    }

}
