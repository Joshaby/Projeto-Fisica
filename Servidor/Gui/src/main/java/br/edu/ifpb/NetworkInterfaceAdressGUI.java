package br.edu.ifpb;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;

public class NetworkInterfaceAdressGUI extends JFrame {

    private JPanel interfacePanel;
    private JPanel portsPanel;
    private JPanel interfacesAndPortsPanel;
    private JPanel mainPanel;
    private JPanel bottonPanel;
    private JLabel interfaceLabel;
    private JLabel portsLabel;
    private JLabel selectedInterface;
    private JLabel selectedPort;
    private JButton nextButton;
    private Map<JRadioButton, String> ipMap;
    private List<JRadioButton> radioButtonList;
    private JList portsList;
    private ButtonGroup buttonGroup;

    private Vector<Integer> portsAvaliabes;

    public NetworkInterfaceAdressGUI() throws SocketException {
        super("Jogo de Física");
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        buttonGroup = new ButtonGroup();
        interfacePanel = new JPanel();
        portsPanel = new JPanel();
        interfacesAndPortsPanel = new JPanel();
        mainPanel = new JPanel();
        bottonPanel = new JPanel();
        interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.Y_AXIS));
        portsPanel.setLayout(new BoxLayout(portsPanel, BoxLayout.Y_AXIS));
        interfacesAndPortsPanel.setLayout(new BoxLayout(interfacesAndPortsPanel, BoxLayout.X_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        bottonPanel.setLayout(new BoxLayout(bottonPanel, BoxLayout.X_AXIS));
        interfaceLabel = new JLabel("Interfaces disponiveis:");
        interfaceLabel.setFont(new Font("Interfaces disponiveis", Font.BOLD, 18));
        portsLabel = new JLabel("Portas disponíveis:");
        portsLabel.setFont(new Font("Interfaces disponiveis", Font.BOLD, 18));
        nextButton = new JButton("Avançar");
        nextButton.addActionListener(new ButtonHandler());
        selectedInterface = new JLabel();
        selectedPort = new JLabel();
        portsAvaliabes = new Vector<>();
        ipMap = new HashMap<>();
        radioButtonList = new ArrayList<>();

        // configuração do painel de interfaces
        interfacePanel.add(Box.createRigidArea(new Dimension(20, 20)));
        JPanel panelO1 = new JPanel();
        panelO1.setLayout(new BoxLayout(panelO1, BoxLayout.X_AXIS));
        panelO1.add(Box.createRigidArea(new Dimension(15, 15)));
        panelO1.add(interfaceLabel);
        interfacePanel.add(panelO1);
        interfacePanel.add(Box.createRigidArea(new Dimension(50, 50)));
        initIpList();

        // layout do painel inferior, com a exibição da interface e porta seleciondas, e botão avançar
        bottonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        bottonPanel.add(new JLabel("Configuração atual: "));
        bottonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        bottonPanel.add(selectedInterface);
        bottonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        bottonPanel.add(selectedPort);
        bottonPanel.add(Box.createHorizontalGlue());
        bottonPanel.add(nextButton);
        bottonPanel.add(Box.createRigidArea(new Dimension(10, 10)));

        // configuração do segundo painel principl, painel que contem o painel das interfaces e portas
        interfacesAndPortsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        interfacesAndPortsPanel.add(interfacePanel);
        interfacesAndPortsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setMaximumSize(new Dimension(110, 600));
        separator.setMinimumSize(new Dimension(110, 600));
        interfacesAndPortsPanel.add(separator);

        // configuração do painel das portas
        initPortsList();
        portsList = new JList(portsAvaliabes);
        portsList.addListSelectionListener(new JListHandler());
        portsList.setVisibleRowCount(6);
        portsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(portsList);
        scrollPane.setMaximumSize(new Dimension(400, 200));
        portsPanel.add(portsLabel);
        portsPanel.add(Box.createRigidArea(new Dimension(50, 50)));
        portsPanel.add(scrollPane);
        interfacesAndPortsPanel.add(portsPanel);

        // configuração do painel principal
        mainPanel.add(interfacesAndPortsPanel);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setMaximumSize(new Dimension(1000, 20));
        separator1.setMinimumSize(new Dimension(1000, 20));
        mainPanel.add(separator1);
        mainPanel.add(bottonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        add(mainPanel);
    }
    private void initIpList() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
            JPanel panelLocal = new JPanel();
            panelLocal.setLayout(new BoxLayout(panelLocal, BoxLayout.X_AXIS));
            JRadioButton radioButton = new JRadioButton();
            radioButton.addItemListener(new RadioButtonHandler());
            JLabel label = new JLabel();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            List<InetAddress> aux = Collections.list(inetAddresses);
            StringBuilder stringBuilder = new StringBuilder();
            label.setText(String.format("Interface: %s - IP: %s", networkInterface.getName(), aux.get(1).toString().substring(1)));
            panelLocal.add(radioButton);
            panelLocal.add(Box.createRigidArea(new Dimension(10, 10)));
            panelLocal.setMaximumSize(new Dimension(250, 60));
            panelLocal.setMinimumSize(new Dimension(250, 60));
            panelLocal.add(label);
            interfacePanel.add(panelLocal);
            ipMap.put(radioButton, networkInterface.getName() + " - " + aux.get(1).toString().substring(1));
            radioButtonList.add(radioButton);
            buttonGroup.add(radioButton);
        }
    }
    private void initPortsList() {
        for (int i = 1024; i <= 1_100; i ++) if (isPortAvailable(i)) portsAvaliabes.add(i);
    }
    public static boolean isPortAvailable(int port) {
        try (var ss = new ServerSocket(port); var ds = new DatagramSocket(port)) {
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public class RadioButtonHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            JRadioButton radioButton = (JRadioButton) event.getSource();
            selectedInterface.setText(ipMap.get(radioButton));
        }
    }
    public class JListHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            selectedPort.setText(String.valueOf(portsAvaliabes.get(portsList.getSelectedIndex())));
        }
    }
    public class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == nextButton) {
                for (int i = 0; i < radioButtonList.size(); i ++) {
                    if (radioButtonList.get(i).isSelected()) {
                        System.out.println(String.format("Interface: %s\nPorta: %s\n", ipMap.get(radioButtonList.get(i)), portsAvaliabes.get(portsList.getSelectedIndex())));
                    }
                }
            }
        }
    }
}
