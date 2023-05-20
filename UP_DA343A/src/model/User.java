package model;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private ImageIcon image;

    public User(String username, ImageIcon image) {
        this.username = username;
        this.image = image;
    }

    public int hashCode() {
        return username.hashCode();
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof User) {
            return username.equals(((User)object).getUsername());
        }

        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}