package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.json.JSONObject;

import com.buaa.comman.Config;
import com.buaa.comman.MessageType;
import com.buaa.domain.ClientJButton;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.Message;
import com.buaa.domain.MessageResult;
import com.buaa.domain.User;
import com.buaa.utils.ClientLink;
import com.buaa.utils.DateUtil;
import com.buaa.utils.MessageUtil;
import com.buaa.utils.WindowUtil;

public class ChatWindow extends JFrame implements ActionListener {
    private User me;
    private User target;
    private ClientLink client;
    private JScrollPane msgAreaSP;
    private JLabel targetName, targetIcon;
    private JTextArea msgArea, inputArea;
    private Color bgColor = Color.decode(Config.CHAT_WINDOW_BGCOLOR);
    private JButton sendBtn, clearBtn;
    private boolean isContinue = true;
    private Message msg;

    /*
     * public static void main(String[] args) { User me = new User("小明",
     * "56685258"); me.setNickName("小强"); new ChatWindow(me, me, null); }
     */

    public ChatWindow(User me, User target, ClientLink client) {
        super();
        this.me = me;
        this.target = target;
        this.client = client;
        prepare();
        init();
        addEvent();
        this.setVisible(true);
    }

    private void prepare() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue) {
                    String msg = client.input();
                    System.out.println(msg);
                    MessageResult mr = MessageUtil.analyzeMessage(msg);
                    if (mr.isRight()) {
                        showMsg(mr);
                    }
                }
            }
        }).start();
    }

    private void init() {
        this.setSize(580, 500);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setBackground(bgColor);
        targetIcon = new ClientJLabel(10, 10, 48, 48, Config.DEFAULT_ICON_48);
        this.add(targetIcon);
        targetName = new ClientJLabel(target.getNickName(), 70, 20, 150, 20);
        this.add(targetName);
        msgArea = new JTextArea();
        msgArea.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        msgArea.setPreferredSize(new Dimension(560, 276));
        msgArea.setBackground(bgColor);
        msgArea.setEditable(false);
        msgAreaSP = new JScrollPane(msgArea);
        // 去掉边框
        msgAreaSP.setBorder(null);
        msgAreaSP.setBounds(0, 70, 580, 280);
        msgAreaSP.setAutoscrolls(true);
        // 设置鼠标滚动速度
        msgAreaSP.getVerticalScrollBar().setUnitIncrement(20);
        this.add(msgAreaSP);

        inputArea = new JTextArea();
        inputArea.setBackground(bgColor);
        inputArea.setBounds(0, 350, 580, 110);
        this.add(inputArea);
        clearBtn = new ClientJButton(380, 470, 70, 24, "清除", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(clearBtn);
        sendBtn = new ClientJButton(460, 470, 70, 24, "发送", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(sendBtn);
        WindowUtil.AddMouseDrag(this);
        WindowUtil.AddCloseButton(this, Config.CLOSE_DEFAULT_IMG, Color.decode(Config.MAIN_BOARD_BANNER_COLOR),
                Config.CLOSE_WINDOW);
    }

    private void addEvent() {
        sendBtn.addActionListener(this);
    }

    public User getTarget() {
        return target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String content = inputArea.getText().trim();
        msg = new Message(content, me.getPid(), target.getPid(), new Date().getTime());
        client.output(MessageType.SEND_NORMAL_MESSAGE, JSONObject.fromObject(msg));
    }

    private void showMsg(MessageResult mr) {
        JSONObject jsonObj = JSONObject.fromObject(mr.getJsonString());
        switch (mr.getMessageType()) {
        case MessageType.RECEIVE_NORMAL_MESSAGE:
            if (target.getPid() + "" == jsonObj.get("fromPid")) {
                Message msg = (Message) JSONObject.toBean(jsonObj, Message.class);
                String date = DateUtil.getTime(Config.DEFAULT_DATE_FORMAT, msg.getSendTime());
                msgArea.append(target.getNickName() + " " + date + "\n");
                msgArea.append(msg.getContent() + "\n");
            }
            break;
        case MessageType.USER_ENTER:
            if (target.equals(jsonObj.get("user"))) {
                targetIcon.setIcon(new ImageIcon(Config.DEFAULT_ICON_48));
            }
            break;
        case MessageType.USER_LOGOUT:
            if (target.equals(jsonObj.get("user"))) {
                targetIcon.setIcon(new ImageIcon(Config.DEFAULT_OFFLINE_ICON_48));
            }
            break;
        case MessageType.SEND_TO_SUCCESS:
            break;
        case MessageType.SEND_TO_ERROR_OFFLINE:
            break;
        case MessageType.SEND_TO_ERROR:
            break;
        case MessageType.TIME_OUT:
            break;
        default:
            break;
        }
    }
}
