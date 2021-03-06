package com.buaa.domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.buaa.comman.Config;
import com.buaa.utils.ChatWindowManager;
import com.buaa.utils.DateUtil;
import com.buaa.view.ChatWindow;

@SuppressWarnings("serial")
public class FriendShow extends JPanel {
    private int width;
    private int height;
    private User friend;
    private User me;

    private String friendName, icon;
    private String friendLastLogin;
    private JLabel iconLabel, friendNameLabel, friendLastLoginLabel;

    public FriendShow(int width, int height, User friend, User me) {
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
        if (friend.isOnline()) {
            icon = Config.DEFAULT_ICON_48;
        } else {
            icon = Config.DEFAULT_OFFLINE_ICON_48;
        }
        friendLastLogin = DateUtil.getTime(Config.LOGIN_DATE_FORMAT, friend.getLastLoginTime());
    }

    private void init() {
        this.setLayout(null);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(Config.FRIEND_LIST_BORDER_COLOR)));
        this.setBackground(Color.decode(Config.FRIEND_LIST_BGCOLOR));
        this.setPreferredSize(new Dimension(width, height - 1));
        iconLabel = new ClientJLabel(20, 5, 48, 48, icon);
        this.add(iconLabel);
        friendNameLabel = new ClientJLabel(friendName, 80, 5, 150, 20);
        this.add(friendNameLabel);
        friendLastLoginLabel = new ClientJLabel(friendLastLogin, 80, 30, 180, 20,
                Color.decode(Config.DEFAULT_DATE_COLOR));
        this.add(friendLastLoginLabel);
    }

    private void addEvent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if (ChatWindowManager.isOpened(friend)) {
                        ChatWindow openedWindow = ChatWindowManager.getOpened(friend);
                        openedWindow.requestFocus();
                    } else {
                        ChatWindow c = new ChatWindow(me, friend);
                        ChatWindowManager.addChatWindow(c);
                    }

                }
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
