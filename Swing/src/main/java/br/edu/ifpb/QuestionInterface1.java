package br.edu.ifpb;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class QuestionInterface1 extends JFrame {
    private JPanel mainPainel;
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JList list;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private ButtonGroup buttonGroup;
    private JButton button;
    private JButton button1;

    public QuestionInterface1() {
        super("Separator test");
        mainPainel = new JPanel();
        mainPainel.setLayout(new GridLayout(0, 1));
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0));
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 0));
        list = new JList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        panel.add(list, BorderLayout.WEST);
        textArea = new JTextArea(
                "(Fuvest) Após chover na cidade de São Paulo, as águas da chuva descerão o rio Tietê até o rio Paraná, percorrendo cerca de 1.000km. Sendo de 4km/h a velocidade média das águas, o percurso mencionado será cumprido pelas águas da chuva em aproximadamente:"
        );
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setRows(50);
        textArea.setColumns(20);
        scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        radioButton = new JRadioButton("A - 1");
        radioButton1 = new JRadioButton("B - 2");
        radioButton2 = new JRadioButton("C - 3");
        radioButton3 = new JRadioButton("D - 4");
        radioButton4 = new JRadioButton("E - 5");
        panel1.add(radioButton);
        panel1.add(radioButton1);
        panel1.add(radioButton2);
        panel1.add(radioButton3);
        panel1.add(radioButton4);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        panel1.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Test"));
        panel.add(panel1, BorderLayout.EAST);
        button = new JButton("Voltar");
        button1 = new JButton("Avançar");
        panel2.add(button, BorderLayout.WEST);
        panel2.add(new JSeparator());
        panel2.add(button1, BorderLayout.EAST);
        // mainPainel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Separator test"));
        mainPainel.add(panel, BorderLayout.NORTH);
        mainPainel.add(panel2, BorderLayout.SOUTH);
        add(mainPainel);
    }
}
