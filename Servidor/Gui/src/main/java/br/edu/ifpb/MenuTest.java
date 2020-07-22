package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.net.SocketException;

public class MenuTest {
    public static void main(String[] args) throws SocketException {
        NetworkInterfaceAdressGUI networkInterfaceAdressGUI = new NetworkInterfaceAdressGUI();
        networkInterfaceAdressGUI.setSize(new Dimension(625, 425));
        networkInterfaceAdressGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        networkInterfaceAdressGUI.setLocationRelativeTo(null);
        networkInterfaceAdressGUI.setVisible(true);
        networkInterfaceAdressGUI.setResizable(false);
    }
}
