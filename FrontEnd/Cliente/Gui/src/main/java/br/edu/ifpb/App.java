package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    public static void main( String[] args ) throws IOException, InterruptedException {
        GUI gui = new GUI("phodas", 1, "localhost", 1200);
        gui.setSize(1306, 714);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gui.setUndecorated(true);
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        //gui.setBackground(new Color(0, 0, 0, 0));
    }
}
