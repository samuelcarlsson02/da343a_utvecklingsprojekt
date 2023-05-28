package model;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User sender;
    private User[] recipientList;
    private String text;
    private ImageIcon image;
    private String timeReceived;
    private String timeDelivered;
    private String username;
    private ImageIcon userPicture;

    public Message(User sender, User[] recipientList, String text, ImageIcon image, String timeReceived, String timeDelivered) {
        this.sender = sender;
        this.recipientList = recipientList;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
        this.timeDelivered = timeDelivered;
    }

    public Message(String username, ImageIcon userPicture, String text, ImageIcon image, String timeReceived){
        this.username = username;
        this.userPicture = userPicture;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
    }

    public Message(){

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

    public String getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(String timeReceived) {
        this.timeReceived = timeReceived;
    }

    public String getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(String timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public User[] getRecipientList() {
        return recipientList;
    }
}
