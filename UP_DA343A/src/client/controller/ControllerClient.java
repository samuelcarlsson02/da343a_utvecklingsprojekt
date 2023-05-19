package client.controller;

import client.model.Message;
import client.view.ClientChat;
import client.view.LoginPanel;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerClient {
    private String username;
    private ImageIcon image;
    private LoginPanel loginPanel;
    private ClientChat clientChat;

    public ControllerClient(){
        loginPanel = new LoginPanel(this);
    }

    public boolean connectToServer(String username, ImageIcon image){
        return true;
    }

    public void loggedIn(){
        loginPanel.setVisible(false);
        clientChat = new ClientChat(this);
    }

    public void loggedOut(){
        clientChat.setVisible(false);
        loginPanel.setVisible(true);
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
