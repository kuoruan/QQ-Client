package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.json.JSONObject;

import com.buaa.comman.Config;
import com.buaa.comman.MessageType;
import com.buaa.domain.ClientJButton;
import com.buaa.domain.ClientJFrame;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.Message;
import com.buaa.domain.User;
import com.buaa.utils.OnlineUserManager;

@SuppressWarnings("serial")
public class GroupChatWindow extends ClientJFrame implements ActionListener {
    private JPanel banner, listPanel;
    private JLabel icon, targetName;
    private JTextArea msgArea, inputArea;
    private JScrollPane msgScrollPane, listScrollPane;
    private JButton sendBtn, clearBtn;
    private User me;
    private int listHeight = 0;

    public GroupChatWindow(User me) {
        super(580, 500, Color.decode(Config.CHAT_WINDOW_BGCOLOR));
        this.me = me;
        init();
        addEvent();
        this.setVisible(true);
    }

    private void init() {
        banner = new JPanel();
        banner.setLayout(null);
        banner.setBounds(0, 0, 580, 70);
        icon = new ClientJLabel(10, 10, 48, 48, Config.DEFAULT_ICON_48);
        banner.add(icon);
        targetName = new ClientJLabel("聊天室", 70, 10, 150, 30, 20);
        banner.add(targetName);
        this.add(banner);
        msgArea = new JTextArea();
        msgArea.setPreferredSize(new Dimension(360, 276));
        msgArea.setEditable(false);
        msgScrollPane = new JScrollPane(msgArea);
        // 去掉边框
        msgScrollPane.setBorder(null);
        msgScrollPane.setBounds(0, 70, 380, 280);
        msgScrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        msgScrollPane.setAutoscrolls(true);
        // 设置鼠标滚动速度
        msgScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(msgScrollPane);
        inputArea = new JTextArea();
        inputArea.setBounds(0, 350, 580, 110);
        this.add(inputArea);
        clearBtn = new ClientJButton(380, 470, 70, 24, "清除", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(clearBtn);
        sendBtn = new ClientJButton(460, 470, 70, 24, "发送", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(sendBtn);
        listPanel = new JPanel();
        showList();
        listPanel.setPreferredSize(new Dimension(180, listHeight));
        listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setBorder(null);
        listScrollPane.setBounds(380, 70, 200, 280);
        listScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0,Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        this.add(listScrollPane);
    }

    public void showList() {
        List<User> onlineUsers = OnlineUserManager.getOnlineUser(me).getOnlineUsers();
        for (User user : onlineUsers) {
            JPanel online = showOnlineUser(user);
            listPanel.add(online);
            listHeight += 34;
        }
    }

    private void addEvent() {
        sendBtn.addActionListener(this);
    }

    private JPanel showOnlineUser(User user) {
        JPanel friend = new JPanel();
        friend.setPreferredSize(new Dimension(200, 30));
        JLabel icon = new ClientJLabel(5, 5, 24, 24, Config.DEFAULT_ICON_24);
        friend.add(icon);
        JLabel name = new ClientJLabel(user.getNickName(), 35, 5, 70, 20);
        friend.add(name);
        return friend;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String content = inputArea.getText().trim();
        Message msg = new Message(content, me.getPid(), 0, new Date().getTime());
        inputArea.setText("");
        OnlineUserManager.getOnlineUser(me)
                .send(MessageType.SEND_GROUP_MESSAGE + JSONObject.fromObject(msg).toString());
    }
}
