package server.controller;

import server.model.Message;
import server.model.User;
import server.view.ServerLogger;

import java.net.Socket;

public class ControllerServer {
    private User[] connectedUsers;
    private ServerLogger serverLogger;

    public void connectUser(User user, Socket clientSocket) {

    }

    public void disconnectUser(String username) {

    }

    public User[] getConnectedUsers() {
        return connectedUsers;
    }

    public void handleMessage(Message message) {

    }
}
