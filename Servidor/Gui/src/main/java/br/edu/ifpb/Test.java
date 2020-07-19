package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        NetworkInterfaceAdressGUI gui = new NetworkInterfaceAdressGUI();
        gui.setSize(new Dimension(400, 300));
        gui.setLocationRelativeTo(null);
        //gui.setResizable(false);
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
