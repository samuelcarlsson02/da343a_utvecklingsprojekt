package client.view;

import client.controller.ControllerClient;
import model.ChatMessage;
import model.Message;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientChat extends javax.swing.JFrame {
    private ControllerClient controllerClient;
    private DefaultListModel<ChatMessage> chatModel;
    private DefaultListModel<User> userModel;
    private DefaultListModel<String> receiverModel;
    private DefaultListModel<String> contactListModel;
    private ChatMessageRenderer chatMessageRenderer;
    private ImageIcon selectedImage;


    public ClientChat(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
        initComponents();
    }

    private void initComponents() {
        jScrollPane2 = new javax.swing.JScrollPane();
        messagePane = new javax.swing.JTextPane();
        choosePictureBtn = new javax.swing.JButton();
        sendMessageBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        usersOnlineList = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        contactsList = new javax.swing.JList<>();
        usersOnlineTxt = new javax.swing.JLabel();
        addContactBtn = new javax.swing.JButton();
        contactTxt = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        receiversList = new javax.swing.JList<>();
        receiversTxt = new javax.swing.JLabel();
        addToReceiverBtn = new javax.swing.JButton();
        chatTxt = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        chatMessagesList = new javax.swing.JList<>();

        userModel = new DefaultListModel<>();
        usersOnlineList.setCellRenderer(new UserRenderer());

        receiverModel = new DefaultListModel<>();

        contactListModel = new DefaultListModel<>();

        chatModel = new DefaultListModel<>();
        chatMessageRenderer = new ChatMessageRenderer();
        chatMessagesList.setCellRenderer(chatMessageRenderer);
        chatMessagesList.setModel(chatModel);
        jScrollPane6.setViewportView(chatMessagesList);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setViewportView(messagePane);

        choosePictureBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        choosePictureBtn.setText("Choose picture");
        choosePictureBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choosePictureBtnActionPerformed(evt);
            }
        });

        sendMessageBtn.setBackground(new java.awt.Color(204, 204, 204));
        sendMessageBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sendMessageBtn.setText("Send");
        sendMessageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageBtnActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(usersOnlineList);

        contactsList.setModel(contactListModel);
        jScrollPane4.setViewportView(contactsList);

        usersOnlineTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        usersOnlineTxt.setText("Users online");

        addContactBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addContactBtn.setText("Add contact");
        addContactBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactBtnActionPerformed(evt);
            }
        });

        contactTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        contactTxt.setText("Contacts");

        logoutBtn.setBackground(new java.awt.Color(255, 51, 51));
        logoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        logoutBtn.setText("Log out");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        receiversList.setModel(receiverModel);
        jScrollPane5.setViewportView(receiversList);

        receiversTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        receiversTxt.setText("Receivers");

        addToReceiverBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addToReceiverBtn.setText("Add to receiver list");
        addToReceiverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiverBtnActionPerformed(evt);
            }
        });

        chatTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        chatTxt.setText("Chat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(chatTxt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(logoutBtn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(receiversTxt)
                                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(12, 12, 12)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(choosePictureBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(sendMessageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane3)
                                                        .addComponent(jScrollPane4)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(usersOnlineTxt)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(contactTxt)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                                                                .addComponent(addContactBtn))
                                                        .addComponent(addToReceiverBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(9, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(logoutBtn)
                                                .addGap(27, 27, 27))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(chatTxt)
                                                .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(choosePictureBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(receiversTxt)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                        .addComponent(sendMessageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(usersOnlineTxt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addContactBtn)
                                                        .addComponent(contactTxt))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                                .addGap(14, 14, 14)
                                                .addComponent(addToReceiverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>

    private void sendMessageBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if(receiverModel.size() > 0){
            User[] receivers = new User[receiversList.getModel().getSize()];

            for (int i = 0; i < receiversList.getModel().getSize(); i++)
            {
                User receiver = new User(receiversList.getModel().getElementAt(i), null);
                receivers[i] = receiver;
            }

            controllerClient.sendMessage(messagePane.getText(), selectedImage, receivers);

            receiversList.setModel(new DefaultListModel<>());
            receiverModel.clear();
            messagePane.setText(null);
            choosePictureBtn.setIcon(null);
            choosePictureBtn.setText("Choose picture");
        }
        else{
            JOptionPane.showMessageDialog(null, "select at least one receiver");
        }
    }

    private void choosePictureBtnActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = new JFrame("Image Chooser");
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            Image image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
            Image resizedImage = image.getScaledInstance(choosePictureBtn.getWidth(), choosePictureBtn.getHeight(), Image.SCALE_REPLICATE);
            selectedImage = new ImageIcon(resizedImage);

            choosePictureBtn.setText(null);
            choosePictureBtn.setIcon(selectedImage);
        }
    }

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt){
        clearEverything();
        controllerClient.loggedOut();
    }

    public void clearEverything(){
        receiversList.setModel(new DefaultListModel<>());
        receiverModel.clear();
        contactsList.setModel(new DefaultListModel<>());
        contactListModel.clear();
        usersOnlineList.setModel(new DefaultListModel<>());
        userModel.clear();
        chatMessagesList.setModel(new DefaultListModel<>());
        chatModel.clear();

        messagePane.setText(null);
        choosePictureBtn.setIcon(null);
        choosePictureBtn.setText("Choose picture");
    }

    public void showNewMessage(ChatMessage chatMessage){
        System.out.println(chatMessage.getUser().getUsername() + " " + chatMessage.getTimeReceived() + " " + chatMessage.getText());
        chatModel.addElement(chatMessage);

        chatMessagesList.setModel(chatModel);
    }

    public void showSentMessage(ChatMessage chatMessage){
        chatModel.addElement(chatMessage);
        chatMessagesList.setModel(chatModel);
    }

    public void displayConnectedUsers(ArrayList<User> usersOnline){
        userModel.clear();
        for (int i = 0; i < usersOnline.size(); i++) {
            if (!usersOnline.get(i).getUsername().equals(controllerClient.getLoggedInUser().getUsername()))
            {
                userModel.addElement(new User(usersOnline.get(i).getUsername(), usersOnline.get(i).getImage()));
            }
        }
        usersOnlineList.setModel(userModel);
    }

    private void receiverBtnActionPerformed(java.awt.event.ActionEvent evt){
        if(contactsList.getSelectedValue() != null){
            receiverModel.addElement(contactsList.getSelectedValue());
        }
        if(usersOnlineList.getSelectedValue() != null){
            receiverModel.addElement(usersOnlineList.getSelectedValue().getUsername());
        }

        receiversList.setModel(receiverModel);
        contactsList.clearSelection();
        usersOnlineList.clearSelection();
    }

    private void contactBtnActionPerformed(java.awt.event.ActionEvent evt){
        User user = (User) usersOnlineList.getSelectedValue();
        controllerClient.addToContactList(user.getUsername());
        contactListModel.addElement(user.getUsername());
        contactsList.setModel(contactListModel);
        usersOnlineList.clearSelection();
    }

    public void displayContactList(ArrayList<String> contactListArrayList){
        contactListModel.clear();
        for (int i = 0; i < contactListArrayList.size(); i++) {
            contactListModel.addElement(contactListArrayList.get(i));
        }
        contactsList.setModel(contactListModel);
    }

    private javax.swing.JButton addContactBtn;
    private javax.swing.JButton addToReceiverBtn;
    private javax.swing.JList<ChatMessage> chatMessagesList;
    private javax.swing.JLabel chatTxt;
    private javax.swing.JButton choosePictureBtn;
    private javax.swing.JLabel contactTxt;
    private javax.swing.JList<String> contactsList;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JTextPane messagePane;
    private javax.swing.JList<String> receiversList;
    private javax.swing.JLabel receiversTxt;
    private javax.swing.JButton sendMessageBtn;
    private javax.swing.JList<User> usersOnlineList;
    private javax.swing.JLabel usersOnlineTxt;
    // End of variables declaration
}

