package com.buaa.utils;

import java.util.ArrayList;
import java.util.List;

import com.buaa.domain.OnlineUser;
import com.buaa.domain.User;

public class OnlineUserManager {
    public static List<OnlineUser> list = new ArrayList<OnlineUser>();

    public static void addOnlineUser(OnlineUser user) {
        list.add(user);
    }

    public static User getUser(String name) {
        for (OnlineUser onlineUser : list) {
            if (onlineUser.getUser().getAccount().equals(name)) {
                return onlineUser.getUser();
            }
        }
        return null;
    }

    public static OnlineUser getOnlineUser(User user) {
        for (OnlineUser onlineUser : list) {
            if (onlineUser.getUser().equals(user)) {
                return onlineUser;
            }
        }
        return null;
    }
}
