package com.buaa.comman;

public abstract class Config {
    /**
     * 服务器地址
     */
    public static final String SERVER_ADDRESS = "127.0.0.1";
    //public static final String SERVER_ADDRESS = "182.92.234.251";
    /**
     * 服务器端口
     */
    public static final int SERVER_PORT = 5487;
    /**
     * 连接超时时间 ms
     */
    public static final int SOCKET_TIME_OUT = 5000;
    /**
     * 默认字体
     */
    public static final String DEFAULT_FONT = "微软雅黑";
    /**
     * 聊天信息字体大小
     */
    public static final int CHAT_FONT_SIZE = 14;
    /**
     * 默认时间格式
     */
    public static final String LOGIN_DATE_FORMAT = "yyyy年MM月dd日 HH:mm";
    /**
     * 默认时间格式
     */
    public static final String MSG_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 默认列表时间颜色
     */
    public static final String DEFAULT_DATE_COLOR = "#7F7F7F";
    /**
     * 文字链接按钮颜色
     */
    public static final String LINK_FONT_COLOR = "#2786E4";
    /**
     * 发送按钮颜色
     */
    public static final String BLUE_BUTTON_COLOR = "#09A3DC";
    /**
     * 默认头像图标 72像素
     */
    public static final String DEFAULT_ICON_72 = "image/DefaultIcon_72.png";
    /**
     * 默认头像图标 64像素
     */
    public static final String DEFAULT_ICON_64 = "image/DefaultIcon_64.png";
    /**
     * 默认头像图标 48像素
     */
    public static final String DEFAULT_ICON_48 = "image/DefaultIcon_48.png";
    /**
     * 默认头像图标 32像素
     */
    public static final String DEFAULT_ICON_32 = "image/DefaultIcon_32.png";
    /**
     * 默认头像图标 24像素
     */
    public static final String DEFAULT_ICON_24 = "image/DefaultIcon_24.png";
    /**
     * 默认头像图标 16像素
     */
    public static final String DEFAULT_ICON_16 = "image/DefaultIcon_16.png";

    /**
     * 默认离线头像图标 48像素
     */
    public static final String DEFAULT_OFFLINE_ICON_48 = "image/DefaultOfflineIcon_48.png";
    /**
     * 关闭按钮默认图标
     */
    public static final String CLOSE_DEFAULT_IMG = "image/CloseBtn.png";
    /**
     * 默认关闭按钮鼠标悬停图标
     */
    public static final String CLOSE_ON_DEFAULT_IMG = "image/CloseBtnOn.png";
    /**
     * 登陆窗口关闭按钮图标
     */
    public static final String LOGIN_CLOSE_IMG = "image/LoginCloseBtn.png";
    /**
     * 注册窗口关闭按钮图标
     */
    public static final String REGISTER_CLOSE_IMG = "image/RegisterCloseBtn.png";
    /**
     * 主面板关闭按钮鼠标悬停图标
     */
    public static final String MAIN_BOARD_CLOSE_IMG = "image/MainBoardCloseBtn.png";
    /**
     * 登陆窗口BANNER
     */
    public static final String LOGIN_BANNER = "image/LoginBanner.png";
    /**
     * 注册窗口BANNER
     */
    public static final String REGISTER_BANNER = "image/RegisterBanner.png";
    /**
     * 主面板背景颜色
     */
    public static final String MAIN_BOARD_BANNER_COLOR = "#288ADD";
    /**
     * 主面板搜索背景颜色
     */
    public static final String MAIN_BOARD_SEARCH_COLOR = "#7EB9EA";
    /**
     * 聊天窗口背景颜色
     */
    public static final String CHAT_WINDOW_BGCOLOR = "#EAECF8";
    /**
     * 聊天窗口边框颜色
     */
    public static final String CHAT_WINDOW_BORDER_COLOR = "#D3D6E2";
    /**
     * 主面板底部背景图片
     */
    public static final String MAIN_BOARD_BOTTOM = "image/MainBoardBottom.png";
    /**
     * 主面板搜索背景
     */
    public static final String MAIN_BOARD_SEARCH = "image/MainBoardSearch.png";
    /**
     * 聊天窗口背景背景
     */
    public static final String CHAT_WINDOW_BACKGROUND = "image/ChatWindowBackground.png";
    /**
     * 主面板组群按钮
     */
    public static final String MAIN_BOARD_GROUP_BTN = "image/MainBoardGroup.png";
    /**
     * 主面板组群按钮悬停
     */
    public static final String MAIN_BOARD_GROUP_BTN_ON = "image/MainBoardGroupOn.png";
    /**
     * 好友列表背景颜色
     */
    public static final String FRIEND_LIST_BGCOLOR = "#EAF4FC";
    /**
     * 好友列表鼠标放上颜色
     */
    public static final String FRIEND_LIST_MOUSE_ON_COLOR = "#FCF0C1";
    /**
     * 好友列表下边框颜色
     */
    public static final String FRIEND_LIST_BORDER_COLOR = "#E8DDB1";
    /**
     * 点击关闭按钮关闭整个系统
     */
    public static final int CLOSE_SYSTEM = 1;
    /**
     * 点击关闭按钮关闭当前窗口
     */
    public static final int CLOSE_WINDOW = 2;
    /**
     * 震动频率
     */
    public static final int SHAKE_TIME = 50;
    /**
     * 震动次数
     */
    public static final int SHAKE_TIMES = 5;
}
