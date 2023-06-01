package server.model;

import model.User;

import java.util.HashMap;

public class Clients {
    private HashMap<User, Client> clients = new HashMap<>();

    public synchronized void put(User user, Client client) {
        clients.put(user, client);
    }

    public synchronized Client get(User user) {
        return clients.get(user);
    }

    public synchronized void remove(User user) {
        clients.remove(user);
    }
}
