package com.buaa.domain;

import java.awt.Window;

import javax.swing.JDialog;

import com.buaa.comman.Config;
import com.buaa.utils.WindowUtil;

public class ClientJDialog extends JDialog {
    private int x;
    private int y;
    private int width;
    private int height;
    private Window parent;
    private String closeIcon = Config.CLOSE_DEFAULT_IMG;
    private int closeType;

    public ClientJDialog(int width, int height, int closeType) {
        super();
        this.width = width;
        this.height = height;
        this.closeType = closeType;
        init();
    }

    public ClientJDialog(int x, int y, int width, int height, Window parent, String closeIcon, int closeType) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
        if (closeIcon != null) {
            this.closeIcon = closeIcon;
        }
        this.closeType = closeType;
        init();
    }

    public ClientJDialog(int x, int y, int width, int height, String closeIcon, int closeType) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (closeIcon != null) {
            this.closeIcon = closeIcon;
        }
        this.closeType = closeType;
        init();
    }

    /**
     * 初始化窗口并添加动作和部件
     */
    private void init() {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setLayout(null);
        this.setLocationRelativeTo(parent);
        // 设置窗口无边框
        this.setUndecorated(true);
        // 设置要显示的内容
        // frame.setContentPane(myPane);
        WindowUtil.AddMouseDrag(this);
        WindowUtil.AddCloseButton(this, closeIcon, Config.CLOSE_ON_DEFAULT_IMG, closeType);
    }
}
