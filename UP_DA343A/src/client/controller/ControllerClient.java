package client.controller;

import client.model.ClientServerConnection;
import client.model.ContactList;
import model.Message;
import model.OnlineUserList;
import model.User;
import client.view.ClientChat;
import client.view.LoginPanel;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControllerClient {
    private String username;
    private ImageIcon image;
    private User loggedInUser;
    private LoginPanel loginPanel;
    private ClientChat clientChat;
    private ContactList contactList;
    private OnlineUserList onlineUserList;
    ClientServerConnection clientServerConnection;

    public ControllerClient(){
        contactList = new ContactList();
        contactList.readFromFile("contactList");
        onlineUserList = new OnlineUserList();

        loginPanel = new LoginPanel(this);
    }

    public boolean connectToServer(String username, ImageIcon image, String ip, int port){
        loggedInUser = new User(username, image);
        clientServerConnection = new ClientServerConnection(ip, port);

        boolean connected = clientServerConnection.connectUser(loggedInUser);
        if (connected) {
            System.out.println("Connection established in ControllerClient");
            return connected;
        }

        return connected;
    }

    public void loggedIn(){
        loginPanel.setVisible(false);
        clientChat = new ClientChat(this);
    }

    public void loggedOut(){
        clientChat.setVisible(false);
        loginPanel.setVisible(true);
        clientServerConnection.disconnect();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendMessage(String messageText, ImageIcon image, User[] recipientList){
        Message message = new Message(getLoggedInUser(), recipientList, messageText, image,
                                        LocalDateTime.now(), LocalDateTime.now());

        clientServerConnection.addMessage(message);
    }

    public void disconnect() {

    }

    public void receiveMessage(Message message) {
        clientChat.showNewMessage(message.getText(), message.getImage(), message.getUser().getUsername(),
                                    message.getUser().getImage(), message.getTimeReceived());
    }

    public void updateOnlineUsers(OnlineUserList userList) {
        ArrayList<String> users = new ArrayList<>();
        ArrayList<ImageIcon> profilePictures = new ArrayList<>();
        ArrayList<User> onlineUsers = userList.getOnlineUsers();

        for (int i = 0; i < onlineUsers.size(); i++) {
            users.add(onlineUsers.get(i).getUsername());
            profilePictures.add(onlineUsers.get(i).getImage());
        }
        clientChat.displayConnectedUsers(users, profilePictures);
    }

    public ArrayList<String> getContactList(){
        return contactList.getContacts();

    }

    public void addToContactList(String username){
        contactList.addContact(username);
    }

    public ArrayList<String> getConnectedUsers(){
        ArrayList<String> users = new ArrayList<>();
        ArrayList<User> onlineUsers = onlineUserList.getOnlineUsers();

        for (int i = 0; i < onlineUsers.size(); i++) {
            users.add(onlineUsers.get(i).getUsername());
        }

        return users;
    }
}
