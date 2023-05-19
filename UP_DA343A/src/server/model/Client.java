package server.model;

import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void updateConnectedList(User[] userList) {

    }

    public void sendMessages(ArrayList<Message> messages) {

    }

    private class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            
        }
    }
}
