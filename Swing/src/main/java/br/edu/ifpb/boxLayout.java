package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class boxLayout extends JFrame {
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton button;
    private JButton button1;
    private JButton button2;
    private JList list;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private ButtonGroup buttonGroup;

    public boxLayout() {
        super("Box Layout");
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        list = new JList(
            new String[]{
                "Questão - 1", "Questão - 2", "Questão - 3", "Questão - 4", "Questão - 5", "Questão - 6", "Questão - 7", "Questão - 8", "Questão - 9", "Questão - 10"
            }
        );
        list.setSize(100, 100);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // define a disposição dos componentes dentro de outro componente
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS)); // X_AXIS: disposição da esquerda pra direita
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS)); // Y_AXIS: disposição de cima para baixo
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // adiciona uma borda de 10px a um componente
        button = new JButton("Voltar");
        button1 = new JButton("Avançar");
        button2 = new JButton("Finalizar");
        button1.addActionListener(new buttonHandler());
        button2.addActionListener(new buttonHandler());
        button.addActionListener(new buttonHandler());
        radioButton = new JRadioButton("A - 10 m");
        radioButton1 = new JRadioButton("B - 20 m");
        radioButton2 = new JRadioButton("C - 30 m");
        radioButton3 = new JRadioButton("D - 40 m");
        radioButton4 = new JRadioButton("E - 50 m");
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
        textArea = new JTextArea(
            "(FUVEST) Um veículo parte do repouso em movimento retilíneo e acelera com aceleração escalar constante e igual a 2,0 m/s2. Pode-se dizer que sua velocidade escalar e a distância percorrida após 3,0 segundos, valem, respectivamente:"
        );
        textArea.setLineWrap(true); // define a quebra de linha do texto
        textArea.setEditable(false);
        textArea.setColumns(25);
        textArea.setRows(25);
        scrollPane = new JScrollPane(textArea); // é usado para ter uma barra de rolamento num JTextArea
        panel1.add(list);
        panel1.add(Box.createRigidArea(new Dimension(15,15)));
        panel1.add(scrollPane);
        panel1.add(Box.createRigidArea(new Dimension(15,15)));
        panel3.add(radioButton);
        panel3.add(radioButton1);
        panel3.add(radioButton2);
        panel3.add(radioButton3);
        panel3.add(radioButton4);
        panel1.add(panel3);
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
}
