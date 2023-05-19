package server.model;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Message {
    private User sender;
    private User[] recipientList;
    private String text;
    private ImageIcon image;
    private Date timeReceived;
    private Date timeDelivered;

    public Message(User sender, User[] recipientList, String text, ImageIcon image, Date timeReceived, Date timeDelivered) {
        this.sender = sender;
        this.recipientList = recipientList;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
        this.timeDelivered = timeDelivered;
    }

    public Date getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(Date timeReceived) {
        this.timeReceived = timeReceived;
    }

    public Date getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(Date timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public User[] getRecipientList() {
        return recipientList;
    }
}
