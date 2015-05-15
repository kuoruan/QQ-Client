package com.buaa.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.json.JSONObject;

import com.buaa.comman.Config;
import com.buaa.comman.MessageType;
import com.buaa.domain.BackGroundPanel;
import com.buaa.domain.ClientJButton;
import com.buaa.domain.ClientJDialog;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.ClientJTextField;
import com.buaa.domain.FriendShow;
import com.buaa.domain.OnlineUser;
import com.buaa.domain.User;
import com.buaa.utils.DateUtil;
import com.buaa.utils.OnlineUserManager;

@SuppressWarnings("serial")
public class MainBoard extends ClientJDialog {
    private int width;
    private int height;
    private User me;
    private JPanel banner, search, bottom, friendPanel;
    private JLabel myIcon, myName, lastLogin, loginTime;
    private JTextField searchTF;
    private JButton group;
    private JScrollPane friendScroll;
    private int friendPanelheight = 0;
    private TrayIcon trayIcon;

    public MainBoard(int width, int height, User me, int closeType) {
        super(width, height, Config.MAIN_BOARD_CLOSE_IMG, closeType);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screensize.getWidth());
        this.setLocation(screenWidth - width - 50, 30);
        this.width = width;
        this.height = height;
        this.me = me;
        prepare();
        init();
        addTray();
        addEvent();
        showFrame();
    }

    public MainBoard() {
    }

    private void prepare() {
        friendPanel = new JPanel();
        friendPanel.setBackground(Color.decode(Config.FRIEND_LIST_BGCOLOR));
        showList();
        // 设置friendPanel大小
        friendPanel.setPreferredSize(new Dimension(width - 20, friendPanelheight));
        // 创建friendScroll对象，将friendPanel加入进去
        friendScroll = new JScrollPane(friendPanel);
        friendScroll.setBorder(null);
        friendScroll.setBounds(0, 183, width, height - 60 - 183);
        // 自动显示滚动条
        friendScroll.setAutoscrolls(true);
        // 设置鼠标滚动速度
        friendScroll.getVerticalScrollBar().setUnitIncrement(20);
        this.add(friendScroll);
    }

    private void init() {
        banner = new JPanel();
        banner.setLayout(null);
        banner.setBackground(Color.decode(Config.MAIN_BOARD_BANNER_COLOR));
        banner.setBounds(0, 0, width, 115);
        myIcon = new ClientJLabel(10, 25, 64, 64, Config.DEFAULT_ICON_64);
        banner.add(myIcon);
        myName = new ClientJLabel(me.getNickName(), 80, 20, 150, 20, 14, Color.white);
        banner.add(myName);
        lastLogin = new ClientJLabel("上次登陆：", 80, 50, 70, 20, 12, Color.white);
        banner.add(lastLogin);
        loginTime = new ClientJLabel(DateUtil.getTime(Config.LOGIN_DATE_FORMAT, me.getLastLoginTime()), 80, 70, 170,
                20, 14, Color.white);
        banner.add(loginTime);
        this.add(banner);
        search = new BackGroundPanel(0, 115, width, 68, Config.MAIN_BOARD_SEARCH);
        searchTF = new ClientJTextField(10, 3, width - 20, 25, Color.decode(Config.MAIN_BOARD_SEARCH_COLOR));
        searchTF.setBorder(null);
        search.add(searchTF);
        group = new ClientJButton(width - 90, 31, 90, 37, Color.decode(Config.CHAT_WINDOW_BGCOLOR));
        group.setIcon(new ImageIcon(Config.MAIN_BOARD_GROUP_BTN));
        search.add(group);
        this.add(search);
        bottom = new BackGroundPanel(0, height - 60, width, 60, Config.MAIN_BOARD_BOTTOM);
        this.add(bottom);
    }

    private void addEvent() {
        group.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                group.setIcon(new ImageIcon(Config.MAIN_BOARD_GROUP_BTN_ON));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                group.setIcon(new ImageIcon(Config.MAIN_BOARD_GROUP_BTN));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    new GroupChatWindow(me);
                }
            }
        });
    }

    /**
     * 遍历并显示好友列表
     * 
     * @Title: showList
     * @param:
     * @return: void
     * @throws
     */
    private void showList() {
        List<User> friendList = OnlineUserManager.getOnlineUser(me).getList();
        for (User user : friendList) {
            friendPanelheight += 64;
            JPanel friend = new FriendShow(width, 60, user, me);
            friendPanel.add(friend);
        }
    }

    /**
     * 用户上线
     * 
     * @param user
     */
    public void refeshUser() {
        friendPanel.removeAll();
        showList();
        friendPanel.repaint();
        friendPanel.validate();
    }

    /**
     * 添加托盘图标
     * 
     * @Title: addTray
     * @param:
     * @return: void
     * @throws
     */
    private void addTray() {
        if (SystemTray.isSupported()) {// 判断当前平台是否支持托盘功能
            // 创建托盘实例
            SystemTray tray = SystemTray.getSystemTray();
            // 创建Image图像
            Image image = new ImageIcon(Config.DEFAULT_ICON_16).getImage();
            // 停留提示text
            String text = "QQ客户端";
            // 弹出菜单popupMenu
            PopupMenu popMenu = new PopupMenu();

            MenuItem itmOpen = new MenuItem("Open");
            itmOpen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showFrame();
                }
            });
            MenuItem itmHide = new MenuItem("Hidden");
            itmHide.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MainBoard.this.setVisible(false);
                }
            });
            MenuItem itmExit = new MenuItem("Exit");

            itmExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    OnlineUser u = OnlineUserManager.getOnlineUser(me);
                    u.send(MessageType.LOGOUT, JSONObject.fromObject(me));
                    u.setContinue(false);
                    System.exit(0);
                }
            });
            popMenu.add(itmOpen);
            popMenu.add(itmHide);
            popMenu.add(itmExit);

            // 创建托盘图标
            trayIcon = new TrayIcon(image, text, popMenu);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        showFrame();
                    }
                }
            });
            // 将托盘图标加到托盘上
            try {
                tray.add(trayIcon);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 显示窗体
     * 
     * @Title: showFrame
     * @param:
     * @return: void
     * @throws
     */
    private void showFrame() {
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    public User getMe() {
        return me;
    }

}
