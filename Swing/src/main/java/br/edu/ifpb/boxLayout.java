package br.edu.ifpb;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class boxLayout extends JFrame {
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton button;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private ButtonGroup buttonGroup;
    private JEditorPane editorPane;

    public boxLayout() throws IOException {
        super("Box Layout");
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // define a disposição dos componentes dentro de outro componente
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS)); // X_AXIS: disposição da esquerda pra direita
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS)); // Y_AXIS: disposição de cima para baixo
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // adiciona uma borda de 10px a um componente
        button = new JButton("Voltar");
        button.setBackground(Color.WHITE);
        button1 = new JButton("Avançar");
        button1.setBackground(Color.WHITE);
        button2 = new JButton("Finalizar");
        button2.setBackground(Color.WHITE);
        button1.addActionListener(new buttonHandler());
        button2.addActionListener(new buttonHandler());
        button.addActionListener(new buttonHandler());
        radioButton = new JRadioButton("A -");
        radioButton1 = new JRadioButton("B -");
        radioButton2 = new JRadioButton("C -");
        radioButton3 = new JRadioButton("D -");
        radioButton4 = new JRadioButton("E -");
        radioButton.setBackground(Color.WHITE);
        radioButton1.setBackground(Color.WHITE);
        radioButton2.setBackground(Color.WHITE);
        radioButton3.setBackground(Color.WHITE);
        radioButton4.setBackground(Color.WHITE);
        buttonGroup = new ButtonGroup(); // usado para criar um lógica entre od radio buttons, não selecionar mais de um ao mesmo temp
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        // button2.setPreferredSize(new Dimension(140, 25));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(5,5))); // cria um espaçador de tamanho definido pelo usuário
        panel.add(panel2);
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        File file = new File("ze.html");
        URL uri = file.toURI().toURL();
        try {
            editorPane.setPage(uri);
        } catch (IOException e) {
            editorPane.setContentType("text/html");
            editorPane.setText("<html>Page not found.</html>");
        }
        scrollPane = new JScrollPane(editorPane); // é usado para ter uma barra de rolamento num JEditoPane
        scrollPane.setPreferredSize(new Dimension(825,620));
        scrollPane.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 0), new EmptyBorder(0, 0, 0, 0))); // mexe na borda inferior e exterior de um componente
        // panel1.add(Box.createRigidArea(new Dimension(25,25)));
        panel1.add(scrollPane);
        panel1.add(Box.createRigidArea(new Dimension(25,25)));

        panel3.add(createAlternativeJPanel("ecryngtxmyb rtneeiuxrj muiegyueuyrnmuyen uvtyvhyhtyhvtyvhhtyvhygerxenxguyegxyxnemyugneuyxmyeugnn", radioButton));
        panel3.add(Box.createRigidArea(new Dimension(25,25)));
        panel3.add(createAlternativeJPanel("ecryng txmyb rtneeiuxrjmuiegyueuyrnmuyen uvtyvhyhtyhvtyvhhtyvhygerxenxguyegxyxne myugneuyxmyeugnn", radioButton1));
        panel3.add(Box.createRigidArea(new Dimension(25,25)));
        panel3.add(createAlternativeJPanel("ecryngtxmyb rtneeiuxrjmuieg yueuyrnmuyen uvtyvhyhtyhvtyvhhtyvhyge rxenxguyegxyxnemyugneuyxmyeugnn", radioButton2));
        panel3.add(Box.createRigidArea(new Dimension(25,25)));
        panel3.add(createAlternativeJPanel("ecryngtxmyb rtneeiuxrjmuiegyueuyrnmuyen uvtyvhyhtyhvtyvhhtyvhygerxenxguyegxyxnemyugneuyxmyeugnn", radioButton3));
        panel3.add(Box.createRigidArea(new Dimension(25,25)));
        panel3.add(createAlternativeJPanel("ecr yngtxmyb rtneeiuxrjmuiegyue uyrnmuyen uvtyvhyhtyhvtyvhhtyvhygerxenxguyegxyxnemyugneuy xmyeugnn", radioButton4));

        panel1.add(panel3);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Questão 13")); // adiciona uma borda em um componente com título na esquerda
        panel2.add(button);
        panel2.add(Box.createHorizontalGlue()); // cria um espaçador na horizontal
        panel2.add(button1);
        panel2.add(Box.createRigidArea(new Dimension(10,0)));
        panel2.add(button2);
        add(panel);
        // radioButton.addItemListener(new radioButton());
    }
    private class buttonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == button1) {
                panel1.remove(panel3);
                panel1.revalidate();
                panel1.repaint();
            }
            if (actionEvent.getSource() == button2) {
                panel1.add(panel3);
                panel1.revalidate();
                panel1.repaint();
            }
            if (actionEvent.getSource() == button) {
                if (radioButton.isSelected()) System.out.println(radioButton.getText());
                if (radioButton1.isSelected()) System.out.println(radioButton1.getText());
                if (radioButton2.isSelected()) System.out.println(radioButton2.getText());
                if (radioButton3.isSelected()) System.out.println(radioButton3.getText());
                if (radioButton4.isSelected()) System.out.println(radioButton4.getText());
            }
        }
    }
    private class radioButton implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (radioButton.isSelected()) System.out.println("oi");
        }
    }

    private JPanel createAlternativeJPanel(String text, JRadioButton radioButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(35);
        textArea.setRows(3);
        textArea.setEditable(false);
        panel.add(radioButton);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(createJScrollPane(textArea));
        return panel;
    }

    private JScrollPane createJScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 0), new EmptyBorder(0, 0, 0, 0)));
        return scrollPane;
    }

    private JScrollPane createJScrollPane(int width, int height) {
        JScrollPane scrollPane = new JScrollPane();

        return scrollPane;
    }
}
