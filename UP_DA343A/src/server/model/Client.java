package server.model;

import model.*;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client
{
    private Socket socket;
    private Buffer<Message> messageBuffer;
    private Logger logger;
    private User user;
    private ControllerServer controllerServer;
    private ClientOutputHandler outputHandler;
    private ClientInputHandler inputHandler;

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

    private void inputConnectionDropped()
    {
        outputHandler.interrupt();
        controllerServer.disconnectUser(user);
    }

    public void addMessage(Message message)
    {
        messageBuffer.put(message);
    }

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
