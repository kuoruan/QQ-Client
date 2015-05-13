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
	private Window parent = null;

	public ClientJDialog(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.setSize(width, height);
		init();
	}

	public ClientJDialog(int x, int y, int width, int height, Window parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.setBounds(x, y, width, height);
		init();
	}

	/**
	 * 初始化窗口并添加动作和部件
	 */
	private void init() {
		this.setLayout(null);
		this.setLocationRelativeTo(parent);
		this.setUndecorated(true);
		WindowUtil.AddMouseDrag(this);
		WindowUtil.AddCloseButton(this, Config.CLOSE_DEFAULT_IMG,
				Config.CLOSE_ON_DEFAULT_IMG, Config.CLOSE_SYSTEM);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Window getParent() {
		return parent;
	}

	public void setParent(Window parent) {
		this.parent = parent;
	}

}
