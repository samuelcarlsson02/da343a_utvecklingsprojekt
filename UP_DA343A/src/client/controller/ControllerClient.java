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

public class ControllerClient {
    private User loggedInUser;
    private LoginPanel loginPanel;
    private ClientChat clientChat;
    private ContactList contactList;
    private OnlineUserList onlineUserList;
    private ClientServerConnection clientServerConnection;

    public ControllerClient(){
        onlineUserList = new OnlineUserList();
        loginPanel = new LoginPanel(this);
        clientChat = new ClientChat(this);
    }

    public boolean connectToServer(String username, ImageIcon image, String ip, int port){
        loggedInUser = new User(username, image);
        clientServerConnection = new ClientServerConnection(this, ip, port);
        initializeContacts(loggedInUser.getUsername());

        boolean connected = clientServerConnection.connectUser(loggedInUser);

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
        onlineUserList.remove(loggedInUser);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendMessage(String messageText, ImageIcon image, User[] recipientList){
        ChatMessage message = new ChatMessage(getLoggedInUser(), recipientList, messageText, image,
                                        getCurrentDateAndTime(), getCurrentDateAndTime());

        clientServerConnection.addMessage(message);
        clientChat.showMessage(message);
    }

    public String getCurrentDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void receiveMessage(ChatMessage message) {
        clientChat.showMessage(message);
    }

    public void updateOnlineUsers(OnlineUserList userList) {
        onlineUserList = userList;
        clientChat.displayConnectedUsers(onlineUserList.getOnlineUsers());
    }

    public void updateContactList(ContactList contactList){
        clientChat.displayContactList(contactList.getContacts());
    }

    public void addToContactList(String contact){
        contactList.addContact(contact);
        clientServerConnection.addMessage(contactList);
        initializeContacts(loggedInUser.getUsername());
    }

    public void initializeContacts(String username){
        contactList = new ContactList();
        contactList.addContact(username);
    }
}
