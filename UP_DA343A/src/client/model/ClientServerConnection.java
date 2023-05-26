package client.model;

import client.controller.ControllerClient;
import model.Message;
import model.OnlineUserList;
import model.User;
import model.Buffer;
import server.model.ServerHandler;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
        clientInput.setUser(user);
        clientInput.start();

        if (socket.isConnected()) {
            System.out.println("Connection established in ClientServerConnection");
            return true;
        }
        return false;
    }

    public void disconnect(){
        clientInput.interrupt();
        serverOutput.interrupt();
    }

    public void addMessage(Message message) {
        clientInput.addMessage(message);
    }


    //sending data to server
    private class ClientInput extends Thread {
        private Socket socket;
        private User user;
        private ObjectOutputStream oos;
        private Buffer<Message> messageBuffer = new Buffer<>();

        public ClientInput(Socket socket) {
            this.socket = socket;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void addMessage(Message message) {
            messageBuffer.put(message);
        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(user);
                oos.flush();
                System.out.println("User info sent");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!isInterrupted()) {
                try {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
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

            while (!isInterrupted()) {
                try {
                    Object object = ois.readObject();

                    if (object instanceof Message message) {
                        controller.receiveMessage(message);
                    } else if (object instanceof OnlineUserList onlineUserList) {
                        controller.updateOnlineUsers(onlineUserList);
                    }

                } catch (EOFException | SocketException e) {
                    e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
