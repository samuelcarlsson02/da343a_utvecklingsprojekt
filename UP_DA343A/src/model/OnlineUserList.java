package model;

import java.util.ArrayList;

public class OnlineUserList {
    private ArrayList<User> onlineUsers;

    public OnlineUserList() {
        onlineUsers = new ArrayList<>();
    }

    public void add(User user) {
        onlineUsers.add(user);
    }

    public void remove(User user) {
        onlineUsers.remove(user);
    }

    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }
}
