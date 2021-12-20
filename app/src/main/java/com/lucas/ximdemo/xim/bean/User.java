package com.lucas.ximdemo.xim.bean;


import java.io.Serializable;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/14  1:55 下午
 */
public class User implements Serializable {

    private String _id;
    private String nickname;
    private String password;
    private String mobile;
    private Long create_time;
    private String token;

    public User(String _id, String nickname, String password, String mobile, Long create_time, String token) {
        this._id = _id;
        this.nickname = nickname;
        this.password = password;
        this.mobile = mobile;
        this.create_time = create_time;
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
