package com.buaa.domain;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import com.buaa.comman.Config;

public class ClientJTextField extends JTextField {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color bgColor = Color.white;
    private int fontSize = 14;
    private Color fontColor = Color.black;

    public ClientJTextField(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    public ClientJTextField(int x, int y, int width, int height, Color bgColor, Color fontColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.fontColor = fontColor;
        init();
    }

    public ClientJTextField(int x, int y, int width, int height, int fontSize, Color fontColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        init();
    }

    public ClientJTextField(int x, int y, int width, int height, Color bgColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        init();
    }

    public ClientJTextField(int x, int y, int width, int height, Color bgColor, int fontSize, Color fontColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        init();
    }

    private void init() {
        this.setBounds(x, y, width, height);
        this.setFont(new Font(Config.DEFAULT_FONT, Font.TRUETYPE_FONT, fontSize));
        this.setForeground(fontColor);
        this.setBackground(bgColor);
    }
}
