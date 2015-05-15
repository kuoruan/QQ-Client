package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Date;

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
import com.buaa.utils.OnlineUserManager;

@SuppressWarnings("serial")
public class ChatWindow extends ClientJFrame implements ActionListener {
    private User me;
    private User target;
    private JScrollPane msgScrollPane;
    private JPanel msgPanel, msg;
    private JLabel targetName, targetIcon;
    private JTextArea inputArea;
    private Color bgColor = Color.decode(Config.CHAT_WINDOW_BGCOLOR);
    private JButton sendBtn, clearBtn, shake;
    private Point p;
    private int msgHeight = 0;

    // public static void main(String[] args) {
    // User u1 = new User();
    // u1.setNickName("小康康");
    // User u2 = new User();
    // u2.setNickName("大大大");
    // new ChatWindow(u1, u2);
    // }

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
        msgPanel = new JPanel();
        // msgPanel.setPreferredSize(new Dimension(560, 276));
        msgPanel.setBackground(bgColor);
        // msgPanel.setSize(560,280);
        msgScrollPane = new JScrollPane(msgPanel);
        // 去掉边框
        msgScrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                Color.decode(Config.CHAT_WINDOW_BORDER_COLOR)));
        msgScrollPane.setBounds(0, 70, 580, 280);
        msgScrollPane.setAutoscrolls(true);
        // 设置鼠标滚动速度
        msgScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(msgScrollPane);
        inputArea = new JTextArea();
        inputArea.setBackground(bgColor);
        inputArea.setBounds(0, 350, 580, 110);
        inputArea.setMargin(new Insets(10, 10, 10, 10));
        // 自动换行
        inputArea.setLineWrap(true);
        this.add(inputArea);
        shake = new ClientJButton(300, 470, 70, 24, "震屏", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(shake);
        clearBtn = new ClientJButton(380, 470, 70, 24, "清除", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(clearBtn);
        sendBtn = new ClientJButton(460, 470, 70, 24, "发送", 14, Color.decode(Config.BLUE_BUTTON_COLOR), null);
        this.add(sendBtn);
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
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == Frame.ICONIFIED) {
                    p = new Point(0, 0);
                }
            }
        });
        clearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputArea.setText(null);
            }
        });
    }

    public User getTarget() {
        return target;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 发送按钮
        String content = inputArea.getText().trim();
        if (!"".equals(content)) {// 如果内容为空
            Message msg = new Message(content, me.getPid(), target.getPid(), new Date().getTime());
            inputArea.setText("");
            addMessage(msg, true);
            OnlineUserManager.getOnlineUser(me).send(
                    MessageType.SEND_NORMAL_MESSAGE + JSONObject.fromObject(msg).toString());
        }
    }

    public void addMessage(Message message, boolean isSend) {
        String date = DateUtil.getTime(Config.MSG_DATE_FORMAT, message.getSendTime());
        JLabel icon, time, content;
        content = new JLabel("<html>" + message.getContent() + "</html>");
        content.setFont(new Font(Config.DEFAULT_FONT, Font.TRUETYPE_FONT, 14));
        View labelView = BasicHTML.createHTMLView(content, content.getText());
        labelView.setSize(135, 14);
        int trueHight = (int) labelView.getMinimumSpan(View.Y_AXIS);
        if (isSend) {
            icon = new ClientJLabel(512, 3, 32, 32, Config.DEFAULT_ICON_32);
            time = new ClientJLabel(date, 380, 0, 200, 15, 10, Color.decode(Config.DEFAULT_DATE_COLOR));
            content.setBounds(250, 20, 250, trueHight);
            content.setHorizontalAlignment(JLabel.RIGHT);
        } else {
            icon = new ClientJLabel(10, 3, 32, 32, Config.DEFAULT_ICON_32);
            time = new ClientJLabel(date, 50, 0, 200, 15, 10, Color.decode(Config.DEFAULT_DATE_COLOR));
            content.setBounds(50, 20, 250, trueHight);
        }

        trueHight += 30;
        msg = new JPanel();
        msg.setLayout(null);
        msg.add(time);
        msg.add(icon);
        msg.add(content);
        msg.setPreferredSize(new Dimension(560, trueHight));
        msgHeight = msgHeight + trueHight + 5;
        System.out.println(msgHeight);
        freshMsg();
    }

    private void freshMsg() {
        msgPanel.add(msg);
        msgPanel.setPreferredSize(new Dimension(560, msgHeight));
        msgPanel.validate();
        msgScrollPane.validate();
        JScrollBar sBar = msgScrollPane.getVerticalScrollBar();
        // 自动滚动到最下面
        sBar.setValue(sBar.getMaximum());
    }

    /**
     * 窗口抖动
     * 
     * @Title: shake
     * @param:
     * @return: void
     * @throws
     */
    public void shake() {
        p = this.getLocationOnScreen();
        final double x = p.getX();
        final double y = p.getY();
        final int dis = 5;
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
