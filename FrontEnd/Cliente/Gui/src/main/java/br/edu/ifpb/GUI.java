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

    private static int seconds = 600;
    private final String SEPARATOR = System.getProperty("os.name").toLowerCase().equals("linux") ? "/" : String.format("\\");
    private static ServerConnection serverConnection;
    private static QuestionUtils questionUtils;

    private JPanel mainPanel; // painel principal, contêm todos os outros paineis
    private JPanel TopBarPanel; // painel do headbar
    private JPanel questionPanel; // painel do editorPane, onde será mostrado o HTML, e da entrada de respostas, seja digitada ou selecionada
    private JPanel alternativesPanel; // painel das alternativas
    private JPanel bottomPanel; // painel inferior
    private JLabel icon; // ícone do app
    private JLabel appName; // nome do Aplicativo
    private JLabel time; // tempo restante para responder a questão
    private JLabel currentQuestion; // questão atual
    private JButton nextButton; // botão para avançar para a próxima questão
    private JEditorPane editorPane; // painel de exibição do HTML
    private JScrollPane scrollPane; // scroll bar do editorPane
    private JScrollPane questionScrollPane; // Scrollpane para a questão, com texto e alternativas

    private ButtonGroup buttonGroup;
    private JTextArea answerEntry;
    private List<JRadioButton> radioButtonList;

    private int currentQuestionPosi = 1; // variáveis para a questão na GUI
    private int maxQuestionPosi;
    private boolean isMultipleChoiceQuestion;
    private String id;
    private StopWatch stopWatch;
    private boolean threadExecute = false;

    private String groupName; // variáveis do grupo
    private int year;
    private int round;

    public GUI(String groupName, List<String> members, int year) throws IOException {
        super("Jogo de física");
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            this.groupName = groupName;
            this.year = year;
            serverConnection = new ServerConnection();
            round = serverConnection.getRound();
            Map<String, List<String>> group = new HashMap<>();
            group.put(groupName, members);
            serverConnection.getConnection1().registerGroups(group, year);
            maxQuestionPosi = serverConnection.getQuestionAmount();
            buttonGroup = new ButtonGroup();
            radioButtonList = new ArrayList<>();
            mainPanel = new JPanel();
            mainPanel.setBounds(10, 10, 100, 30);
            mainPanel.setOpaque(false);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            TopBarPanel = new JPanel();
            TopBarPanel.setOpaque(false);
            TopBarPanel.setLayout(new BoxLayout(TopBarPanel, BoxLayout.X_AXIS));
            questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
            questionPanel.setOpaque(false);
            alternativesPanel = new JPanel();
            alternativesPanel.setLayout(new BoxLayout(alternativesPanel, BoxLayout.X_AXIS));
            alternativesPanel.setOpaque(false);
            bottomPanel = new JPanel();
            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
            questionScrollPane = new JScrollPane(questionPanel);

            initComponents();
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initComponents() throws IOException, InterruptedException {

        waitMessage("Aguarde o servidor liberar as questões!");

        // Painel1 e seus componentes

        appName = new JLabel("Jogo de física");
        appName.setFont(new Font("Jogo de física", Font.BOLD, 15));
        currentQuestion = new JLabel();
        time = new JLabel();
        nextButton = new JButton("Avançar");
        nextButton.addActionListener(new ButtonHandler());
        TopBarPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        icon = new JLabel();
        //icon.setIcon(new ImageIcon(getClass().getResource("logo.png")));
        TopBarPanel.add(icon);
        TopBarPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        TopBarPanel.add(appName);
        TopBarPanel.add(Box.createHorizontalGlue());
        TopBarPanel.add(time);
        TopBarPanel.add(Box.createHorizontalGlue());
        TopBarPanel.add(currentQuestion);
        TopBarPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        TopBarPanel.add(nextButton);
        TopBarPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        stopWatch = new StopWatch(time, seconds);

        // Painel2 e seus componentes

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane = new JScrollPane(editorPane);

        // Painel4 e seus componentes

        bottomPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        bottomPanel.add(new JLabel("Grupo: " + groupName));
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        bottomPanel.add(new JLabel(String.format("Série: %d ano", year)));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(new JLabel("Versão: 1.0-SNAPSHOT"));
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        //

        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        mainPanel.add(TopBarPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        mainPanel.add(questionScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        mainPanel.add(bottomPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        add(mainPanel);
        createQuestionComponents();
        stopWatch.start();
    }

    public void waitMessage(String message) throws IOException, InterruptedException {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel(message);
        label.setFont(new Font(message, Font.BOLD, 34));
        frame.add(label);
        frame.setSize(new Dimension(725, 100));
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
        while (serverConnection.initializateQuestions(groupName)) {
            Thread.currentThread().sleep(1000);
        }
        frame.setVisible(false);
        questionUtils = new QuestionUtils(serverConnection);
    }

    public void createQuestionComponents() throws IOException {
        List<String> alternatives = new ArrayList<>();
        id = questionUtils.getQuestion(alternatives);
        if (id == null) finalPanel();
        currentQuestion.setText(String.format("Questão %d (ID: %s) de %d", currentQuestionPosi, GUI.this.id, maxQuestionPosi));
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
        panel.setOpaque(false);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.setOpaque(false);
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
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(30, 30)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        alternativesPanel.add(panel);
        questionPanel.add(alternativesPanel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void createMultipleChoiceQuestionComponents(List<String> alternatives) throws IOException {
        radioButtonList = new ArrayList<>();
        JPanel panel = new JPanel(); // painel principal com área de texto e painel das alternativas
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        JPanel panel1 = new JPanel(); // painel das alternativas
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(845, 600));
        int amountOfPanels = alternatives.size();
        editorPane.setPage(new File(".cache" + SEPARATOR + "Q" + id + ".HTM").toURI().toURL());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        for (int i = 0; i < amountOfPanels; i++) {
            JPanel alternativePanel = new JPanel();
            alternativePanel.setLayout(new BoxLayout(alternativePanel, BoxLayout.X_AXIS));
            alternativePanel.setOpaque(false);
            JRadioButton radioButton = new JRadioButton();
            radioButton.setText(String.format("%c", i + 65));
            buttonGroup.add(radioButton);
            radioButtonList.add(radioButton);
            JLabel label = new JLabel("<html>" + alternatives.get(i) + "</html>");
            alternativePanel.add(radioButton);
            alternativePanel.add(Box.createRigidArea(new Dimension(10, 10)));
            alternativePanel.add(label);
            panel1.add(alternativePanel);
            panel1.add(Box.createRigidArea(new Dimension(25, 25)));
        }
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        panel.add(scrollPane);
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setSize(new Dimension(1000, 1));
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(15, 15)));
        alternativesPanel.add(panel);
        questionPanel.add(alternativesPanel);
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
                alternativesPanel.removeAll();
                questionPanel.remove(alternativesPanel);
                currentQuestionPosi++;
                serverConnection.sendAnswer(year, groupName, GUI.this.id, answer, seconds - stopWatch.getSeconds());
                SwingUtilities.updateComponentTreeUI(GUI.this);
                createQuestionComponents();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
