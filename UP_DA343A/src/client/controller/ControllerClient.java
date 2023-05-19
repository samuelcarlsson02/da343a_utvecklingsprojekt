package client.controller;

import client.model.ClientServerConnection;
import client.model.ContactList;
import model.Message;
import model.User;
import client.view.ClientChat;
import client.view.LoginPanel;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerClient {
    private String username;
    private ImageIcon image;
    private User loggedInUser;
    private LoginPanel loginPanel;
    private ClientChat clientChat;
    private ContactList contactList;

    public ControllerClient(){
        contactList = new ContactList();
        contactList.readFromFile("contactList");

        loginPanel = new LoginPanel(this);
    }

    public boolean connectToServer(String username, ImageIcon image){
        String ip = loginPanel.getIp();
        int port = loginPanel.getPort();

        loggedInUser = new User(username, image);
        ClientServerConnection clientServerConnection = new ClientServerConnection(ip, port);

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
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendMessage(Message message){

    }

    public void disconnect(){

    }

    public void receiveMessage(Message message){

    }

    public void updateConnectedUsers(ArrayList<String> users){

    }

    public ArrayList<String> getContactList(){
        return null;
    }

    public void addToContactList(String username){

    }

    public ArrayList<String> getConnectedUsers(){
        return null;
    }
}
