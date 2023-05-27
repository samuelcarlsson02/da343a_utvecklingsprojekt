package client.view;

import model.ChatMessage;
import model.Message;

import javax.swing.*;
import java.awt.*;

public class ChatMessageRenderer extends JLabel implements ListCellRenderer<ChatMessage> {
    private JLabel usernameLabel;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private JLabel userPictureLabel;
    private JLabel messageImageLabel;

    public ChatMessageRenderer() {
       setOpaque(true);

        setLayout(new BorderLayout());
        usernameLabel = new JLabel();
        messageLabel = new JLabel();
        timeLabel = new JLabel();
        userPictureLabel = new JLabel();
        messageImageLabel = new JLabel();

        add(usernameLabel, BorderLayout.NORTH);
        add(messageLabel, BorderLayout.CENTER);
        add(timeLabel, BorderLayout.SOUTH);
        add(userPictureLabel, BorderLayout.WEST);
        add(messageImageLabel, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ChatMessage> list, ChatMessage message, int index, boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = message.getImage();
        if (imageIcon == null)
        {
            imageIcon = new ImageIcon();
        }
        setIcon(imageIcon);

        String chat = message.getUser().getUsername() + " (" + message.getTimeReceived().toString() + ") \n" + message.getText();
        setText(chat);

        return this;
    }
}
