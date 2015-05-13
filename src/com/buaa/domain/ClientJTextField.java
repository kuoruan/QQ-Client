package com.buaa.domain;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;

import com.buaa.comman.Config;

public class ClientJTextField extends JTextField {
	private int x;
	private int y;
	private int width;
	private int height;
	private Color bgColor = Color.white;
	private Font font = new Font(Config.DEFAULT_FONT, 1, 14);
	private Color fontColor = Color.black;

	public ClientJTextField(int x, int y, int width, int height)
			throws HeadlessException {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}

	public ClientJTextField(int x, int y, int width, int height, Color bgColor,
			Color fontColor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgColor = bgColor;
		this.fontColor = fontColor;
	}

	public ClientJTextField(int x, int y, int width, int height, Font font,
			Color fontColor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.fontColor = fontColor;
	}

	public ClientJTextField(int x, int y, int width, int height, Color bgColor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgColor = bgColor;
	}

	public ClientJTextField(int x, int y, int width, int height, Color bgColor,
			Font font, Color fontColor) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgColor = bgColor;
		this.font = font;
		this.fontColor = fontColor;
	}

	private void init() {
		this.setBounds(x, y, width, height);
		this.setFont(font);
		this.setForeground(fontColor);
		this.setBackground(bgColor);
	}
}
