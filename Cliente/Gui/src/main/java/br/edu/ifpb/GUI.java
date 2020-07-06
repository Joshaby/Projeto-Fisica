package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    private static int minutes = 2;
    private static int seconds = 0;
    private final String SEPARATOR = System.getProperty("os.name").toLowerCase().equals("linux") ? "/" : String.format("\\") ;
    private static ServerConnection serverConnection = new ServerConnection();

    private JPanel panel; // painel principal, contêm todos os outros paineis
    private JPanel panel1; // painel do headbar
    private JPanel panel2; // painel do editorPane, onde será mostrado o HTML, e da entrada de respostas, seja digitada ou selecionada
    private JPanel panel4; // painel das alternativas
    private JLabel icon; // ícone do app
    private JLabel appName; // nome do Aplicativo
    private JLabel time; // tempo restante para responder a questão
    private JLabel cuurentQuestion; // questão atual
    private JButton nextButton; // botão para avançar para a próxima questão
    private JEditorPane editorPane; // painel de exibição do HTML
    private JScrollPane scrollPane; // scroll bar do editrPane
    private ButtonGroup buttonGroup;
    private List<JRadioButton> radioButtonList;

    public GUI () throws IOException {
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            buttonGroup = new ButtonGroup();
            radioButtonList = new ArrayList<>();
            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Dimension arcs = new Dimension(15,15);
                    int width = getWidth();
                    int height = getHeight();
                    Graphics2D graphics = (Graphics2D) g;
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    //Draws the rounded opaque panel with borders.
                    graphics.setColor(getBackground());
                    graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
                    graphics.setColor(getForeground());
                    graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
                }
            };
            panel.setBounds(10,10,100,30);
            panel.setOpaque(false);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel1 = new JPanel();
            panel1.setOpaque(false);
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
            panel2 = new JPanel();
            panel2.setOpaque(false);
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
            panel4 = new JPanel();
            panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

            // Painel1 e seus componentes

            appName = new JLabel("Jogo de física");
            appName.setFont(new Font("Jogo de física", Font.BOLD, 15));
            cuurentQuestion = new JLabel(" Questão 1 de 5");
            time = new JLabel("Tempo restante: 2:00");
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
            panel1.add(cuurentQuestion);
            panel1.add(Box.createRigidArea(new Dimension(15, 15)));
            panel1.add(nextButton);
            panel1.add(Box.createRigidArea(new Dimension(10, 10)));

            // Painel2 e seus componentes

            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setBackground(new Color(0, 0, 0, 0));
            scrollPane = new JScrollPane(editorPane);
//            scrollPane.setMaximumSize(new Dimension(830, 600));
//            scrollPane.setMinimumSize(new Dimension(830, 600));
            scrollPane.setPreferredSize(new Dimension(830, 600));
            scrollPane.setBorder(new EmptyBorder( 0, 0, 0, 0));
            panel2.add(Box.createRigidArea(new Dimension(15, 15)));
            panel2.add(scrollPane);
            //

            panel.add(Box.createRigidArea(new Dimension(0, 5)));
            panel.add(panel1);
            panel.add(Box.createRigidArea(new Dimension(5, 5)));
            panel.add(panel2);
            panel.add(Box.createRigidArea(new Dimension(5, 5)));
            add(panel);
            createQuestionComponents();
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createQuestionComponents() throws IOException {
        List<String> alternatives = new ArrayList<>();
        String id = serverConnection.getQuestion(alternatives);
        if (alternatives.isEmpty()) createEssayQuestionComponents();
        else createMultipleChoiceQuestionComponents(alternatives, id);
    }

    public void updateQuestionComponents() {
//        JPanel panel5 = new JPanel();
//        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
//        JTextArea textArea = new JTextArea();
//        textArea.setBorder(new LineBorder(Color.BLACK, 1, true));
//        textArea.setMaximumSize(new Dimension(200, 20));
//        textArea.setMinimumSize(new Dimension(200, 20));
//        panel2.add(scrollPane);
//        panel2.add(Box.createRigidArea(new Dimension(18, 18)));
//        panel5.add(new JLabel("Digite aqui sua respotas(apenas números)!"));
//        panel5.add(Box.createRigidArea(new Dimension(10, 10)));
//        panel5.add(textArea);
//        panel2.add(panel5);
//        panel2.setOpaque(false);
    }
    public void createEssayQuestionComponents() {

    }
    public void createMultipleChoiceQuestionComponents(List<String> alternatives, String id) throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        int amountOfPanels = alternatives.size();
        for (int i = 0; i < amountOfPanels; i ++) {
            editorPane.setPage(new File(".cache" + SEPARATOR + "Q" + id + ".HTM").toURI().toURL());
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
            panel.add(alternativePanel);
        }
        panel2.add(Box.createRigidArea(new Dimension(15, 15)));
        panel2.add(panel);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void finalPanel() {
        System.exit(0);
    }

    public class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == nextButton) {
                for (JRadioButton radioButton : radioButtonList) {
                    if (radioButton.isSelected()) {
                        String answer = radioButton.getText();
                        System.out.println(answer);
                    }
                }
                //createQuestionComponents();
            }
        }
    }
}
