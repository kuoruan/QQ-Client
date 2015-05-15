package com.buaa.utils;

import java.util.ArrayList;
import java.util.List;

import com.buaa.domain.User;
import com.buaa.view.ChatWindow;

public class ChatWindowManager {
    private static List<ChatWindow> list = new ArrayList<ChatWindow>();

    public static void addChatWindow(ChatWindow chatWindow) {
        list.add(chatWindow);
    }

    public static boolean isOpened(User user) {
        for (ChatWindow chatWindow : list) {
            if (chatWindow.getTarget().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public static ChatWindow getOpened(User user) {
        for (ChatWindow chatWindow : list) {
            if (chatWindow.getTarget().equals(user)) {
                return chatWindow;
            }
        }
        return null;
    }
    public static void remove(ChatWindow c) {
        list.remove(c);
    }
}
