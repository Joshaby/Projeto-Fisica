package br.edu.ifpb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MenuGUI extends JFrame {

    private Dimension dimension = new Dimension(150, 35);
    private Scoreboard_IF connection;

    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JLabel appName;
    private JButton startGameButton;
    private JButton checkScoreboard;
    private JButton checkCredits;
    private JButton exitButton;

    public MenuGUI() {
        super("Jogo de Física");
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            Registry localRegitry = LocateRegistry.getRegistry("localhost", 1026);
            connection = (Scoreboard_IF) localRegitry.lookup("Scoreboard");
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException | RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }
        panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        appName = new JLabel("Jogo de Física");
        appName.setFont(new Font("Jogo de Física", Font.BOLD, 22));
        startGameButton = new JButton("Iniciar jogo");
        startGameButton.setMaximumSize(dimension);
        startGameButton.setMinimumSize(dimension);
        checkScoreboard = new JButton("Placar");
        checkScoreboard.setMaximumSize(dimension);
        checkScoreboard.setMinimumSize(dimension);
        checkCredits = new JButton("Créditos");
        checkCredits.setMaximumSize(dimension);
        checkCredits.setMinimumSize(dimension);
        exitButton = new JButton("Sair");
        exitButton.addActionListener(new buttonHandler());
        exitButton.setMaximumSize(dimension);
        exitButton.setMinimumSize(dimension);

        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(appName);
        panel.add(Box.createRigidArea(new Dimension(50, 50)));
        panel.add(startGameButton);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(checkScoreboard);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(checkCredits);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        panel.add(exitButton);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));

        panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        panel3.add(new JLabel("Grupo: Phodas"));
        panel3.add(Box.createRigidArea(new Dimension(10, 10)));
        panel3.add(new JLabel("Série: 1 ano"));
        panel3.add(Box.createHorizontalGlue());
        panel3.add(new JLabel("Versão: 1.0-SNAPSHOT"));
        panel3.add(Box.createRigidArea(new Dimension(10, 10)));

        panel1.add(panel);
        panel1.add(Box.createRigidArea(new Dimension(15, 15)));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(Color.BLACK);
        panel1.add(separator);
        //panel1.add(Box.createHorizontalGlue());

        panel2.add(panel1);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBackground(Color.BLACK);
        separator1.setMaximumSize(new Dimension(1900, 1900));
        separator1.setMinimumSize(new Dimension(1900, 1900));
        panel2.add(separator1);
        panel2.add(panel3);
        panel2.add(Box.createRigidArea(new Dimension(4, 4)));
        add(panel2);
    }

    public class buttonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == exitButton) {
                panel4.removeAll();
                panel1.remove(panel4);
                Map<Integer, List<String>> scoreboard = new HashMap<>();
                try {
                    scoreboard = connection.getScoreboard(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println(scoreboard);
                Vector<Vector<String>> data = new Vector<>();
                Vector<String> col = new Vector<>(Arrays.asList("Grupo", "Pontos"));
                for (Integer i : scoreboard.keySet()) {
                    scoreboard.get(i).forEach(e -> {
                        data.add(new Vector(Arrays.asList(e, String.format("%d", i))));
                    });
                }
                JTable table = new JTable(new DefaultTableModel(data, col) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
                panel4.add(new JScrollPane(table));
                panel1.add(panel4);
            }
            SwingUtilities.updateComponentTreeUI(MenuGUI.this);
        }
    }
}
