/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoAn;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ACER
 */
public class Client1 extends javax.swing.JFrame {

    /**
     * Creates new form Client1
     */
    private String ImagePath;
    private Socket socket;
    
    public Client1() {
        initComponents();
        connectToServer();
        currentInstance = this;
    }
    private void connectToServer() {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Client is connected to the server.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi kết nối đến server");
        }
    }
    private static Client1 currentInstance;

    public static Client1 getCurrentInstance() {
    return currentInstance;
    }
    public String getImagePath() {
    return ImagePath;
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chonanh = new javax.swing.JButton();
        hinh1 = new javax.swing.JLabel();
        xacnhan = new javax.swing.JButton();
        hinh2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tennhandien = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        chonanh1 = new javax.swing.JButton();
        hinh3 = new javax.swing.JLabel();
        xacnhan1 = new javax.swing.JButton();
        hinh4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tennhandien1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));

        jPanel2.setBackground(new java.awt.Color(153, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("NHẬN DIỆN KHUÔN MẶT");

        chonanh.setText("Chọn ảnh");
        chonanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonanhActionPerformed(evt);
            }
        });

        hinh1.setBackground(new java.awt.Color(255, 255, 255));
        hinh1.setForeground(new java.awt.Color(255, 255, 255));
        hinh1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        xacnhan.setText("Xác nhận");
        xacnhan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        xacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacnhanActionPerformed(evt);
            }
        });

        hinh2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Khuôn mặt nhận diện");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Khuôn mặt cần nhận diện");

        tennhandien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tennhandien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(275, 275, 275))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(hinh1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(xacnhan)
                                    .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(chonanh)
                                .addGap(227, 227, 227)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hinh2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tennhandien, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(164, 164, 164))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hinh1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hinh2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(xacnhan)
                        .addGap(18, 18, 18)
                        .addComponent(reset)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chonanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tennhandien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhận diện khuôn mặt", jPanel2);

        jPanel11.setBackground(new java.awt.Color(153, 255, 204));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setText("NHẬN DIỆN ĐỐI TƯỢNG");

        chonanh1.setText("Chọn ảnh");
        chonanh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonanh1ActionPerformed(evt);
            }
        });

        hinh3.setBackground(new java.awt.Color(255, 255, 255));
        hinh3.setForeground(new java.awt.Color(255, 255, 255));
        hinh3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        xacnhan1.setText("Xác nhận");
        xacnhan1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        xacnhan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacnhan1ActionPerformed(evt);
            }
        });

        hinh4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hinh4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Đối tượng được nhận diện trong ảnh");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Đối tượng cần nhận diện");

        tennhandien1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tennhandien1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(275, 275, 275))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(hinh3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(xacnhan1)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(chonanh1)
                                .addGap(227, 227, 227)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hinh4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tennhandien1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(113, 113, 113))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hinh3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hinh4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(xacnhan1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chonanh1)
                    .addComponent(tennhandien1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhận diện đối tượng", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacnhanActionPerformed
        connectToServer();
        //sendImagePathToServer(ImagePath);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            // Gửi đường dẫn ảnh đến server
            out.println("km "+ImagePath);
            // Nhận và in kết quả từ server
            String line = in.readLine();
            if(line  != null && !line.equals("null")) {
                System.out.println(line);
                String[] parts = line.split(";");
                String receivedPath = parts[0];
                String receivedName = parts[1];
                String result = parts[2];
                System.out.println("Received Name: " + receivedName);
                System.out.println("Received Image Path: " + receivedPath);
                JOptionPane.showMessageDialog(this, "Nhận diện thành công");
                ImageIcon icon = new ImageIcon(receivedPath);
                Image image = icon.getImage().getScaledInstance(hinh2.getWidth(), hinh2.getHeight(), Image.SCALE_SMOOTH);
                hinh2.setIcon(new ImageIcon(image));
                tennhandien.setText("Giống với "+receivedName+" với độ chính xác là: "+result+"%" );
                } else {
                ImageIcon imageIcon = new ImageIcon(ImagePath);
                themthatbai.transferImage(imageIcon);
            } 
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi kết nối đến server");
        }

    }//GEN-LAST:event_xacnhanActionPerformed

    private void chonanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonanhActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedImagePath = selectedFile.getAbsolutePath();
                System.out.println("Đường dẫn ảnh đã chọn: " + selectedImagePath);

                // Hiển thị ảnh trên JLabel
                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image image = icon.getImage().getScaledInstance(hinh1.getWidth(), hinh1.getHeight(), Image.SCALE_SMOOTH);
                hinh1.setIcon(new ImageIcon(image));

                // Cập nhật lại giao diện
                jPanel2.validate();
                jPanel2.repaint();
                ImagePath = selectedImagePath;

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_chonanhActionPerformed

    private void chonanh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonanh1ActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedImagePath = selectedFile.getAbsolutePath();
                System.out.println("Đường dẫn ảnh đã chọn: " + selectedImagePath);

                // Hiển thị ảnh trên JLabel
                ImageIcon icon = new ImageIcon(selectedImagePath);
                Image image = icon.getImage().getScaledInstance(hinh3.getWidth(), hinh3.getHeight(), Image.SCALE_SMOOTH);
                hinh3.setIcon(new ImageIcon(image));

                // Cập nhật lại giao diện
                jPanel11.validate();
                jPanel11.repaint();
                ImagePath = selectedImagePath;

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_chonanh1ActionPerformed

    private void xacnhan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacnhan1ActionPerformed
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            // Gửi đường dẫn ảnh đến server
            out.println("dt "+ImagePath);
            // Nhận và in kết quả từ server
            String line;
            while ((line = in.readLine()) != null) {
                
                System.out.println(line);
                JOptionPane.showMessageDialog(this, "Nhận diện thành công");
                hinh4.setText(line);
            } 

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi kết nối đến server");
        }
    }//GEN-LAST:event_xacnhan1ActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed


        // Đặt hình ảnh về null
        hinh1.setIcon(null);
        hinh2.setIcon(null);
        hinh3.setIcon(null);
        hinh4.setIcon(null);

        // Xóa nội dung đang hiển thị
        tennhandien.setText("");
        tennhandien1.setText("");
        // Cập nhật lại giao diện
        jPanel2.validate();
        jPanel2.repaint();
        jPanel11.validate();
        jPanel11.repaint();
    }//GEN-LAST:event_resetActionPerformed
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    Client1 client = new Client1();
                    client.setVisible(true);
                    client.setLocationRelativeTo(null);
                }
            });
        }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chonanh;
    private javax.swing.JButton chonanh1;
    private javax.swing.JLabel hinh1;
    private javax.swing.JLabel hinh2;
    private javax.swing.JLabel hinh3;
    private javax.swing.JLabel hinh4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton reset;
    private javax.swing.JLabel tennhandien;
    private javax.swing.JLabel tennhandien1;
    private javax.swing.JButton xacnhan;
    private javax.swing.JButton xacnhan1;
    // End of variables declaration//GEN-END:variables
 }
