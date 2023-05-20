package model;

import javax.swing.*;
import java.time.LocalDateTime;

public class Message {
    private User sender;
    private User[] recipientList;
    private String text;
    private ImageIcon image;
    private LocalDateTime timeReceived;
    private LocalDateTime timeDelivered;

    public Message(User sender, User[] recipientList, String text, ImageIcon image, LocalDateTime timeReceived, LocalDateTime timeDelivered) {
        this.sender = sender;
        this.recipientList = recipientList;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
        this.timeDelivered = timeDelivered;
    }

    public Message(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public ImageIcon getImage() {
        return image;
    }

    public User getUser() {
        return sender;
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
