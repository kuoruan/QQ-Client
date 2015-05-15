package com.buaa.utils;

import java.util.ArrayList;
import java.util.List;

import com.buaa.domain.User;
import com.buaa.view.MainBoard;

public class MainBoardManager {
    public static List<MainBoard> list = new ArrayList<MainBoard>();

    public static void addMainBoard(MainBoard board) {
        list.add(board);
    }

    public static MainBoard getMainBoard(User user) {
        for (MainBoard board : list) {
            if (board.getMe().equals(user)) {
                return board;
            }
        }
        return null;
    }
}
