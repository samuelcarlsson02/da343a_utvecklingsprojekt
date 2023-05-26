package server.model;

import model.Buffer;
import model.Message;
import model.User;
import server.controller.ControllerServer;

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

    public Client(Socket socket, ControllerServer controllerServer) {
        this.socket = socket;
        this.controllerServer = controllerServer;

        logger = new Logger();
        messageBuffer = new Buffer<>();
        try {
            new ClientOutputHandler(socket).start();
            new ClientInputHandler(socket).start();
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

    private class ClientOutputHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        public ClientOutputHandler(Socket socket) throws IOException {
            this.socket = socket;
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
        }

        public void run() {
            try {
                while (true) {
                    ois = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message) ois.readObject();
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
