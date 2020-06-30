package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        gui.setSize(1350, 700);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setUndecorated(true);
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        gui.setBackground(new Color(0, 0, 0, 0));
    }
}
