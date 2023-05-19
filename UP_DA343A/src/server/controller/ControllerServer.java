package server.controller;

import model.Message;
import model.User;
import server.model.Client;
import server.model.Clients;
import server.model.Logger;
import server.model.UnsentMessages;
import server.view.ServerLogger;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControllerServer {
    private User[] connectedUsers;
    private ServerLogger serverLogger;
    private Logger logger;
    private Clients clients;
    private UnsentMessages unsentMessages;
    private ArrayList<Message> messages;

    public ControllerServer(){
        serverLogger = new ServerLogger(this);
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

    public User[] getConnectedUsers() {
        return connectedUsers;
    }

    public void handleMessage(Message message) {

    }
}
