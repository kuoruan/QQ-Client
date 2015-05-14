package com.buaa.view;

import com.buaa.domain.ClientLink;
import com.buaa.domain.User;

public class ChatWindow {
    private User me;
    private User friend;
    private ClientLink client;

    public ChatWindow(User me, User friend, ClientLink client) {
        super();
        this.me = me;
        this.friend = friend;
        this.client = client;
    }

    public ChatWindow() {
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
