package br.edu.ifpb;

import javax.swing.*;

public class QuestionInterfaceTest {
    public static void main(String[] args) {
        QuestionInterface qI = new QuestionInterface();
        qI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        qI.setVisible(true);
        qI.setSize(520, 500);
        qI.setLocationRelativeTo(null);
    }
}
