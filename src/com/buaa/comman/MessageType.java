package com.buaa.comman;

public abstract class MessageType {
    // 客户端向服务器端发送
    /**
     * 注册
     */
    public static final int REGEDIT = 0;
    /**
     * 登录
     */
    public static final int LOGIN = 1;
    /**
     * 获取在线好友列表
     */
    public static final int GET_ONLINE_FRIEND = 2;
    /**
     * 发送简单的单人信息
     */
    public static final int SEND_NORMAL_MESSAGE = 3;
    /**
     * 发送群组信息信息
     */
    public static final int SEND_GROUP_MESSAGE = 4;
    /**
     * 获取好友列表
     */
    public static final int GET__FRIEND = 5;
    /**
     * 下线
     */
    public static final int LOGOUT = 6;
    /**
     * 获取离线信息
     */
    public static final int GET_OFFLINE_MSG = 7;
    /**
     * 发送震屏
     */
    public static final int SHAKE = 8;

    // 服务器端向客户端发送
    /**
     * 注册成功
     */
    public static final int REGEDIT_SUCCESS = 10001;
    /**
     * 注册失败、有相同用户存在
     */
    public static final int REGEDIT_ERROR_SAME_USER = 10002;
    /**
     * 注册失败，其他原因
     */
    public static final int REGEDIT_ERROR = 10003;
    /**
     * 收到在线好友列表
     */
    public static final int RECEIVE_ONLINE_FRIEND = 10004;
    /**
     * 收到单人聊天信息
     */
    public static final int RECEIVE_NORMAL_MESSAGE = 10005;
    /**
     * 收到群组聊天信息
     */
    public static final int RECEIVE_GROUP_MESSAGE = 10006;
    /**
     * 登录成功
     */
    public static final int LOGIN_SUCCESS = 10007;
    /**
     * 登录失败
     */
    public static final int LOGIN_ERROR = 10008;
    /**
     * 用户已经登录
     */
    public static final int LOGIN_AREADY = 10009;
    /**
     * 未知错误
     */
    public static final int ERROR = 10010;
    /**
     * 转发成功
     */
    public static final int SEND_TO_SUCCESS = 10011;
    /**
     * 转发失败，对方不在线，信息已保存到数据库。
     */
    public static final int SEND_TO_ERROR_OFFLINE = 10012;
    /**
     * 未知原因转发出错
     */
    public static final int SEND_TO_ERROR = 10013;
    /**
     * 获取好友列表成功
     */
    public static final int GET_FRIEND_SUCCESS = 10014;
    /**
     * 获取好友列表失败
     */
    public static final int GET_FRIEND_ERROR = 10015;
    /**
     * 有人上线
     */
    public static final int USER_ENTER = 10016;
    /**
     * 有人下线
     */
    public static final int USER_LOGOUT = 10017;
    /**
     * 连接超时
     */
    public static final int TIME_OUT = 10018;
    /**
     * 收到离线消息
     */
    public static final int RECEIVE_OFFLINE_MSG = 10019;
    /**
     * 收到震屏
     */
    public static final int RECEIVE_SHAKE = 10020;
}
