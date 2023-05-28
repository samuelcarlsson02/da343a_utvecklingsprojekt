package client.controller;

import client.model.ClientServerConnection;
import client.model.ContactList;
import model.ChatMessage;
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
        onlineUserList = new OnlineUserList();
        contactList.readFromFile("contactList");

        loginPanel = new LoginPanel(this);
        clientChat = new ClientChat(this);
    }

    public boolean connectToServer(String username, ImageIcon image, String ip, int port){
        loggedInUser = new User(username, image);
        clientServerConnection = new ClientServerConnection(this, ip, port);

        boolean connected = clientServerConnection.connectUser(loggedInUser);
        if (connected) {
            System.out.println("Connection established in ControllerClient");
            return connected;
        }

        return connected;
    }

    public void loggedIn(){
        loginPanel.setVisible(false);
        clientChat.setVisible(true);
    }

    public void loggedOut(){
        clientChat.setVisible(false);
        loginPanel.setVisible(true);
        clientServerConnection.disconnect(loggedInUser);
        onlineUserList.remove(loggedInUser);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendMessage(String messageText, ImageIcon image, User[] recipientList){
        ChatMessage message = new ChatMessage(getLoggedInUser(), recipientList, messageText, image,
                                        LocalDateTime.now(), LocalDateTime.now());

        clientServerConnection.addMessage(message);
    }

    public void disconnect() {

    }

    public void receiveMessage(ChatMessage message) {
        clientChat.showNewMessage(message);
    }

    public void updateOnlineUsers(OnlineUserList userList) {
        onlineUserList = userList;
        for (int i = 0; i < onlineUserList.getOnlineUsers().size(); i++) {
            System.out.println(onlineUserList.getOnlineUsers().get(i).getUsername());
        }
        clientChat.displayConnectedUsers(onlineUserList.getOnlineUsers());
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
