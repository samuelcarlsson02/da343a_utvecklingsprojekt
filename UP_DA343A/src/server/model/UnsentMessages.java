package server.model;

import model.ChatMessage;
import model.Message;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnsentMessages {
    private HashMap<User, ArrayList<ChatMessage>> unsentMessages;
    private ArrayList<ChatMessage> messages;

    public UnsentMessages(){
        unsentMessages = new HashMap<>();
        messages = new ArrayList<>();
    }

    public synchronized void putMessage(User user, ChatMessage message) {
        messages.add(message);
        unsentMessages.put(user, messages);
    }

    public synchronized ArrayList<ChatMessage> getMessages(User user) {
        return unsentMessages.get(user);
    }

    public synchronized void removeMessage(User user, ChatMessage message){
        messages.remove(message);
        unsentMessages.put(user, messages);
    }
}
