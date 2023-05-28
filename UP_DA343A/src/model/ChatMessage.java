package model;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatMessage extends Message{
    private User sender;
    private User[] recipientList;
    private String text;
    private ImageIcon image;
    private String timeReceived;
    private String timeDelivered;
    private String username;
    private ImageIcon userPicture;

    public ChatMessage(String username, ImageIcon userPicture, String text, ImageIcon image, String timeReceived){
        this.username = username;
        this.userPicture = userPicture;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
    }

    public ChatMessage(User sender, User[] recipientList, String text, ImageIcon image, String timeReceived, String timeDelivered) {
        this.sender = sender;
        this.recipientList = recipientList;
        this.text = text;
        this.image = image;
        this.timeReceived = timeReceived;
        this.timeDelivered = timeDelivered;
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

    public String toString(){
        ArrayList<String> recipientsString = new ArrayList<>();
        for (int i = 0; i < recipientList.length; i++) {
            recipientsString.add(recipientList[i].getUsername());
        }

        String formattedRecipientList = String.join(", ", recipientsString);

        return String.format("'%s', from: %s, to: %s", text, sender.getUsername(), formattedRecipientList);
    }
}
