package server.model;

import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private Buffer<Message> messageBuffer;
    private Logger logger;

    public Client(Socket socket) {
        this.socket = socket;
        logger = new Logger();
        messageBuffer = new Buffer<>();
        try {
            new ClientHandler(socket).start();
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

    private class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket) throws IOException
        {
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
}
