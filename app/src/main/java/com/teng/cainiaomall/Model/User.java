package com.teng.cainiaomall.Model;

public class User {
    //用户编号
    private String user_id;
    //用户密码
    private String user_password;
    //用户姓名
    private String user_name;
    //用户余额
    private Double user_money;
    //用户电话
    private Long user_tel;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Double getUser_money() {
        return user_money;
    }

    public void setUser_money(Double user_money) { this.user_money = user_money; }

    public Long getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(Long user_tel) {
        this.user_tel = user_tel;
    }

    public int getUser_statue() {
        return user_statue;
    }

    public void setUser_statue(int user_statue) {
        this.user_statue = user_statue;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    //账号状态
    private int user_statue;
    //保存头像路径
    private String user_avatar;
}
