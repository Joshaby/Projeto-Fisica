package br.edu.ifpb;

import javax.swing.*;
import java.io.IOException;

public class boxLayoutTest {
    public static void main(String[] args) throws IOException {
        boxLayout bL = new boxLayout();
        bL.setSize(700, 700);
        bL.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bL.setVisible(true);
        bL.setLocationRelativeTo(null);
    }
}
