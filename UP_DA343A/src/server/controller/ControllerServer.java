package server.controller;

import model.ContactList;
import model.OnlineUserList;
import server.model.*;
import model.Message;
import model.User;
import server.view.ServerLogger;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControllerServer {
    private ArrayList<User> connectedUsers;
    private ServerLogger serverLogger;
    private Logger logger;
    private Clients clients;
    private UnsentMessages unsentMessages;
    private ArrayList<Message> messages;
    private FileManager fileManager;
    private ServerHandler serverHandler;
    private OnlineUserList onlineUserList;
    private ContactList contactList;
    private Client connectedClient;

    public ControllerServer() throws UnknownHostException
    {
        serverLogger = new ServerLogger(this);
        serverHandler = new ServerHandler(3343, this);
        fileManager = new FileManager();
        logger = new Logger(this);
        clients = new Clients();
        unsentMessages = new UnsentMessages();
        onlineUserList = new OnlineUserList();
        contactList = new ContactList();

        System.out.println(InetAddress.getLocalHost());
    }

    public String getCurrentDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public boolean connectUser(User user, ObjectInputStream ois, Socket clientSocket) {
        logger.addLogEntry("User " + user.getUsername() + " is online.");

        connectedClient = new Client(clientSocket, ois, user, this);
        clients.put(user, connectedClient);
        onlineUserList.add(user);
        contactList = getContactList(user.getUsername());
        connectedClient.updateContactList(contactList);

        for (int i = 0; i < onlineUserList.getOnlineUsers().size(); i++) {
            System.out.println(onlineUserList.getOnlineUsers().get(i).getUsername());
            Client client = clients.get(onlineUserList.getOnlineUsers().get(i));
            client.updateConnectedList(onlineUserList);
        }

        //messages = new ArrayList<>();
        //messages = unsentMessages.getMessage(user);

        //client.sendMessages(messages);

        return true;
    }

    public synchronized void disconnectUser(User user) {
        System.out.println("Removing connection: " + user);
        logger.addLogEntry("User " + user.getUsername() + " is offline.");
        onlineUserList.remove(user);
        clients.remove(user);

        for (int i = 0; i < onlineUserList.getOnlineUsers().size(); i++) {
            System.out.println(onlineUserList.getOnlineUsers().get(i).getUsername());
            Client client = clients.get(onlineUserList.getOnlineUsers().get(i));
            client.updateConnectedList(onlineUserList);
        }
    }

    public ArrayList<User> getConnectedUsers() {
      /*  String path = System.getProperty("user.dir");
        String fileName = path + "\\files\\connectedUsers.txt";
        ArrayList<String> usersString = fileManager.readFromFile(fileName);

        for(int i = 0; i < usersString.size(); i++){
            String[] split = usersString.get(i).split(",");
            String username = split[0];
            String picture = split[1];
            connectedUsers.add(new User(username, new ImageIcon(path + picture)));
        }

        return connectedUsers;*/
        return null;
    }

    public synchronized void handleMessage(Message message) {
        //loops through recipientlist for this message and checks if the user is online or not
        //if online, sends message to the clients in the recipientlist
        //if offline puts it in the unsentmessages hashmap
        for (int i = 0; i < message.getRecipientList().length; i++) {
            User user = message.getRecipientList()[i];
            Client client = clients.get(user);

            if (client != null) {
                System.out.println("Recipient found");
                client.sendMessage(message);
            } else {
                System.out.println("Recipient not found");
                unsentMessages.putMessage(user, message);
            }
        }
    }

    public String[] getServerLog(String startTime, String endTime){
        return logger.getLogEntries(startTime, endTime);
    }

    public ContactList getContactList(String username){
        ArrayList<String> contacts = fileManager.readFromFile(username, "contactList.txt");
        contactList.setContactList(contacts);

        return contactList;
    }

    public void writeToContactList(ContactList contactList){
        ArrayList<String> contacts = contactList.getContacts();
        String username = contacts.get(0);
        for(int i = 1; i < contacts.size(); i++){
            String contact = contactList.getContacts().get(i);
            fileManager.writeToFile("contactList.txt", username + ": " + contact);
        }
    }
}
