package server.model;

import model.*;
import server.controller.ControllerServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private Buffer<Message> messageBuffer;
    private Logger logger;
    private ControllerServer controllerServer;
    private ClientOutputHandler outputHandler;
    private ClientInputHandler inputHandler;

    public Client(Socket socket, ObjectInputStream ois, ControllerServer controllerServer) {
        this.socket = socket;
        this.controllerServer = controllerServer;

        logger = new Logger();
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

    public void updateConnectedList(OnlineUserList onlineUserList) {
        messageBuffer.put(onlineUserList);
    }

    public void sendMessages(ArrayList<Message> messages) {
        for (Message message : messages) {
            messageBuffer.put(message);
        }
    }

    public void sendMessage(Message message) {
        messageBuffer.put(message);
    }

    private class ClientOutputHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        public ClientOutputHandler(Socket socket) throws IOException {
            this.socket = socket;
        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (!isInterrupted()) {
                    Message message = messageBuffer.get();

                    if (message instanceof OnlineUserList onlineUserList)
                    {
                        oos.writeObject(onlineUserList);
                        oos.flush();
                        oos.reset();
                        System.out.println("Size when sending to client = " + onlineUserList.getOnlineUsers().size());
                    } else if (message instanceof ChatMessage chatMessage) {
                        oos.writeObject(chatMessage);
                        oos.flush();
                        oos.reset();
                        logger.addLogEntry("Message: '" + message + "' sent at: " + LocalDateTime.now());
                    }
                }
            } catch (SocketException | SocketTimeoutException e) {
               try {
                   socket.close();
                   interrupt();
               } catch (IOException ex) {
                   e.printStackTrace();
                   ex.printStackTrace();
               }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            outputHandler.interrupt();
        }
    }

    private class ClientInputHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;

        public ClientInputHandler(ObjectInputStream ois) throws IOException
        {
            this.ois = ois;
        }

        public void run() {
            try {
                while (!isInterrupted()) {
                    ChatMessage message = (ChatMessage) ois.readObject();
                    message.setTimeReceived(LocalDateTime.now());
                    logger.addLogEntry("Message: '" + message + "' sent at: " + LocalDateTime.now());
                    controllerServer.handleMessage(message);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
