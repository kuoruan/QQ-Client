package com.buaa.domain;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;
    private String imageUrl;

    public BackGroundPanel(int x, int y, int width, int height, String imageUrl) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
        init();
    }

    private void init() {
        this.setLayout(null);
        this.setBounds(x, y, width, height);
    }

    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件
    protected void paintComponent(Graphics g) {
        Image bgImg = new ImageIcon(imageUrl).getImage();
        g.drawImage(bgImg, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
