package com.buaa.domain;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import com.buaa.comman.Config;

@SuppressWarnings("serial")
public class ClientJButton extends JButton {
    private int x;
    private int y;
    private int width;
    private int height;
    private String name;
    private int size = 14;
    private Color bgColor;
    private Color fontColor = Color.white;

    public ClientJButton(int x, int y, int width, int height, String name, int size, Color bgColor, Color fontColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.size = size;
        this.bgColor = bgColor;
        if (fontColor != null) {
            this.fontColor = fontColor;
        }
        init();
    }

    public ClientJButton(int x, int y, int width, int height, String name, int size) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.size = size;
        init();
    }

    public ClientJButton(int x, int y, int width, int height, String name, Color bgColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        init();
    }

    public ClientJButton(int x, int y, int width, int height, Color bgColor) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bgColor = bgColor;
        init();
    }

    private void init() {
        this.setBounds(x, y, width, height);
        this.setText(name);
        this.setBorder(null);
        this.setFocusPainted(false);
        this.setFont(new Font(Config.DEFAULT_FONT, Font.BOLD, size));
        this.setBackground(bgColor);
        this.setForeground(fontColor);
    }
}
