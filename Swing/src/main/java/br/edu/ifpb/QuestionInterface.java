package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class QuestionInterface extends JFrame {

    //private Path questionsPath = Path.of("Question.csv");

    private TreeSet<Question> questions;

    private JLabel label;
    private JTextArea textArea;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private ButtonGroup buttonGroup;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton button;
    private JButton button1;
    private JPanel panel1;
    private JPanel global;

    public QuestionInterface() {
        super("Question Interface");
        questions = new TreeSet<>(Comparator.naturalOrder());
        global = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(40);
        textArea.setLineWrap(true);
        textArea.setRows(15);
        scrollPane = new JScrollPane(textArea);
        radioButton = new JRadioButton();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton4 = new JRadioButton();
        button = new JButton("Next");
        button.setHorizontalAlignment(SwingConstants.RIGHT);
        button.setVerticalAlignment(SwingConstants.BOTTOM);
        button1 = new JButton("Return");
        button1.setHorizontalAlignment(SwingConstants.LEFT);
        button1.setVerticalAlignment(SwingConstants.BOTTOM);
        panel1 = new JPanel();
        panel1.add(button);
        panel1.add(button1);
        panel = new JPanel();
        panel.add(radioButton);
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3);
        panel.add(radioButton4);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        global.add(label);
        global.add(new JSeparator(SwingConstants.HORIZONTAL));
        global.add(scrollPane);
        global.add(panel);
        global.add(panel1);
        add(global);
        //initDataBase();
        initComponents();
    }

//    private void initDataBase() {
//        try {
//            List<String> questionsCsv = Files.readAllLines(questionsPath);
//            for (String i : questionsCsv) {
//                String[] dataQuestions = i.split(";");
//                List<String> alternatives = new ArrayList<>();
//                String correctAlternative = "";
//                for (int j = 2; j < 7; j++) {
//                    if (dataQuestions[j].startsWith("-")) {
//                        correctAlternative = dataQuestions[j].substring(1);
//                        alternatives.add(dataQuestions[j].substring(1));
//                    }
//                    else alternatives.add(dataQuestions[j]);
//                }
//                questions.add(
//                        new Question(Integer.parseInt(dataQuestions[0]), dataQuestions[1], alternatives, correctAlternative)
//                );
//            }
//            for (Question i : questions) {
//                System.out.println(i);
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error in data base of questions",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//            System.exit(0);
//        }
//    }

    private void initComponents() {
        label.setText("Questão %d");
        textArea.setText("usndasbdyubasudbsada");
        radioButton.setText("A - ");
        radioButton1.setText("B - ");
        radioButton2.setText("C - ");
        radioButton3.setText("D - ");
        radioButton4.setText("E - ");
    }
}
