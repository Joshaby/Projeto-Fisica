package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class MenuTest {
    public static void main(String[] args) {
        MenuGUI menuGUI = new MenuGUI();
        menuGUI.setSize(new Dimension(650, 500));
        menuGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuGUI.setLocationRelativeTo(null);
        menuGUI.setVisible(true);
        menuGUI.setResizable(false);
    }
}
