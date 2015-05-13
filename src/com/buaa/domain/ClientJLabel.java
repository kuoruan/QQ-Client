package com.buaa.domain;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.buaa.comman.Config;

public class ClientJLabel extends JLabel {
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private String imgIcon;
	private int size = 14;
	private Color fontColor = Color.black;


	public ClientJLabel(String name, int x, int y, int width, int height,
			int size, Color fontColor) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.size = size;
		this.fontColor = fontColor;
		init();
	}


	public ClientJLabel(String name, int x, int y, int width, int height) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}
	

	public ClientJLabel(String name, int x, int y, int width, int height,
			int size) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.size = size;
		init();
	}

	public ClientJLabel(String name, int x, int y, int width, int height,
			Color fontColor) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fontColor = fontColor;
		init();
	}


	public ClientJLabel(int x, int y, int width, int height, String imgIcon) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.imgIcon = imgIcon;
		init();
	}
	private void init() {
		this.setText(name);
		this.setIcon(new ImageIcon(imgIcon));
		this.setBorder(null);
		this.setBounds(x, y, width, height);
		this.setFont(new Font(Config.DEFAULT_FONT, 1, size));
		this.setForeground(fontColor);
	}
}
