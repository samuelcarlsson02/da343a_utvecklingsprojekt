package client.model;

import client.controller.ControllerClient;
import model.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientServerConnection {
    private ControllerClient controller;
    private String ip;
    private int port;
    private Socket socket;
    private ClientInput clientInput;
    private ServerOutput serverOutput;

    public ClientServerConnection(ControllerClient controller, String ip, int port) {
        this.controller = controller;
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

        if(socket.isConnected()) {
            return true;
        }
        return false;
    }

    public void disconnect(){
        clientInput.interrupt();
        serverOutput.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!isInterrupted()) {
                try {
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                } catch (InterruptedException e) {
                    interrupt();
                } catch (IOException ex) {
                    ex.printStackTrace();
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
                    Object obj = ois.readObject();

                    if (obj instanceof ChatMessage chatMessage) {
                        controller.receiveMessage(chatMessage);
                    } else if (obj instanceof OnlineUserList onlineUserList) {
                        controller.updateOnlineUsers(onlineUserList);
                    } else if (obj instanceof ContactList contactList){
                        controller.updateContactList(contactList);
                    }

                } catch (EOFException | SocketException e) {
                    interrupt();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
