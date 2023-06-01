package server.controller;

import model.*;
import server.model.*;
import server.view.ServerLogger;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The ControllerServer class handles the server-side logic and communication with clients.
 * @author Samuel Carlsson and Alexander Giheden.
 */
public class ControllerServer {
    private ServerLogger serverLogger;
    private Logger logger;
    private Clients clients;
    private UnsentMessages unsentMessages;
    private FileManager fileManager;
    private ServerHandler serverHandler;
    private OnlineUserList onlineUserList;
    private ContactList contactList;
    private Client connectedClient;

    /**
     * Constructor that prepares the server.
     */
    public ControllerServer()
    {
        serverLogger = new ServerLogger(this);
        serverHandler = new ServerHandler(3343, this);
        fileManager = new FileManager();
        logger = new Logger(this);
        clients = new Clients();
        unsentMessages = new UnsentMessages();
        onlineUserList = new OnlineUserList();
        contactList = new ContactList();
    }

    public String getCurrentDateAndTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Connects a user to the server.
     * @param user         the User object representing the user.
     * @param ois          the ObjectInputStream for receiving data from the client.
     * @param clientSocket the Socket object representing the client's socket connection.
     */
    public void connectUser(User user, ObjectInputStream ois, Socket clientSocket) {
        logger.addLogEntry("User " + user.getUsername() + " is online.");

        connectedClient = new Client(clientSocket, ois, user, this);
        clients.put(user, connectedClient);
        onlineUserList.add(user);
        contactList = getContactList(user.getUsername());
        connectedClient.addMessage(contactList);

        if(unsentMessages.getMessages(user) != null){   //checks if there are any unsent messages for the user
            ArrayList<ChatMessage> unsentMessagesUser = unsentMessages.getMessages(user);
            for (int i = 0; i < unsentMessagesUser.size(); i++) {
                connectedClient.addMessage(unsentMessagesUser.get(i));
                unsentMessages.removeMessage(user, unsentMessagesUser.get(i));
            }
        }

        for (int i = 0; i < onlineUserList.getOnlineUsers().size(); i++) {     //updates users online
            Client client = clients.get(onlineUserList.getOnlineUsers().get(i));
            client.addMessage(onlineUserList);
        }
    }

    /**
     * Disconnects a user from the server.
     * @param user the User object representing the user.
     */
    public synchronized void disconnectUser(User user) {
        logger.addLogEntry("User " + user.getUsername() + " is offline.");
        onlineUserList.remove(user);
        clients.remove(user);

        for (int i = 0; i < onlineUserList.getOnlineUsers().size(); i++) {      //updates users online
            Client client = clients.get(onlineUserList.getOnlineUsers().get(i));
            client.addMessage(onlineUserList);
        }
    }

    /**
     * Handles a received message from a client.
     * @param message the Message object received from the client.
     */
    public synchronized void handleMessage(Message message) {
        //loops through recipientlist for this message and checks if the user is online or not
        //if online, sends message to the clients in the recipientlist
        //if offline puts it in the unsentmessages hashmap
        for (int i = 0; i < message.getRecipientList().length; i++) {
            User user = message.getRecipientList()[i];
            Client client = clients.get(user);

            if (client != null) {
                client.addMessage(message);
            } else {
                unsentMessages.putMessage(user, (ChatMessage) message);
            }
        }
    }

    /**
     * Returns the serverlog within the selected hours.
     * @param startTime hour to start showing logs from
     * @param endTime hour to stop showing logs from
     * @return log within the selected hours
     */
    public String[] getServerLog(String startTime, String endTime){
        return logger.getLogEntries(startTime, endTime);
    }

    /**
     * Retrieves the contact list for the specified username.
     * @param username the username of the user.
     * @return the ContactList object representing the user's contact list
     */
    public ContactList getContactList(String username){
        ArrayList<String> contacts = fileManager.readFromContacts(username, "contactList.txt");
        contactList.setContactList(contacts);

        return contactList;
    }

    /**
     * Writes a new contact for a user to the contactList.txt-file
     * @param contactList The ContactList object with the updated contact.
     */
    public void writeToContactList(ContactList contactList){
        ArrayList<String> contacts = contactList.getContacts();
        String username = contacts.get(0);
        for(int i = 1; i < contacts.size(); i++){
            String contact = contactList.getContacts().get(i);
            fileManager.writeToFile("contactList.txt", username + ": " + contact);
        }
    }
}
