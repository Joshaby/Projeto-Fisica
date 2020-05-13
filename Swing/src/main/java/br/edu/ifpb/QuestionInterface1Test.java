package br.edu.ifpb;

import javax.swing.*;

public class QuestionInterface1Test {
    public static void main(String[] args) {
        QuestionInterface1 qI = new QuestionInterface1();
        qI.setVisible(true);
        qI.setSize(800, 700);
        qI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        qI.setLocationRelativeTo(null);
    }
}
