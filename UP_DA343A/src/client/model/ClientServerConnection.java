package client.model;

import client.controller.ControllerClient;
import model.Message;
import model.User;
import server.model.Buffer;
import server.model.ServerHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientServerConnection {
    private ControllerClient controller;
    private ServerHandler serverHandler;
    private String ip;
    private int port;
    private Socket socket;
    private ClientInput clientInput;
    private ServerOutput serverOutput;

    public ClientServerConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new Socket(ip, port);
            clientInput = new ClientInput(socket);
            serverOutput = new ServerOutput(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean connectUser(User user) {
        boolean connected = serverHandler.connectUser(user);

        if (connected) {
            System.out.println("Connection established in ClientServerConnection");
            return connected;
        }

        return connected;
    }


    //sending data to server
    private class ClientInput extends Thread {
        private Socket socket;
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer = new Buffer<>();

        public ClientInput(Socket socket) {
            this.socket = socket;
            start();
        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                User user = controller.getLoggedInUser();
                Message message = new Message(user);
                oos.writeObject(message);
                oos.flush();
                System.out.println("User info sent");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // listening and receiving data from server
    private class ServerOutput extends Thread {
        private Socket socket;
        private ObjectInputStream ois;

        public ServerOutput(Socket socket) {
            this.socket = socket;
            start();
        }

        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try {
                    Object object = ois.readObject();

                    if (object instanceof Message message) {
                        controller.receiveMessage(message);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
