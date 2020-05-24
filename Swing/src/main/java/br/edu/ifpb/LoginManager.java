package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginManager extends JFrame {
    private Icon icon1 = new ImageIcon(getClass().getResource("confirm.png"));
    private Icon icon2 = new ImageIcon(getClass().getResource("cancel.png"));
    private List<List<String>> dataBase;

    private JLabel labe1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField;
    private JTextField turma;
    private JPasswordField passwordField;
    private JButton button1;
    private JButton button2;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JCheckBox checkBox;
    private JComboBox combo;

    public LoginManager() {
        super("Login Manager");
        setLayout(new FlowLayout());
        dataBase = new ArrayList<>();
        startDataBase();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        combo = new JComboBox(new String[]{"1", "2", "3"});
        combo.setMaximumRowCount(3);
        labe1 = new JLabel("Username");
        label2 = new JLabel("Password");
        label3 = new JLabel("Turma");
        textField = new JTextField(11);
        passwordField = new JPasswordField(11);
        turma = new JTextField(11);
        checkBox = new JCheckBox("Show password");
        passwordField.setEchoChar('*');
        button1 = new JButton("Confirm");
        button1.setIcon(icon1);
        button2 = new JButton("Cancel");
        button2.setIcon(icon2);
        panel1.add(labe1);
        panel1.add(textField);
        panel2.add(label2);
        panel2.add(passwordField);
//        panel4.add(label3);
//        panel4.add(turma);
        panel4.add(combo);
        panel3.add(button1);
        panel3.add(button2);
        add(panel1);
        add(panel2);
        add(checkBox);
        add(panel4);
        add(panel3);

        LoginManagerHandler handler = new LoginManagerHandler();
        button1.addActionListener(handler);
        button2.addActionListener(handler);

        ShowPasswordHandler handler1 = new ShowPasswordHandler();
        checkBox.addItemListener(handler1);
    }

    private void startDataBase() {
        try {
            List<String> aux = Files.readAllLines(Path.of("DataBase.txt"));
            for (String s : aux) {
                String[] aux1 = s.split(" ");
                dataBase.add(Arrays.asList(aux1));
            }
            System.out.println(dataBase);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error in data base!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private class LoginManagerHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == button1) {
                if (textField.getText().equals("") && passwordField.getText().equals("")) {
                    JOptionPane.showMessageDialog(LoginManager.this,
                            "The user not typed your username and password!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (passwordField.getText().equals("")) {
                    JOptionPane.showMessageDialog(LoginManager.this,
                            "The user not typed your password!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (textField.getText().equals("")) {
                    JOptionPane.showMessageDialog(LoginManager.this,
                            "The user not typed your username!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    for (List<String> user : dataBase) {
                        if (user.get(0).equals(textField.getText()) && user.get(1).equals(passwordField.getText())) {
                            System.out.println(user.get(0).equals(textField.getText()));
                            System.out.println(user.get(1).equals(passwordField.getText()));
                            break;
                        }
                        else if (!user.get(0).equals(textField.getText())) {
                            JOptionPane.showMessageDialog(LoginManager.this,
                                    "The user is not registred in data base!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        else if (!user.get(1).equals(passwordField.getText())) {
                            JOptionPane.showMessageDialog(LoginManager.this,
                                    "The user typed wrong password!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                    }
                }
            }
            if (event.getSource() == button2) System.exit(0);
        }
    }

    private class ShowPasswordHandler implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (checkBox.isSelected()) passwordField.setEchoChar((char)0);
            else passwordField.setEchoChar('*');
        }
    }
}
