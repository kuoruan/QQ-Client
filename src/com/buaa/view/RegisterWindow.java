package com.buaa.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.json.JSONObject;

import com.buaa.comman.Config;
import com.buaa.comman.MessageType;
import com.buaa.domain.ClientJButton;
import com.buaa.domain.ClientJDialog;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.ClientJPasswordField;
import com.buaa.domain.ClientJTextField;
import com.buaa.domain.MessageResult;
import com.buaa.domain.User;
import com.buaa.utils.ClientLink;
import com.buaa.utils.MessageUtil;

@SuppressWarnings("serial")
public class RegisterWindow extends ClientJDialog implements ActionListener {

    private JLabel banner, userLb, passwdLb, repasswdLb, error, nickName;
    private JTextField userTF, nickNameTF;
    private JPasswordField passwdFd, repasswdFd;
    private JButton register, clear;
    private ClientLink client;

    public RegisterWindow(int width, int height, ClientLink client) {
        super(width, height, Config.REGISTER_CLOSE_IMG, Config.CLOSE_WINDOW);
        this.client = client;
        init();
        addEvent();
        this.setVisible(true);
    }

    private void init() {
        error = new ClientJLabel("", 160, 105, 200, 30, 14, Color.red);
        this.add(error);
        // 添加BANNER
        banner = new ClientJLabel(0, 0, 430, 145, Config.REGISTER_BANNER);
        this.add(banner);
        // 添加输入框说明
        userLb = new ClientJLabel("用户名：", 55, 160, 75, 30);
        userLb.setHorizontalAlignment(SwingConstants.RIGHT);
        passwdLb = new ClientJLabel("密　码：", 55, 200, 75, 30);
        passwdLb.setHorizontalAlignment(SwingConstants.RIGHT);
        repasswdLb = new ClientJLabel("重复密码：", 55, 240, 75, 30);
        repasswdLb.setHorizontalAlignment(SwingConstants.RIGHT);
        nickName = new ClientJLabel("昵　称：", 55, 280, 75, 30);
        nickName.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(userLb);
        this.add(passwdLb);
        this.add(repasswdLb);
        this.add(nickName);
        // 添加输入框
        userTF = new ClientJTextField(160, 160, 200, 30);
        passwdFd = new ClientJPasswordField(160, 200, 200, 30);
        repasswdFd = new ClientJPasswordField(160, 240, 200, 30);
        nickNameTF = new ClientJTextField(160, 280, 200, 30);
        this.add(userTF);
        this.add(passwdFd);
        this.add(repasswdFd);
        this.add(nickNameTF);
        // 插入按钮
        register = new ClientJButton(160, 320, 95, 30, "注册", 16, Color.decode(Config.BLUE_BUTTON_COLOR), Color.white);
        clear = new ClientJButton(265, 320, 95, 30, "清空", 16, Color.decode(Config.BLUE_BUTTON_COLOR), Color.white);
        this.add(register);
        this.add(clear);
    }

    private void addEvent() {
        register.addActionListener(this);
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userTF.setText(null);
                passwdFd.setText(null);
                repasswdFd.setText(null);
                nickNameTF.setText(null);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client = new ClientLink(Config.SERVER_ADDRESS, Config.SERVER_PORT);
        String username = userTF.getText().trim();
        String passwd = new String(passwdFd.getPassword()).trim();
        String repasswd = new String(repasswdFd.getPassword());
        String nick = nickNameTF.getText().trim();
        if ("".equals(username) || "".equals(passwd) || "".equals(repasswd)) {
            error.setText("注册失败，帐号密码不能为空!");
        } else {
            if (repasswd.equals(passwd)) {
                JSONObject jsnObj = JSONObject.fromObject(new User(username, passwd, nick));
                client.output(MessageType.REGEDIT, jsnObj);
                showMsg(client.input());

            } else {
                error.setText("注册失败，确认密码不一致。");
            }
        }
    }

    private void showMsg(String str) {
        MessageResult mr = MessageUtil.analyzeMessage(str);
        if (mr.isRight()) {
            switch (mr.getMessageType()) {
            case MessageType.REGEDIT_SUCCESS:
                error.setText("注册成功！");
                error.setForeground(Color.green);
                try {
                    Thread.sleep(2000);
                    this.dispose();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case MessageType.REGEDIT_ERROR_SAME_USER:
                error.setText("注册失败，有相同的用户存在！");
                break;
            case MessageType.REGEDIT_ERROR:
                error.setText("注册失败，请重试。");
                break;
            case MessageType.TIME_OUT:
                error.setText("连接超时，注册失败。");
                break;
            default:
                error.setText("未知错误");
                break;
            }
            client.close();
        }
    }
}
