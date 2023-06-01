package model;

import javax.swing.*;
import java.io.Serializable;

public class Message implements Serializable {
    private User[] recipientList;
    private String text;
    private ImageIcon image;
    private String timeReceived;

    public String getText() {
        return text;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(String timeReceived) {
        this.timeReceived = timeReceived;
    }

    public User[] getRecipientList() {
        return recipientList;
    }
}
