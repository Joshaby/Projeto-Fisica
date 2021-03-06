package br.edu.ifpb;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private static int seconds = 300;
    private final String SEPARATOR = System.getProperty("os.name").toLowerCase().equals("linux") ? "/" : String.format("\\");
    private static ServerConnection serverConnection;
    private static QuestionUtils questionUtils;

    private JPanel panel; // painel principal, contêm todos os outros paineis
    private JPanel panel1; // painel do headbar
    private JPanel panel2; // painel do editorPane, onde será mostrado o HTML, e da entrada de respostas, seja digitada ou selecionada
    private JPanel panel3; // painel das alternativas
    private JLabel icon; // ícone do app
    private JLabel appName; // nome do Aplicativo
    private JLabel time; // tempo restante para responder a questão
    private JLabel currentQuestion; // questão atual
    private JButton nextButton; // botão para avançar para a próxima questão
    private JEditorPane editorPane; // painel de exibição do HTML
    private JScrollPane scrollPane; // scroll bar do editrPane
    private ButtonGroup buttonGroup;
    private JTextArea answerEntry;
    private List<JRadioButton> radioButtonList;

    private int currentQuestionPosi = 1; // variáveis para a questão na GUI
    private int maxQuestionPosi;
    private boolean isMultipleChoiceQuestion;
    private String id;
    private StopWatch stopWatch;
    private String groupName;

    public GUI() throws IOException {

        try {
            groupName = "Pho";
            Map<String, List<String>> group = new HashMap<>();
            group.put(groupName, Arrays.asList("José", "Talison", "Henrique"));
            serverConnection = new ServerConnection();
            serverConnection.getConnection1().registerGroups(group, 1);
            serverConnection.initializateQuestions(groupName);
            questionUtils = new QuestionUtils(serverConnection);
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            maxQuestionPosi = serverConnection.getQuestionAmout();
            buttonGroup = new ButtonGroup();
            radioButtonList = new ArrayList<>();
            panel = new JPanel();
            panel.setBounds(10, 10, 100, 30);
            panel.setOpaque(false);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel1 = new JPanel();
            panel1.setOpaque(false);
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
            panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
            panel2.setOpaque(false);
            panel3 = new JPanel();
            panel3.setOpaque(false);

            // Painel1 e seus componentes

            appName = new JLabel("Jogo de física");
            appName.setFont(new Font("Jogo de física", Font.BOLD, 15));
            currentQuestion = new JLabel("Questão 1 de " + maxQuestionPosi);
            time = new JLabel();
            nextButton = new JButton("Avançar");
            nextButton.addActionListener(new ButtonHandler());
            panel1.add(Box.createRigidArea(new Dimension(15, 15)));
            icon = new JLabel();
            //icon.setIcon(new ImageIcon(getClass().getResource("logo.png")));
            panel1.add(icon);
            panel1.add(Box.createRigidArea(new Dimension(10, 10)));
            panel1.add(appName);
            panel1.add(Box.createHorizontalGlue());
            panel1.add(time);
            panel1.add(Box.createHorizontalGlue());
            panel1.add(currentQuestion);
            panel1.add(Box.createRigidArea(new Dimension(15, 15)));
            panel1.add(nextButton);
            panel1.add(Box.createRigidArea(new Dimension(10, 10)));
            stopWatch = new StopWatch(time);

            // Painel2 e seus componentes

            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            scrollPane = new JScrollPane(editorPane);

            //

            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(panel1);
            panel.add(Box.createRigidArea(new Dimension(10, 10)));
            panel.add(panel2);
            panel.add(Box.createRigidArea(new Dimension(10, 10)));
            add(panel);
            createQuestionComponents();
            stopWatch.start();
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createQuestionComponents() throws IOException {
        List<String> alternatives = new ArrayList<>();
        id = questionUtils.getQuestion(alternatives);
        if (id == null) finalPanel();
        if (alternatives.isEmpty()) {
            createEssayQuestionComponents(id);
            isMultipleChoiceQuestion = false;
        } else {
            createMultipleChoiceQuestionComponents(alternatives);
            isMultipleChoiceQuestion = true;
        }
    }

    public void createEssayQuestionComponents(String id) throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        editorPane.setPage(new File(".cache" + SEPARATOR + "Q" + id + ".HTM").toURI().toURL());
        scrollPane.setPreferredSize(new Dimension(1300, 550));
        answerEntry = new JTextArea();
        answerEntry.setBorder(new LineBorder(Color.BLACK, 1, true));
        answerEntry.setMaximumSize(new Dimension(200, 20));
        answerEntry.setMinimumSize(new Dimension(200, 20));
        panel1.add(new JLabel("Digite aqui sua respotas(apenas números)!"));
        panel1.add(Box.createRigidArea(new Dimension(10, 10)));
        panel1.add(answerEntry);
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(30, 30)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel3.add(panel);
        panel2.add(panel3);
        stopWatch.setSeconds(GUI.seconds);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void createMultipleChoiceQuestionComponents(List<String> alternatives) throws IOException {
        radioButtonList = new ArrayList<>();
        JPanel panel = new JPanel(); // painel principal com área de texto e painel das alternativas
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JPanel panel1 = new JPanel(); // painel das alternativas
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        scrollPane.setPreferredSize(new Dimension(830, 600));
        int amountOfPanels = alternatives.size();
        editorPane.setPage(new File(".cache" + SEPARATOR + "Q" + id + ".HTM").toURI().toURL());
        for (int i = 0; i < amountOfPanels; i++) {
            JPanel alternativePanel = new JPanel();
            alternativePanel.setLayout(new BoxLayout(alternativePanel, BoxLayout.X_AXIS));
            JRadioButton radioButton = new JRadioButton();
            radioButton.setText(String.format("%c", i + 65));
            buttonGroup.add(radioButton);
            radioButtonList.add(radioButton);
            JLabel label = new JLabel("<html>" + alternatives.get(i) + "</html>");
            alternativePanel.add(radioButton);
            alternativePanel.add(Box.createRigidArea(new Dimension(10, 10)));
            alternativePanel.add(label);
            panel1.add(alternativePanel);
        }
        panel1.setPreferredSize(new Dimension(430, 600));
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        panel3.add(panel);
        panel2.add(panel3);
        stopWatch.setSeconds(GUI.seconds);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void finalPanel() throws RemoteException {
        this.setVisible(false);
        JOptionPane.showMessageDialog(
                this,
                String.format("Pontos obtidos: %d", serverConnection.getPoints(groupName)),
                "Pontos",
                JOptionPane.OK_OPTION
        );
        System.exit(0);
    }

    public class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String answer = new String();
            if (event.getSource() == nextButton) {
                if (isMultipleChoiceQuestion) {
                    for (int i = 0; i < radioButtonList.size(); i++) {
                        if (radioButtonList.get(i).isSelected()) answer = radioButtonList.get(i).getText();
                    }
                }
            } else {
                if (answerEntry.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            GUI.this,
                            "Você não digitou nenhuma resposta!",
                            "Erro",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else answer = answerEntry.getText();
            }
            try {
                panel3.removeAll();
                panel2.remove(panel3);
                currentQuestionPosi++;
                currentQuestion.setText(String.format("Questão %d de %d", currentQuestionPosi, maxQuestionPosi));
                serverConnection.sendAnswer(1, groupName, GUI.this.id, answer, seconds - stopWatch.getSeconds());
                SwingUtilities.updateComponentTreeUI(GUI.this);
                createQuestionComponents();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
