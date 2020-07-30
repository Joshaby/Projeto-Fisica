package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class MenuTest {
    public static void main(String[] args) {
        MenuGUI menuGUI = new MenuGUI("Phodas", 1, "localhost", 1026);
        menuGUI.setSize(new Dimension(625, 400));
        menuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuGUI.setLocationRelativeTo(null);
        menuGUI.setVisible(true);
        menuGUI.setResizable(false);
    }
}
