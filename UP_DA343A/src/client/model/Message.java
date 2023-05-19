package client.model;

import server.model.User;

import javax.swing.*;
import java.time.LocalDateTime;

public class Message {
    private server.model.User sender;
    private server.model.User[] recipientList;
    private String text;
    private ImageIcon image;
    private LocalDateTime timeReceived;
    private LocalDateTime timeDelivered;

    public Message(server.model.User sender, server.model.User[] recipientList, String text, ImageIcon image, LocalDateTime timeReceived, LocalDateTime timeDelivered) {
        this.sender = sender;
        this.recipientList = recipientList;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
        this.timeDelivered = timeDelivered;
    }

    public LocalDateTime getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(LocalDateTime timeReceived) {
        this.timeReceived = timeReceived;
    }

    public LocalDateTime getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(LocalDateTime timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public User[] getRecipientList() {
        return recipientList;
    }
}
