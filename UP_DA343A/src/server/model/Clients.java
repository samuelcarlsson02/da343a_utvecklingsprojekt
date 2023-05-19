package server.model;

import java.util.HashMap;

public class Clients {
    private HashMap<User, Client> clients = new HashMap<>();

    public synchronized void put(User user, Client client) {
        clients.put(user, client);
    }

    public synchronized Client get(User user) {
        return get(user);
    }

    public void updateConnectedList() {

    }

    public void remove(User user) {

    }
}
