package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import net.sf.json.JSONObject;

import com.buaa.comman.Config;
import com.buaa.comman.MessageType;
import com.buaa.domain.ClientJButton;
import com.buaa.domain.ClientJFrame;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.Message;
import com.buaa.domain.User;
import com.buaa.utils.DateUtil;
import com.buaa.utils.GroupUserManager;
import com.buaa.utils.OnlineUserManager;

@SuppressWarnings("serial")
public class GroupChatWindow extends ClientJFrame implements ActionListener {
    private JPanel banner, msgPanel, listPanel, msg;
    private JLabel icon, targetName;
    private JTextArea inputArea;
    private JScrollPane msgScrollPane, listScrollPane;
    private JButton sendBtn, clearBtn;
    private User me;
    private int listHeight = 0;
    private int msgHeight = 0;

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
        msgPanel = new JPanel();
        msgPanel.setBackground(Color.decode(Config.CHAT_WINDOW_BGCOLOR));
        msgScrollPane = new JScrollPane(msgPanel);
        msgScrollPane.setBounds(0, 70, 380, 280);
        msgScrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        msgScrollPane.setAutoscrolls(true);
        // 设置鼠标滚动速度
        msgScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(msgScrollPane);
        inputArea = new JTextArea();
        inputArea.setBounds(0, 350, 580, 110);
        inputArea.setMargin(new Insets(10, 10, 10, 10));
        this.add(inputArea);
        clearBtn = new ClientJButton(380, 470, 70, 24, "清除", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(clearBtn);
        sendBtn = new ClientJButton(460, 470, 70, 24, "发送", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(sendBtn);
        listPanel = new JPanel();
        showList();
        listScrollPane = new JScrollPane(listPanel);
        listScrollPane.setBorder(null);
        listScrollPane.setBounds(380, 70, 200, 280);
        listScrollPane.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0,
                Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        this.add(listScrollPane);
    }

    public void showList() {
        List<User> onlineUsers = OnlineUserManager.getOnlineUser(me).getOnlineUsers();
        JPanel mePanel = showOnlineUser(me);
        listPanel.add(mePanel);
        listHeight = 34;
        for (User user : onlineUsers) {
            GroupUserManager.addGroupUser(user);
            JPanel online = showOnlineUser(user);
            listPanel.add(online);
            listHeight += 34;
        }
        listPanel.setPreferredSize(new Dimension(180, listHeight));
        System.out.println(listHeight);
    }

    private void addEvent() {
        sendBtn.addActionListener(this);
        clearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputArea.setText(null);
            }
        });
    }

    private JPanel showOnlineUser(User user) {
        JPanel friend = new JPanel();
        friend.setLayout(null);
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
        addMessage(msg, true);
        OnlineUserManager.getOnlineUser(me)
                .send(MessageType.SEND_GROUP_MESSAGE + JSONObject.fromObject(msg).toString());
    }

    public void addMessage(Message m, boolean isSend) {
        String date = DateUtil.getTime(Config.MSG_DATE_FORMAT, m.getSendTime());
        JLabel name, time, content;
        content = new JLabel("<html>" + m.getContent() + "</html>");
        content.setFont(new Font(Config.DEFAULT_FONT, Font.TRUETYPE_FONT, 14));
        View labelView = BasicHTML.createHTMLView(content, content.getText());
        labelView.setSize(135, 14);
        int trueHight = (int) labelView.getMinimumSpan(View.Y_AXIS);
        if (isSend) {
            name = new ClientJLabel("我", 312, 3, 32, 32);
            name.setBackground(Color.white);
            time = new ClientJLabel(date, 180, 0, 200, 15, 10, Color.decode(Config.DEFAULT_DATE_COLOR));
            content.setBounds(50, 20, 250, trueHight);
            content.setHorizontalAlignment(JLabel.RIGHT);
        } else {
            User who = GroupUserManager.getGroupUserById(m.getFromPid());
            name = new ClientJLabel(who.getNickName(), 10, 3, 32, 32);
            name.setBackground(Color.white);
            time = new ClientJLabel(date, 50, 0, 200, 15, 10, Color.decode(Config.DEFAULT_DATE_COLOR));
            content.setBounds(50, 20, 250, trueHight);
        }

        trueHight += 30;
        msg = new JPanel();
        msg.setLayout(null);
        msg.add(time);
        msg.add(name);
        msg.add(content);
        msg.setPreferredSize(new Dimension(360, trueHight));
        msgHeight = msgHeight + trueHight + 5;
        System.out.println(msgHeight);
        freshMsg();
    }

    private void freshMsg() {
        msgPanel.add(msg);
        msgPanel.setPreferredSize(new Dimension(360, msgHeight));
        msgPanel.validate();
        msgScrollPane.validate();
        JScrollBar sBar = msgScrollPane.getVerticalScrollBar();
        // 自动滚动到最下面
        sBar.setValue(sBar.getMaximum());
    }
}
