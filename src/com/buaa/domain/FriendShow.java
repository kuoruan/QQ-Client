package com.buaa.domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.buaa.comman.Config;
import com.buaa.utils.ClientLink;
import com.buaa.utils.DateUtil;
import com.buaa.view.ChatWindow;

public class FriendShow extends JPanel {
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
        this.setLayout(null);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(Config.FRIEND_LIST_BORDER_COLOR)));
        this.setBackground(Color.decode(Config.FRIEND_LIST_BGCOLOR));
        this.setPreferredSize(new Dimension(width, height-1));
        iconLabel = new ClientJLabel(10, 5, 48, 48, icon);
        this.add(iconLabel);
        friendNameLabel = new ClientJLabel(friendName, 70, 5, 50, 20);
        this.add(friendNameLabel);
        friendLastLoginLabel = new ClientJLabel(friendLastLogin, 70, 30, 180, 20);
        this.add(friendLastLoginLabel);
    }

    private void addEvent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChatWindow(me, friend, client);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                FriendShow.this.setBackground(Color.decode(Config.FRIEND_LIST_MOUSE_ON_COLOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                FriendShow.this.setBackground(Color.decode(Config.FRIEND_LIST_BGCOLOR));
            }
        });
    }
}
