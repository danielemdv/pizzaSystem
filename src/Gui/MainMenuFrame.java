/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

/**
 *
 * @author daniel
 */
public class MainMenuFrame extends javax.swing.JFrame {
    
    private PController controller; //Declaration of the controller

    /**
     * Creates new form MainMenuFrame
     * @param controller
     * <p> The program controller object that will control the GUI must be passed
     */
    public MainMenuFrame(PController controller) {
        super("Menu Principal");
        this.controller = controller;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerClientButton = new javax.swing.JButton();
        logOutButton = new javax.swing.JButton();
        clientManagerButton = new javax.swing.JButton();
        newOrderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        registerClientButton.setText("Alta Cliente");
        registerClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerClientButtonActionPerformed(evt);
            }
        });

        logOutButton.setText("Cerrar sesión");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        clientManagerButton.setText("Administrar clientes");
        clientManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientManagerButtonActionPerformed(evt);
            }
        });

        newOrderButton.setText("Alta Orden");
        newOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logOutButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clientManagerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registerClientButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(newOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(182, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerClientButton)
                .addGap(18, 18, 18)
                .addComponent(clientManagerButton)
                .addGap(18, 18, 18)
                .addComponent(newOrderButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(logOutButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerClientButtonActionPerformed
        controller.mainMenuRegisterClientButton();
    }//GEN-LAST:event_registerClientButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        controller.logOut();
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void clientManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientManagerButtonActionPerformed
        controller.mainMenuClientManagerButton();
    }//GEN-LAST:event_clientManagerButtonActionPerformed

    private void newOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOrderButtonActionPerformed
        controller.mainMenuNewOrderButton();
    }//GEN-LAST:event_newOrderButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clientManagerButton;
    private javax.swing.JButton logOutButton;
    private javax.swing.JButton newOrderButton;
    private javax.swing.JButton registerClientButton;
    // End of variables declaration//GEN-END:variables
}
