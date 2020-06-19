package br.edu.ifpb;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.IOException;

public class boxLayoutTest {
    public static void main(String[] args) throws IOException, BadLocationException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        boxLayout bL = new boxLayout();
        bL.setSize(1366, 728);
        bL.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // bL.setUndecorated(true);
        bL.setVisible(true);
        // bL.setResizable(false);
        bL.setLocationRelativeTo(null);
        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println(OS);
    }
}
