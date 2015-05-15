package com.buaa.domain;

import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class ClientJPasswordField extends JPasswordField {
    private int x;
    private int y;
    private int width;
    private int height;

    public ClientJPasswordField(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        this.setBounds(x, y, width, height);
    }
}
