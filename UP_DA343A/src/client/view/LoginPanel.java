/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.view;

import client.controller.ControllerClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class LoginPanel extends javax.swing.JFrame {
    private ControllerClient controllerClient;
    private ImageIcon selectedProfilePicture;

    public LoginPanel(ControllerClient controllerClient) {
        this.controllerClient = controllerClient;
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ipTxtPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        portTxtPane = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        usernameTxtPane = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        profilePicture = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IP-address:");

        jLabel2.setText("Port:");
        portTxtPane.setText(String.valueOf(3343));

        jLabel3.setText("Username:");

        jScrollPane1.setViewportView(ipTxtPane);

        jScrollPane2.setViewportView(portTxtPane);

        jScrollPane3.setViewportView(usernameTxtPane);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Select profile picture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectProfilePictureBtnActionPerformed(evt);
            }
        });

        profilePicture.setText("Profile picture");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)
                                    .addComponent(jScrollPane2)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(profilePicture)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(25, 25, 25)
                .addComponent(profilePicture)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameTxtPane.getText();
        ImageIcon image = (ImageIcon) profilePicture.getIcon();

        boolean login = false;

        if(image != null){
            login = controllerClient.connectToServer(username, image, getIp(), getPort());
        }
        else{
            JOptionPane.showMessageDialog(null, "select profile picture");
        }

        if(login){
            controllerClient.loggedIn();
        }
    }

    private void selectProfilePictureBtnActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = new JFrame("Image Chooser");
        JFileChooser fileChooser = new JFileChooser();
        File directory = null;
        try{
            directory = new File(new File(".").getCanonicalPath() + "\\UP_DA343A\\pictures");
        } catch (IOException e){
            e.printStackTrace();
        }
        fileChooser.setCurrentDirectory(directory);

        int result = fileChooser.showOpenDialog(frame);

        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            Image image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
            Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_REPLICATE);
            selectedProfilePicture = new ImageIcon(resizedImage);

            profilePicture.setText(null);
            profilePicture.setIcon(selectedProfilePicture);
        }
    }

    public String getIp() {
        return ipTxtPane.getText();
    }

    public int getPort() {
        return Integer.valueOf(portTxtPane.getText());
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel profilePicture;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane ipTxtPane;
    private javax.swing.JTextPane portTxtPane;
    private javax.swing.JTextPane usernameTxtPane;
}
