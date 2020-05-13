package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class LabelFrame extends JFrame {
    public LabelFrame()  {
        super("Test");
        setLayout(new FlowLayout());
        JTextField textField = new JTextField(10);
        JLabel label1 = new JLabel("Label1");
        JPanel panel = new JPanel();
        JButton button = new JButton("Button");
        label1.setToolTipText("Label1");
        panel.add(label1);
        panel.add(textField);
        add(panel);
        add(button);
    }
}
