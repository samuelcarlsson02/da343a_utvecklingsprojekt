package server.model;

import model.ChatMessage;
import model.Message;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UnsentMessages {
    private HashMap<User, ChatMessage> unsentMessages;

    public UnsentMessages(){
        unsentMessages = new HashMap<>();
    }

    public synchronized void putMessage(User user, ChatMessage message) {
        unsentMessages.put(user, message);
    }

    public synchronized Message getMessage(User user) {
        return unsentMessages.get(user);
    }

    public synchronized void removeMessage(User user, ChatMessage message){
        unsentMessages.remove(user, message);
    }

    public ArrayList<ChatMessage> getUnsentMessagesFromUser(User user) {
        ArrayList<ChatMessage> userMessages = new ArrayList<>();

        for (User u : unsentMessages.keySet()) {
            if (u.equals(user)) {
                userMessages.add(unsentMessages.get(u));
            }
        }

        return userMessages;
    }
}
