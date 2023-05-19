package client.view;

import client.controller.ControllerClient;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientChat extends javax.swing.JFrame {
    private ControllerClient controllerClient;

    public ClientChat(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
        initComponents();
        setVisible(true);
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

        usersOnlineList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(usersOnlineList);

        contactsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(contactsList);

        usersOnlineTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        usersOnlineTxt.setText("Users online");

        addContactBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addContactBtn.setText("Add contact");

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

        receiversList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(receiversList);

        receiversTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        receiversTxt.setText("Receivers");

        addToReceiverBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addToReceiverBtn.setText("Add to receiver list");

        chatTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        chatTxt.setText("Chat");

        chatMessagesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(chatMessagesList);

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
        // TODO add your handling code here:
    }

    private void choosePictureBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt){
        controllerClient.loggedOut();
    }

    public void showNewMessage(String text, ImageIcon image, String username, ImageIcon userPicture, LocalDateTime timeReceived){

    }

    public void displayConnectedUsers(ArrayList<String> users){

    }

    public void displayContactList(ArrayList<String> contactList){

    }

    // Variables declaration - do not modify
    private javax.swing.JButton addContactBtn;
    private javax.swing.JButton addToReceiverBtn;
    private javax.swing.JList<String> chatMessagesList;
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
    private javax.swing.JList<String> usersOnlineList;
    private javax.swing.JLabel usersOnlineTxt;
    // End of variables declaration
}

