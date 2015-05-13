package com.buaa.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import com.buaa.domain.ClientLink;
import com.buaa.domain.MessageResult;
import com.buaa.domain.User;
import com.buaa.utils.MessageUtil;

/**
 * 登录窗口
 * 
 * @ClassName: LoginWindow
 * @author: Liao
 * @date: 2015年5月13日 下午9:36:04
 *
 */
public class LoginWindow extends ClientJDialog implements ActionListener {

    ClientLink client;
    JLabel banner, icon, register, forget, error;
    JTextField userTF;
    JPasswordField passwdF;
    JButton loginBtn;
    User user;

    public static void main(String[] args) {
        new LoginWindow(430, 345);
    }

    public LoginWindow(int width, int height) {
        super(width, height, Config.CLOSE_SYSTEM);
        prepare();
        init();
        addEvent();
        this.setVisible(true);
    }

    /**
     * 创建服务器连接
     * 
     * @Title: prepare
     * @param:
     * @return: void
     * @throws
     */
    private void prepare() {
        // client = new ClientLink(Config.SERVER_ADDRESS, Config.SERVER_PORT);
    }

    private void init() {
        banner = new ClientJLabel(0, 0, 430, 182, Config.LOGIN_BANNER);
        this.add(banner);
        icon = new ClientJLabel(30, 200, 72, 72, Config.DEFAULT_ICON_72);
        this.add(icon);
        userTF = new ClientJTextField(120, 200, 200, 30);
        this.add(userTF);
        passwdF = new ClientJPasswordField(120, 240, 200, 30);
        this.add(passwdF);
        register = new ClientJLabel("注册帐号", 330, 200, 60, 30, Color.decode(Config.LINK_FONT_COLOR));
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forget = new ClientJLabel("忘记密码", 330, 240, 60, 30, Color.decode(Config.LINK_FONT_COLOR));
        forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(register);
        this.add(forget);
        loginBtn = new ClientJButton(120, 280, 200, 30, "登　录", 16, Color.decode(Config.BLUE_BUTTON_COLOR), Color.white);
        this.add(loginBtn);
        error = new ClientJLabel("", 120, 320, 200, 20, 12, Color.red);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(error);
        this.setAlwaysOnTop(true);
    }

    private void addEvent() {
        loginBtn.addActionListener(this);
        passwdF.addActionListener(this);
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginWindow.this.dispose();
                JDialog regWindow = new RegisterWindow(430, 345, client);
                regWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        LoginWindow.this.setVisible(true);
                    }
                });
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loginBtn.setText("登录中。。。");
        loginBtn.setBackground(Color.decode("#B8CFE5"));
        loginBtn.validate();
        String username = userTF.getText().trim();
        String passwd = new String(passwdF.getPassword()).trim();
        if ("".equals(username) || "".equals(passwd)) {
            showError("用户名密码不能为空！");
        } else {
            if (client == null) {
                client = new ClientLink(Config.SERVER_ADDRESS, Config.SERVER_PORT);
            }
            user = new User(username, passwd);
            JSONObject jsnObj = JSONObject.fromObject(user);
            client.output(MessageType.LOGIN, jsnObj);
            String msg = client.input();
            MessageResult mr = MessageUtil.analyzeMessage(msg);
            if (mr.isRight()) {
                switch (mr.getMessageType()) {
                case MessageType.LOGIN_SUCCESS:
                    break;
                case MessageType.LOGIN_ERROR:
                    showError("登录失败！");
                    break;
                case MessageType.LOGIN_AREADY:
                    showError("用户已经登录！");
                    break;
                case MessageType.ERROR:
                    showError("未知错误，请重试！");
                    break;
                case MessageType.TIME_OUT:
                    showError("连接服务器超时！");
                    break;
                default:
                    showError("请重新登录！");
                    break;
                }
            }
        }
    }

    private void showError(String errorMsg) {
        error.setText(errorMsg);
        loginBtn.setText("登　录");
        loginBtn.setBackground(Color.decode(Config.BLUE_BUTTON_COLOR));
    }
}
