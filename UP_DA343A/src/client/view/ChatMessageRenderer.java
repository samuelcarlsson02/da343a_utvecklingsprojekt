package client.view;

import model.Message;

import javax.swing.*;
import java.awt.*;

public class ChatMessageRenderer extends JPanel implements ListCellRenderer<Message> {
    private JLabel usernameLabel;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private JLabel userPictureLabel;
    private JLabel messageImageLabel;

    public ChatMessageRenderer() {
        setLayout(new BorderLayout());
        setOpaque(true);

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
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus) {
        return null;
    }
}
