package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;

public class GUI extends JFrame {

    private static int minutes = 2;
    private static int seconds = 0;
    private static int currentQuestionNumber = 1;

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

    public GUI () throws IOException {
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

        // Painel1 e seus componentes

        appName = new JLabel("Jogo de física");
        appName.setFont(new Font("Jogo de física", Font.BOLD, 15));
        cuurentQuestion = new JLabel(" Questão 1 de 5");
        time = new JLabel("Tempo restante: 2:00");
        nextButton = new JButton("Avançar");
        panel1.add(Box.createRigidArea(new Dimension(15, 15)));
        icon = new JLabel();
        icon.setIcon(new ImageIcon(getClass().getResource("logo.png")));
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
        scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(835, 710));
        scrollPane.setBorder(new EmptyBorder( 0, 0, 0, 0));
        panel2.add(Box.createRigidArea(new Dimension(5, 5)));
        panel2.add(scrollPane);
        panel2.add(Box.createRigidArea(new Dimension(5, 5)));
        panel2.setOpaque(false);

        //

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(panel2);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        add(panel);
    }
}
