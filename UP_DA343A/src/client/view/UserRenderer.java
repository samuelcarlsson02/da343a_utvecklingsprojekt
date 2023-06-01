package client.view;

import model.User;

import javax.swing.*;
import java.awt.*;

public class UserRenderer extends JLabel implements ListCellRenderer<User> {

    public UserRenderer()
    {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon imageIcon = user.getImage();

        if (imageIcon == null)
        {
            imageIcon = new ImageIcon();
        }
        setIcon(imageIcon);
        setText(user.getUsername());

        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
