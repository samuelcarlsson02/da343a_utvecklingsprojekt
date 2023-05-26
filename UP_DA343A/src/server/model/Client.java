package server.model;

import model.Buffer;
import model.Message;
import model.User;
import server.controller.ControllerServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private Buffer<Message> messageBuffer;
    private Logger logger;
    private ControllerServer controllerServer;
    private ClientOutputHandler clientOutputHandler;
    private ClientInputHandler clientInputHandler;

    public Client(Socket socket, ControllerServer controllerServer) {
        this.socket = socket;
        this.controllerServer = controllerServer;

        logger = new Logger();
        messageBuffer = new Buffer<>();
        try {
            clientOutputHandler = new ClientOutputHandler(socket);
            clientInputHandler = new ClientInputHandler(socket);
            clientOutputHandler.start();
            clientInputHandler.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateConnectedList(User[] userList) {

    }

    public void sendMessages(ArrayList<Message> messages) {
        for (Message message : messages) {
            messageBuffer.put(message);
        }
    }

    public void sendMessage(Message message) {
        messageBuffer.put(message);
    }

    public void disconnect(){
        try {
            socket.close();
            clientOutputHandler.disconnect();
            clientInputHandler.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private class ClientOutputHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        public ClientOutputHandler(Socket socket) throws IOException
        {
            this.socket = socket;
        }

        public void disconnect() {
            try {
                socket.close(); // Close the main socket

                // Close the output stream socket
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                // Handle the exception or log the error message
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                    logger.addLogEntry("Message: '" + message + "' sent at: " + LocalDateTime.now());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class ClientInputHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;

        public ClientInputHandler(Socket socket) throws IOException
        {
            this.socket = socket;
            ois = new ObjectInputStream(socket.getInputStream());
        }

        public void disconnect() {
            try {
                socket.close(); // Close the main socket

                // Close the input stream socket
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                // Handle the exception or log the error message
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    message.setTimeReceived(LocalDateTime.now());
                    logger.addLogEntry("Message: '" + message + "' sent at: " + LocalDateTime.now());
                    controllerServer.handleMessage(message);
                } catch (EOFException e) {
                    // Handle end of stream (connection closed) gracefully
                    User user = controllerServer.getClients().getUserBySocket(socket);
                    if(user != null){
                        logger.addLogEntry("Connection closed for user: " + socket.getInetAddress());
                        controllerServer.disconnectUser(user);
                    }
                    break;  // Exit the loop
                } catch (Exception e) {
                    // Handle other exceptions
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
