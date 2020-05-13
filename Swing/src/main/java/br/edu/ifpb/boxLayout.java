package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;

public class boxLayout extends JFrame {
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JButton button;
    private JButton button1;
    private JButton button2;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public boxLayout() {
        super("Box Layout");
        panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button = new JButton("Voltar");
        button1 = new JButton("Avançar");
        button2 = new JButton("Finalizar");
        // button2.setPreferredSize(new Dimension(140, 25));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(panel2);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setColumns(15);
        textArea.setRows(15);
        scrollPane = new JScrollPane(textArea);
        panel1.add(scrollPane);
        panel2.add(button);
        panel2.add(Box.createHorizontalGlue());
        panel2.add(button1);
        panel2.add(Box.createRigidArea(new Dimension(10,0)));
        panel2.add(button2);
        add(panel);
    }
}
