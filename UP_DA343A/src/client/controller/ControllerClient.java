package client.controller;

import client.model.ClientServerConnection;
import model.ContactList;
import model.ChatMessage;
import model.OnlineUserList;
import model.User;
import client.view.ClientChat;
import client.view.LoginPanel;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        loginPanel = new LoginPanel(this);
        clientChat = new ClientChat(this);
    }

    public boolean connectToServer(String username, ImageIcon image, String ip, int port){
        loggedInUser = new User(username, image);
        clientServerConnection = new ClientServerConnection(this, ip, port);
        contactList.addContact(loggedInUser.getUsername());

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
        clientServerConnection.disconnect();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendMessage(String messageText, ImageIcon image, User[] recipientList){
        ChatMessage message = new ChatMessage(getLoggedInUser(), recipientList, messageText, image,
                                        getCurrentDateAndTime(), getCurrentDateAndTime());

        clientServerConnection.addMessage(message);
        clientChat.showSentMessage(message);
    }

    public String getCurrentDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void disconnect() {

    }

    public void receiveMessage(ChatMessage message) {
        clientChat.showNewMessage(message);
    }

    public void updateOnlineUsers(OnlineUserList userList) {
        for (int i = 0; i < userList.getOnlineUsers().size(); i++) {
            System.out.println(userList.getOnlineUsers().get(i).getUsername());
        }
        clientChat.displayConnectedUsers(userList.getOnlineUsers());
    }

    public void updateContactList(ContactList contactList){
        clientChat.displayContactList(contactList.getContacts());
    }

    public ArrayList<String> getContactList(){
        return contactList.getContacts();
    }

    public void addToContactList(String contact){
        contactList.addContact(contact);
        clientServerConnection.addContactList(contactList);
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
