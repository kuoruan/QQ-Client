package com.buaa.domain;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.buaa.comman.Config;
import com.buaa.utils.DateUtil;
import com.buaa.view.ChatWindow;

public class FriendShow extends JLabel {
    private int width;
    private int height;
    private User friend;
    private User me;
    private ClientLink client;

    private boolean online;
    private String friendName, icon;
    private String friendLastLogin;
    private JLabel iconLabel, friendNameLabel, friendLastLoginLabel;

    public FriendShow(int width, int height, User friend, User me, ClientLink client) {
        super();
        this.width = width;
        this.height = height;
        this.friend = friend;
        this.me = me;
        prepare();
        init();
        addEvent();
    }

    private void prepare() {
        friendName = friend.getNickName();
        online = friend.isOnline();
        if (online) {
            icon = Config.DEFAULT_ICON_48;
        } else {
            icon = Config.DEFAULT_ICON_48;
        }
        friendLastLogin = DateUtil.getTime(Config.DEFAULT_DATE_FORMAT, friend.getLastLoginTime());
    }

    private void init() {
        this.setSize(width, height);
        this.setLayout(null);
        iconLabel = new ClientJLabel(10, 10, 48, 48, icon);
        this.add(iconLabel);
        friendNameLabel = new ClientJLabel(70, 10, 50, 20, friendName);
        this.add(friendNameLabel);
        friendLastLoginLabel = new ClientJLabel(friendLastLogin, 70, 50, 180, 20);
        this.add(friendLastLoginLabel);
    }

    private void addEvent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChatWindow(me, friend, client);
            }
        });
    }
}
