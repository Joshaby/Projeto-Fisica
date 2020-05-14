package br.edu.ifpb;

import javax.swing.*;

public class boxLayoutTest {
    public static void main(String[] args) {
        boxLayout bL = new boxLayout();
        bL.setSize(550, 475);
        bL.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bL.setVisible(true);
        bL.setLocationRelativeTo(null);
    }
}
