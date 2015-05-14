package com.buaa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.buaa.comman.Config;
import com.buaa.domain.BackGroundPanel;
import com.buaa.domain.ClientJDialog;
import com.buaa.domain.ClientJLabel;
import com.buaa.domain.ClientJTextField;
import com.buaa.domain.ClientLink;
import com.buaa.domain.FriendShow;
import com.buaa.domain.User;
import com.buaa.utils.DateUtil;

public class MainBoard extends ClientJDialog {
    private int width;
    private int height;
    private User me;
    private String username;
    private JPanel banner, search, bottom, friendPanel;
    private JLabel myIcon, myName, lastLogin, loginTime;
    private JTextField searchTF;
    private JScrollPane friendScroll;
    private String userJson;
    private ClientLink client;
    private List<User> users = new ArrayList<User>();
    private int friendPanelheight = 0;

    public static void main(String[] args) {
        new MainBoard(280, 700, null, "张三", null, Config.CLOSE_WINDOW);
    }

    public MainBoard(int width, int height, ClientLink client, String username, String userJson, int closeType) {
        super(width, height, closeType);
        this.width = width;
        this.height = height;
        this.client = client;
        this.username = username;
        this.userJson = userJson;
        prepare();
        init();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    public MainBoard() {
    }

    private void prepare() {
        me = new User("zhangsan1", "2258555566");
        userJson = "{\"userList\":[{\"account\":\"zhangsan1\",\"friend\":\"\",\"lastLoginTime\":1431338285543,\"nickName\":\"小名\",\"online\":false,\"password\":\"123456\",\"pid\":21,\"registTime\":1431338285543}]}";
        JSONObject jsonObj = JSONObject.fromObject(userJson);
        JSONArray arr = JSONArray.fromObject(jsonObj.get("userList"));
        users = JSONArray.toList(arr, new User(), new JsonConfig());
        ScrollList();
    }

    private void init() {
        banner = new JPanel();
        banner.setLayout(null);
        banner.setBackground(Color.decode(Config.MAIN_BOARD_BANNER_COLOR));
        banner.setBounds(0, 0, width, 115);
        myIcon = new ClientJLabel(10, 25, 64, 64, Config.DEFAULT_ICON_64);
        banner.add(myIcon);
        myName = new ClientJLabel(me.getAccount(), 80, 20, 150, 20, 14, Color.white);
        banner.add(myName);
        lastLogin = new ClientJLabel("登陆时间：", 80, 50, 70, 20, 12, Color.white);
        banner.add(lastLogin);
        loginTime = new ClientJLabel(DateUtil.getTime(Config.DEFAULT_DATE_FORMAT, me.getLastLoginTime()), 80, 70, 170,
                20, 14, Color.white);
        banner.add(loginTime);
        this.add(banner);
        search = new BackGroundPanel(0, 115, width, 67, Config.MAIN_BOARD_SEARCH);
        searchTF = new ClientJTextField(10, 3, width - 20, 25, Color.decode(Config.MAIN_BOARD_SEARCH_COLOR));
        searchTF.setBorder(null);
        search.add(searchTF);
        this.add(search);
        bottom = new BackGroundPanel(0, height - 60, width, 60, Config.MAIN_BOARD_BOTTOM);
        this.add(bottom);
    }

    private void ScrollList() {
        friendPanel = new JPanel();
        showList();
        // 设置friendPanel大小
        friendPanel.setPreferredSize(new Dimension(width - 20, friendPanelheight));
        // 创建friendScroll对象，将friendPanel加入进去
        friendScroll = new JScrollPane(friendPanel);
        friendScroll.setBounds(0, 182, width, height - 60 - 182);
        friendScroll.setBorder(null);
        friendScroll.setAutoscrolls(true);
        // 将friendPanel加入friendScroll
        // friendScroll.getViewport().add(friendPanel);
        // friendScroll.getViewport().setView(friendPanel);
        this.add(friendScroll);
    }

    private void showList() {
        for (User user : users) {
            if (username.equals(user.getAccount())) {
                this.me = user;
            } else {
                friendPanelheight += 60;
                JLabel friend = new FriendShow(width - 20, 60, user, me, client);
                friendPanel.add(friend);
            }
        }
    }
}
