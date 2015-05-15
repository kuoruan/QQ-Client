package com.buaa.domain;

public class User {
    private int pid;// 用户pid

    private String account;// 用户帐号

    private String password;// 用户密码

    private String nickName;// 昵称

    private long registTime;// 注册时间

    private long lastLoginTime;// 最后一次登录时间

    private String friend;// 好友列表

    private boolean online;// 是否在线，0 离线 1在线

    public User() {
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(String account, String password, String nickName) {
        super();
        this.account = account;
        this.password = password;
        this.nickName = nickName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getRegistTime() {
        return registTime;
    }

    public void setRegistTime(long registTime) {
        this.registTime = registTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.account.equals(((User) obj).getAccount());
        }
        return false;
    }

}
