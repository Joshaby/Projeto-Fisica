package br.edu.ifpb;

import javax.swing.*;

public class GUI extends JFrame {
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel time;
    private JButton nextButton;
    private JButton cancelButton;

    public GUI () {
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
