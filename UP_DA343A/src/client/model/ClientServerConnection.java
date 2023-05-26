package client.model;

import client.controller.ControllerClient;
import model.Message;
import model.OnlineUserList;
import model.User;
import model.Buffer;
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
            System.out.println("kom hit serverconnection innan");
            socket = new Socket(ip, port);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean connectUser(User user) {
        clientInput = new ClientInput(socket);
        serverOutput = new ServerOutput(socket);

        clientInput.setUser(user);
        clientInput.start();

        if (socket.isConnected()) {
            System.out.println("Connection established in ClientServerConnection");
            return true;
        }
        return false;
    }

    public void disconnectUser() {
        try {
            if (clientInput != null) {
                clientInput.stopThread();
            }

            if (serverOutput != null) {
                serverOutput.stopThread();
            }

            if (socket != null) {
                socket.close();
                socket = null;
            }
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
        private boolean running;

        public ClientInput(Socket socket) {
            this.socket = socket;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void addMessage(Message message) {
            messageBuffer.put(message);
        }

        public void stopThread(){
            running = false;
            try{
                if(socket != null){
                    if(oos != null){
                        oos.close();
                    }
                    socket.close();
                    socket = null;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(user);
                oos.flush();
                System.out.println("User info sent");
                running = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (running) {
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
        private boolean running;

        public ServerOutput(Socket socket) {
            this.socket = socket;
            start();
        }
        public void stopThread(){
            running = false;
            try{
                if(socket != null){
                    if(ois != null){
                        ois.close();
                    }
                    socket.close();
                    socket = null;
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }


        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                running = true;
            } catch (IOException e) {
                if (socket.isClosed() && socket != null) {
                    running = false;
                    return;
                }
                throw new RuntimeException(e);
            }

            while (running) {
                try {
                    if(!socket.isClosed() && socket.getInputStream().available() > 0){
                        Object object = ois.readObject();

                        if (object instanceof Message message) {
                            controller.receiveMessage(message);
                        } else if (object instanceof OnlineUserList onlineUserList) {
                            controller.updateOnlineUsers(onlineUserList);
                        }
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