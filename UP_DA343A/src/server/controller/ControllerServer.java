package server.controller;

import server.model.FileManager;
import server.model.Message;
import server.model.User;
import server.view.ServerLogger;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerServer {
    private ArrayList<User> connectedUsers;
    private ServerLogger serverLogger;
    private FileManager fileManager;

    public ControllerServer(){
        serverLogger = new ServerLogger(this);
        fileManager = new FileManager();
    }

    public void connectUser(User user, Socket clientSocket) {

    }

    public void disconnectUser(String username) {

    }

    public ArrayList<User> getConnectedUsers() {
        String path = System.getProperty("user.dir");
        String fileName = path + "\\files\\connectedUsers.txt";
        ArrayList<String> usersString = fileManager.readFromFile(fileName);

        for(int i = 0; i < usersString.size(); i++){
            String[] split = usersString.get(i).split(",");
            String username = split[0];
            String picture = split[1];
            connectedUsers.add(new User(username, new ImageIcon(path + picture)));
        }

        return connectedUsers;
    }

    public void handleMessage(Message message) {

    }
}
