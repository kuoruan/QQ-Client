package com.buaa.utils;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.buaa.comman.Config;
import com.buaa.domain.ClientJButton;

public class WindowUtil {
    private static int xOld = 0;
    private static int yOld = 0;
    private static JButton closeButton;

    public static void AddMouseDrag(final Window window) {
        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();
                yOld = e.getY();
            }
        });
        window.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                int xx = xOnScreen - xOld;
                int yy = yOnScreen - yOld;
                window.setLocation(xx, yy);
            }
        });
    }

    public static void AddCloseButton(final Window window, final String mouseOutImg, Color bgColor, final int closeType) {
        final Icon out = new ImageIcon(mouseOutImg);
        closeButton = new ClientJButton(window.getWidth() - 30, 0, 30, 29, bgColor);
        closeButton.setIcon(new ImageIcon(mouseOutImg));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setIcon(new ImageIcon(Config.CLOSE_ON_DEFAULT_IMG));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setIcon(out);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (closeType) {
                case 1:
                    System.exit(0);
                    break;
                case 2:
                    window.dispose();
                    break;
                default:
                    break;
                }
            }
        });
        window.add(closeButton);
    }
}
