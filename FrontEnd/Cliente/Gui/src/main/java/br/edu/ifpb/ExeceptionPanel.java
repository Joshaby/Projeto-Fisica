package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class ExeceptionPanel extends Component {
    public ExeceptionPanel(String text) {
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            JOptionPane.showMessageDialog(this, text, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
