package server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class UnsentMessages {
    private HashMap<User, ArrayList<Message>> unsentMessages;

    public synchronized void putMessage(User user, Message message) {

    }

    public synchronized ArrayList<Message> getMessage(User user) {

    }
}
