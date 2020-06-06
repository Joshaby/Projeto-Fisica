package br.edu.ifpb;

import javax.swing.*;

public class LoginManagerTest {
    public static void main(String[] args) {
        LoginManager lM = new LoginManager();
        lM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lM.setSize(295, 200);
        lM.setResizable(false);
        lM.setVisible(true);
        lM.setLocationRelativeTo(null);
    }
}
