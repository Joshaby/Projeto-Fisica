package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class App {
    public static void main( String[] args ) throws IOException, InterruptedException {
        GUI gui = new GUI("Phodas", Arrays.asList("José", "Talison", "Vinicius"), 1);
        gui.setSize(1306, 714);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gui.setUndecorated(true);
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        //gui.setBackground(new Color(0, 0, 0, 0));
    }
}
