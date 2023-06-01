package server.model;

import model.*;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Class that represents a client connected to the server.
 * @author Samuel Carlsson and Alexander Giheden
 */
public class Client
{
    private Socket socket;
    private Buffer<Message> messageBuffer;
    private Logger logger;
    private User user;
    private ControllerServer controllerServer;
    private ClientOutputHandler outputHandler;
    private ClientInputHandler inputHandler;

    /**
     * Constructor used to prepare a client for input and output to the server.
     * @param socket representing the client connection
     * @param ois used for receiving objects
     * @param user the user connected to the client
     * @param controllerServer controller object
     */
    public Client(Socket socket, ObjectInputStream ois, User user, ControllerServer controllerServer)
    {
        this.socket = socket;
        this.controllerServer = controllerServer;
        this.user = user;

        logger = new Logger(controllerServer);
        messageBuffer = new Buffer<>();
        try {
            outputHandler = new ClientOutputHandler(socket);
            inputHandler = new ClientInputHandler(ois);

            outputHandler.start();
            inputHandler.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles if a client log-outs or closes the chat to close the connection.
     */
    private void inputConnectionDropped()
    {
        outputHandler.interrupt();
        controllerServer.disconnectUser(user);
    }

    /**
     * Adds a message to the message buffer to be sent to the client.
     * @param message Message object to be sent to the client
     */
    public void addMessage(Message message)
    {
        messageBuffer.put(message);
    }

    /**
     * Handles output from the server to send it to the client.
     */
    private class ClientOutputHandler extends Thread
    {
        private Socket socket;
        private ObjectOutputStream oos;

        public ClientOutputHandler(Socket socket) throws IOException
        {
            this.socket = socket;
        }

        public void run()
        {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (!isInterrupted()) {
                    Message message = messageBuffer.get();

                    if (message instanceof OnlineUserList onlineUserList) {
                        oos.writeObject(onlineUserList);
                        oos.flush();
                        oos.reset();
                        logger.addLogEntry("Online users updated for " + user.getUsername());
                    } else if (message instanceof ChatMessage chatMessage) {
                        oos.writeObject(chatMessage);
                        oos.flush();
                        oos.reset();
                        chatMessage.setTimeReceived(controllerServer.getCurrentDateAndTime());
                        logger.addLogEntry("Message received for " + user.getUsername() + ": " + message);
                    } else if (message instanceof ContactList contactList) {
                        oos.writeObject(contactList);
                        oos.flush();
                        oos.reset();
                        logger.addLogEntry("Contacts updated for " + user.getUsername());
                    }
                }
            } catch (SocketException | SocketTimeoutException e) {
                try {
                    interrupt();
                    socket.close();
                } catch (IOException ex) {
                    e.printStackTrace();
                    ex.printStackTrace();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            outputHandler.interrupt();
        }
    }

    /**
     * Handles input from the client to send it to the server.
     */
    private class ClientInputHandler extends Thread
    {
        private ObjectInputStream ois;

        public ClientInputHandler(ObjectInputStream ois) throws IOException
        {
            this.ois = ois;
        }

        public void run()
        {
            try {
                while (!isInterrupted()) {
                    Message message = (Message) ois.readObject();

                    if (message instanceof ChatMessage chatMessage) {
                        logger.addLogEntry("Message sent: " + chatMessage);
                        controllerServer.handleMessage(chatMessage);
                    } else if (message instanceof ContactList contactList) {
                        logger.addLogEntry(user.getUsername() + " added " + contactList.getAddedContact() + " to contact list");
                        controllerServer.writeToContactList(contactList);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                try {
                    ois.close();
                    interrupt();
                    inputConnectionDropped();
                } catch (IOException ex) {
                    e.printStackTrace();
                    ex.printStackTrace();
                }
            }
        }
    }
}
