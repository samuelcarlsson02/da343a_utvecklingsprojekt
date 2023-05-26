package server.model;

import model.User;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Clients {
    private HashMap<User, Client> clients = new HashMap<>();

    public synchronized void put(User user, Client client) {
        clients.put(user, client);
    }

    public synchronized Client get(User user) {
        return clients.get(user);
    }

    public void updateConnectedList() {

    }

    public void remove(User user) {
        clients.remove(user);
    }
}
