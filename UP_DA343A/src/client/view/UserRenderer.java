package client.view;

import model.User;

import javax.swing.*;
import java.awt.*;

public class UserRenderer extends JPanel implements ListCellRenderer<User> {
    private JLabel usernameLabel;
    private JLabel profilePictureLabel;

    public UserRenderer() {
        setLayout(new BorderLayout());
        setOpaque(true);

        usernameLabel = new JLabel();
        profilePictureLabel = new JLabel();

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.add(profilePictureLabel);
        userPanel.add(usernameLabel);

        add(userPanel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
        return null;
    }
}
