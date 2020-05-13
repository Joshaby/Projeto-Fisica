package br.edu.ifpb;

import javax.swing.*;

public class LabelFrameTest {
    public static void main(String[] args) {
        LabelFrame lF = new LabelFrame();
        lF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lF.setSize(260, 180);
        lF.setResizable(false);
        lF.setVisible(true);
    }
}
