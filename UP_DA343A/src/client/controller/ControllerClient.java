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

/**
 * The ControllerClient class handles the client-side logic and interactions in the connected client.
 * @author Samuel Carlsson and Alexander Giheden
 */
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

    /**
     * Connects to the server with the provided username, image, IP address, and port number.
     * @param username The username of the logged-in user.
     * @param image    The image associated with the logged-in user.
     * @param ip       The IP address of the server.
     * @param port     The port number of the server.
     * @return true if the connection to the server is successful, false otherwise.
     */
    public boolean connectToServer(String username, ImageIcon image, String ip, int port){
        loggedInUser = new User(username, image);
        clientServerConnection = new ClientServerConnection(this, ip, port);
        initializeContacts(loggedInUser.getUsername());

        boolean connected = clientServerConnection.connectUser(loggedInUser);

        return connected;
    }

    /**
     * Handles what happens when a user logs in.
     */
    public void loggedIn(){
        loginPanel.setVisible(false);
        clientChat.setVisible(true);
    }

    /**
     * Handles what happens when a user logs out.
     */
    public void loggedOut(){
        clientChat.setVisible(false);
        loginPanel.setVisible(true);
        clientServerConnection.disconnect();
        onlineUserList.remove(loggedInUser);
    }

    /**
     * Returns the logged-in user.
     * @return The logged-in User object.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Sends a message to clientServerConnection with the given message text, image, and recipient list.
     *
     * @param messageText    The text of the message.
     * @param image          The image associated with the message.
     * @param recipientList  The array of users who are the recipients of the message.
     */
    public void sendMessage(String messageText, ImageIcon image, User[] recipientList){
        ChatMessage message = new ChatMessage(getLoggedInUser(), recipientList, messageText, image,
                                        getCurrentDateAndTime(), getCurrentDateAndTime());

        clientServerConnection.addMessage(message);
        clientChat.showMessage(message);
    }

    /**
     * Gets current date and time.
     * @return current date and time
     */
    public String getCurrentDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Receives a message from the server and displays it in the client chat window.
     * @param message The received ChatMessage object.
     */
    public void receiveMessage(ChatMessage message) {
        clientChat.showMessage(message);
    }

    /**
     * Updates the list of online users and displays it in the client chat window.
     * @param userList The updated OnlineUserList object.
     */
    public void updateOnlineUsers(OnlineUserList userList) {
        onlineUserList = userList;
        clientChat.displayConnectedUsers(onlineUserList.getOnlineUsers());
    }

    /**
     * Updates the contact list and displays it in the client chat window.
     * @param contactList The updated ContactList object.
     */
    public void updateContactList(ContactList contactList){
        clientChat.displayContactList(contactList.getContacts());
    }

    /**
     * Adds a contact to the contact list and updates the server and GUI.
     * @param contact The contact to add.
     */
    public void addToContactList(String contact){
        contactList.addContact(contact);
        clientServerConnection.addMessage(contactList);
        initializeContacts(loggedInUser.getUsername());
    }

    /**
     * Resets the contact list array and adds the logged-in username.
     * @param username logged-in user
     */
    public void initializeContacts(String username){
        contactList = new ContactList();
        contactList.addContact(username);
    }
}
