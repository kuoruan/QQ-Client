package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import com.buaa.utils.DateUtil;
import com.buaa.utils.OnlineUserManager;

@SuppressWarnings("serial")
public class ChatWindow extends ClientJFrame implements ActionListener {
    private User me;
    private User target;
    private JScrollPane msgScrollPane;
    private JLabel targetName, targetIcon;
    private JTextArea msgArea, inputArea;
    private Color bgColor = Color.decode(Config.CHAT_WINDOW_BGCOLOR);
    private JButton sendBtn, clearBtn, shake;
    private Point p;

    /*
     * public static void main(String[] args) { User me = new User("小明",
     * "56685258"); me.setNickName("小强"); new ChatWindow(me, me, null); }
     */

    public ChatWindow(User me, User target) {
        super(580, 500, Color.decode(Config.CHAT_WINDOW_BGCOLOR));
        this.me = me;
        this.target = target;
        init();
        addEvent();
        this.setVisible(true);
    }

    private void init() {
        targetIcon = new ClientJLabel(10, 10, 48, 48, Config.DEFAULT_ICON_48);
        this.add(targetIcon);
        targetName = new ClientJLabel(target.getNickName(), 70, 20, 150, 20);
        this.add(targetName);
        msgArea = new JTextArea();
        msgArea.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        msgArea.setPreferredSize(new Dimension(560, 276));
        msgArea.setBackground(bgColor);
        msgArea.setEditable(false);
        msgScrollPane = new JScrollPane(msgArea);
        // 去掉边框
        msgScrollPane.setBorder(null);
        msgScrollPane.setBounds(0, 70, 580, 280);
        msgScrollPane.setAutoscrolls(true);
        // 设置鼠标滚动速度
        msgScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(msgScrollPane);

        inputArea = new JTextArea();
        inputArea.setBackground(bgColor);
        inputArea.setBounds(0, 350, 580, 110);
        this.add(inputArea);
        shake = new ClientJButton(300, 470, 70, 24, "震屏", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(shake);
        clearBtn = new ClientJButton(380, 470, 70, 24, "清除", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(clearBtn);
        sendBtn = new ClientJButton(460, 470, 70, 24, "发送", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(sendBtn);
        // WindowUtil.AddMouseDrag(this);
        // WindowUtil.AddCloseButton(this, Config.CLOSE_DEFAULT_IMG,
        // Color.decode(Config.MAIN_BOARD_BANNER_COLOR),
        // Config.CLOSE_WINDOW);
    }

    private void addEvent() {
        sendBtn.addActionListener(this);
        shake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                shake();
                Message msg = new Message();
                msg.setToPid(target.getPid());
                msg.setFromPid(me.getPid());
                OnlineUserManager.getOnlineUser(me).send(MessageType.SHAKE + JSONObject.fromObject(msg).toString());
            }
        });
        
        this.addWindowListener(new WindowAdapter() {
            
        });
    }

    public User getTarget() {
        return target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 发送按钮
        String content = inputArea.getText().trim();
        Message msg = new Message(content, me.getPid(), target.getPid(), new Date().getTime());
        inputArea.setText("");
        OnlineUserManager.getOnlineUser(me).send(MessageType.SEND_NORMAL_MESSAGE + JSONObject.fromObject(msg).toString());
    }

    /*
     * private void showMsg(MessageResult mr) { JSONObject jsonObj =
     * JSONObject.fromObject(mr.getJsonString()); switch (mr.getMessageType()) {
     * case MessageType.RECEIVE_NORMAL_MESSAGE: if (target.getPid() + "" ==
     * jsonObj.get("fromPid")) { Message msg = (Message)
     * JSONObject.toBean(jsonObj, Message.class); String date =
     * DateUtil.getTime(Config.MSG_DATE_FORMAT, msg.getSendTime());
     * msgArea.append(target.getNickName() + " " + date + "\n");
     * msgArea.append(msg.getContent() + "\n"); } break; case
     * MessageType.USER_ENTER: if (target.equals(jsonObj.get("user"))) {
     * targetIcon.setIcon(new ImageIcon(Config.DEFAULT_ICON_48)); } break; case
     * MessageType.USER_LOGOUT: if (target.equals(jsonObj.get("user"))) {
     * targetIcon.setIcon(new ImageIcon(Config.DEFAULT_OFFLINE_ICON_48)); }
     * break; case MessageType.SEND_TO_SUCCESS: break; case
     * MessageType.SEND_TO_ERROR_OFFLINE: break; case MessageType.SEND_TO_ERROR:
     * break; case MessageType.TIME_OUT: break; default: break; } }
     */

    public void addMessage(Message message) {
        String date = DateUtil.getTime(Config.MSG_DATE_FORMAT, message.getSendTime());
        msgArea.append(target.getNickName() + " " + date + "\n");
        msgArea.append(message.getContent() + "\n");
    }

    public void shake() {
        p = this.getLocationOnScreen();
        final double x = p.getX();
        final double y = p.getY();
        final int dis = 100;
        this.setAlwaysOnTop(true);
        new Thread(new Runnable() {
            int i = Config.SHAKE_TIMES;

            @Override
            public void run() {
                while (i > 0) {
                    ChatWindow.this.setLocation(new Point((int) (x - dis), (int) (y + dis)));
                    try {
                        Thread.sleep(Config.SHAKE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ChatWindow.this.setLocation(new Point((int) (x - dis), (int) (y - dis)));
                    try {
                        Thread.sleep(Config.SHAKE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ChatWindow.this.setLocation(new Point((int) (x + dis), (int) (y - dis)));
                    try {
                        Thread.sleep(Config.SHAKE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ChatWindow.this.setLocation(new Point((int) (x + dis), (int) (y + dis)));
                    try {
                        Thread.sleep(Config.SHAKE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                }
                ChatWindow.this.setLocation(p);
                ChatWindow.this.setAlwaysOnTop(false);
            }
        }).start();
    }
}
