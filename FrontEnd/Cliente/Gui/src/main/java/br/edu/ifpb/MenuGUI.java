package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuGUI extends JFrame {

    private Dimension dimension = new Dimension(150, 35);
    private Scoreboard_IF connection;

    private JPanel mainPanel;
    private JPanel buttonsAndAppNamePanel;
    private JPanel bottomPanel;
    private JPanel secondaryPanel;
    private JPanel scoreboardPanel;
    private JLabel appName;
    private JButton startGameButton;
    private JButton checkScoreboard;
    private JButton checkCredits;
    private JButton exitButton;

    private String groupName;
    private int year;

    public MenuGUI(String groupName, int year, String ip, int port) {
        super("Jogo de Física");
        try {
            this.year = year;
            this.groupName = groupName;
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            Registry localRegitry = LocateRegistry.getRegistry(ip, port);
            //connection = (Scoreboard_IF) localRegitry.lookup("Scoreboard");
            buttonsAndAppNamePanel = new JPanel();
            secondaryPanel = new JPanel();
            mainPanel = new JPanel();
            bottomPanel = new JPanel();
            scoreboardPanel = new JPanel();
            buttonsAndAppNamePanel.setLayout(new BoxLayout(buttonsAndAppNamePanel, BoxLayout.Y_AXIS));
            secondaryPanel.setLayout(new BoxLayout(secondaryPanel, BoxLayout.X_AXIS));
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
            appName = new JLabel("Jogo de Física");
            appName.setFont(new Font("Jogo de Física", Font.BOLD, 22));
            startGameButton = new JButton("Iniciar jogo");
            startGameButton.setMaximumSize(dimension);
            startGameButton.setMinimumSize(dimension);
            checkScoreboard = new JButton("Placar");
            checkScoreboard.addActionListener(new buttonHandler());
            checkScoreboard.setMaximumSize(dimension);
            checkScoreboard.setMinimumSize(dimension);
            checkCredits = new JButton("Créditos");
            checkCredits.setMaximumSize(dimension);
            checkCredits.setMinimumSize(dimension);
            exitButton = new JButton("Sair");
            exitButton.addActionListener(new buttonHandler());
            exitButton.setMaximumSize(dimension);
            exitButton.setMinimumSize(dimension);

            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(20, 20)));
            buttonsAndAppNamePanel.add(appName);
            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(50, 50)));
            buttonsAndAppNamePanel.add(startGameButton);
            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(20, 20)));
            buttonsAndAppNamePanel.add(checkScoreboard);
            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(20, 20)));
            buttonsAndAppNamePanel.add(checkCredits);
            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(20, 20)));
            buttonsAndAppNamePanel.add(exitButton);
            buttonsAndAppNamePanel.add(Box.createRigidArea(new Dimension(20, 20)));

            bottomPanel.add(Box.createHorizontalGlue());
            bottomPanel.add(new JLabel("Versão: 1.0-SNAPSHOT"));
            bottomPanel.add(Box.createRigidArea(new Dimension(10, 10)));

            secondaryPanel.add(buttonsAndAppNamePanel);
            secondaryPanel.add(Box.createRigidArea(new Dimension(15, 15)));
            JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
            separator.setBackground(Color.BLACK);
            secondaryPanel.add(separator);

            mainPanel.add(secondaryPanel);
            JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
            separator1.setBackground(Color.BLACK);
            separator1.setMaximumSize(new Dimension(1900, 1900));
            separator1.setMinimumSize(new Dimension(1900, 1900));
            mainPanel.add(separator1);
            mainPanel.add(bottomPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(4, 4)));
            add(mainPanel);
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public class buttonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == checkScoreboard) {
                scoreboardPanel.removeAll();
                secondaryPanel.remove(scoreboardPanel);
                Map<Integer, List<String>> scoreboard = new HashMap<>();
                try {
                    scoreboard = connection.getScoreboard(MenuGUI.this.year);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println(scoreboard);
                Vector<Vector<String>> data = new Vector<>();
                Vector<String> col = new Vector<>(Arrays.asList("Posição", "Grupo", "Pontos"));
                AtomicInteger integer = new AtomicInteger(1);
                for (Integer i : scoreboard.keySet()) {
                    scoreboard.get(i).forEach(e -> {
                        data.add(new Vector(Arrays.asList(String.format("%d", Integer.parseInt(String.valueOf(integer))), e, String.format("%d", i))));
                        integer.getAndIncrement();
                    });
                }
                JTable table = new JTable(data, col) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                JLabel localLabel = new JLabel(String.format("Placar do %d ano", MenuGUI.this.year));
                localLabel.setFont(new Font(String.format("Placar do %d ano", MenuGUI.this.year), Font.BOLD, 12));
                JPanel localPanel = new JPanel();
                localPanel.add(localLabel);
                scoreboardPanel.add(Box.createRigidArea(new Dimension(5, 5)));
                scoreboardPanel.add(localPanel);
                scoreboardPanel.add(Box.createRigidArea(new Dimension(5, 5)));
                table.setMaximumSize(new Dimension(30, 30));
                table.setMinimumSize(new Dimension(30, 30));
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(425, 300));
                scoreboardPanel.add(scrollPane);
                secondaryPanel.add(scoreboardPanel);
                SwingUtilities.updateComponentTreeUI(MenuGUI.this);
            }
            else if (event.getSource() == exitButton) System.exit(0);
        }
    }
}
