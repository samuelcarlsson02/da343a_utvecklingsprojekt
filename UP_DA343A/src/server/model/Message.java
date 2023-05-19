package server.model;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;

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
