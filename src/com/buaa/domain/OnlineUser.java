package com.buaa.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.buaa.comman.MessageType;
import com.buaa.utils.ChatWindowManager;
import com.buaa.utils.MainBoardManager;
import com.buaa.utils.MessageUtil;
import com.buaa.view.ChatWindow;
import com.buaa.view.MainBoard;

public class OnlineUser implements Runnable {
    private User user;
    private List<User> list = new ArrayList<User>();
    private Socket socket;
    private OutputStream out;
    private BufferedReader br;
    private boolean isContinue = true;

    public OnlineUser(String username, String jsonString, Socket socket) {
        this.socket = socket;
        try {
            this.out = this.socket.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObj = JSONObject.fromObject(jsonString);
        JSONArray arr = JSONArray.fromObject(jsonObj.get("userList"));
        @SuppressWarnings("unchecked")
        List<User> tempList = JSONArray.toList(arr, new User(), new JsonConfig());
        // 获取自己
        for (User user : tempList) {
            if (username.equals(user.getAccount())) {
                this.user = user;
                break;
            }
        }
        // 获取好友列表
        for (User user : tempList) {
            if (!username.equals(user.getAccount())) {
                list.add(user);
            }
        }
        changeListOrder();
        send(MessageType.GET_OFFLINE_MSG, JSONObject.fromObject(user));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getList() {
        return list;
    }

    public void send(String str) {
        try {
            System.out.println("发送：" + str);
            out.write((str + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int t, JSONObject obj) {
        try {
            out.write((t + obj.toString() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isContinue) {
            try {
                String msg = br.readLine().trim();
                System.out.println("接收:" + msg);
                MessageResult mr = MessageUtil.analyzeMessage(msg);
                if (mr.isRight()) {
                    switch (mr.getMessageType()) {
                    case MessageType.RECEIVE_NORMAL_MESSAGE:// 收到转发消息
                        processReceiveMessage(mr.getJsonString());
                        break;
                    case MessageType.USER_ENTER:// 用户上线
                    case MessageType.USER_LOGOUT:// 用户上线
                        processUserChange(mr.getJsonString());
                        break;
                    case MessageType.RECEIVE_OFFLINE_MSG:
                        processOfflineMsg(mr.getJsonString());
                        break;
                    case MessageType.RECEIVE_SHAKE:// 震屏
                        processShake(mr.getJsonString());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    private void processOfflineMsg(String jsonString) {
        JSONArray arr = JSONArray.fromObject(jsonString);
        @SuppressWarnings("unchecked")
        List<Message> msgList = JSONArray.toList(arr, new Message(), new JsonConfig());
        for (Message msg : msgList) {
            processReceiveMessage(msg);
        }

    }

    private void close() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void processShake(String jsonString) {
        // 收到消息
        Message m = (Message) JSONObject.toBean(JSONObject.fromObject(jsonString), Message.class);
        // 判断该窗口是否打开
        User fromUser = getUser(m.getFromPid());
        if (ChatWindowManager.isOpened(fromUser)) {
            ChatWindow cw = ChatWindowManager.getOpened(fromUser);
            System.out.println("正常状态:" + cw.isShowing());
            if (cw.isShowing()) {
                cw.shake();
            }
            System.out.println("最小化:" + cw.isMinimumSizeSet());
            if (cw.isMinimumSizeSet()) {
                cw.setExtendedState(JFrame.NORMAL);
                cw.shake();
            }
        } else {// 如果窗口没打开
            ChatWindow chatWindow = new ChatWindow(user, fromUser);
            ChatWindowManager.addChatWindow(chatWindow);
            chatWindow.shake();
        }

    }

    private void processUserChange(String jsonString) {
        // 状态改变的用户
        User u = (User) JSONObject.toBean(JSONObject.fromObject(jsonString), User.class);
        // 将列表中的用户改变
        for (User user : list) {
            if (user.equals(u)) {
                user.setOnline(u.isOnline());
            }
        }
        // 将好友list重新排序，在线的放前边
        changeListOrder();

        // 通知每一个MainBoard有用户上线
        for (MainBoard board : MainBoardManager.list) {
            if (board != null) {
                board.refeshUser();
            }
        }

    }

    public void changeListOrder() {
        List<User> onlineUser = new ArrayList<User>();
        List<User> offlineUser = new ArrayList<User>();
        for (User user : list) {
            if (user.isOnline()) {
                onlineUser.add(user);
            } else {
                offlineUser.add(user);
            }
        }
        list.removeAll(list);
        for (User user : onlineUser) {
            list.add(user);
        }
        for (User user : offlineUser) {
            list.add(user);
        }
    }

    public List<User> getOnlineUsers() {
        List<User> onlineUser = new ArrayList<User>();
        for (User user : list) {
            if (user.isOnline()) {
                onlineUser.add(user);
            }
        }
        return onlineUser;
    }

    private void processReceiveMessage(String jsonString) {
        // 收到消息
        Message m = (Message) JSONObject.toBean(JSONObject.fromObject(jsonString), Message.class);
        processReceiveMessage(m);
    }

    private void processReceiveMessage(Message jsonString) {
        // 收到消息
        Message m = (Message) JSONObject.toBean(JSONObject.fromObject(jsonString), Message.class);
        // 判断该窗口是否打开
        User fromUser = getUser(m.getFromPid());
        if (ChatWindowManager.isOpened(fromUser)) {
            ChatWindow cw = ChatWindowManager.getOpened(fromUser);
            cw.addMessage(m, false);
        } else {// 如果窗口没打开
            ChatWindow chatWindow = new ChatWindow(user, fromUser);
            ChatWindowManager.addChatWindow(chatWindow);
            chatWindow.addMessage(m, false);
        }

    }

    public User getUser(int pid) {
        for (User user : list) {
            if (user.getPid() == pid) {
                return user;
            }
        }
        return null;
    }

    public void setContinue(boolean isContinue) {
        this.isContinue = isContinue;
    }
}
