package com.buaa.domain;

import java.awt.Color;

import javax.swing.JFrame;

import com.buaa.comman.Config;
import com.buaa.utils.WindowUtil;

@SuppressWarnings("serial")
public class ClientJFrame extends JFrame {
    private int width ;
    private int height;
    private Color bgColor;

    public ClientJFrame(int width, int height, Color bgColor) {
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        init();
    }

    private void init() {
        this.setSize(width, height);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setBackground(bgColor);
        WindowUtil.AddMouseDrag(this);
        WindowUtil.AddCloseButton(this, Config.CLOSE_DEFAULT_IMG, Color.decode(Config.MAIN_BOARD_BANNER_COLOR),
                Config.CLOSE_WINDOW);
    }
}
