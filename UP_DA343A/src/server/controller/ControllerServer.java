package server.controller;

import server.model.FileManager;
import model.Message;
import model.User;
import server.model.Client;
import server.model.Clients;
import server.model.Logger;
import server.model.UnsentMessages;
import server.view.ServerLogger;

import javax.swing.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControllerServer {
    private ArrayList<User> connectedUsers;
    private ServerLogger serverLogger;
    private Logger logger;
    private Clients clients;
    private UnsentMessages unsentMessages;
    private ArrayList<Message> messages;
    private FileManager fileManager;

    public ControllerServer(){
        serverLogger = new ServerLogger(this);
        fileManager = new FileManager();
        logger = new Logger();
        clients = new Clients();

    }

    public boolean connectUser(User user, Socket clientSocket) {
        logger.addLogEntry("User connected at ControllerServer at: " + LocalDateTime.now());

        Client client = new Client(clientSocket);
        clients.put(user, client);

        unsentMessages = new UnsentMessages();
        messages = new ArrayList<>();
        messages = unsentMessages.getMessage(user);

        client.sendMessages(messages);

        return true;
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
