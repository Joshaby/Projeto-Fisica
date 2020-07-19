package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;

public class NetworkInterfaceAdressGUI extends JFrame {
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private Map<JRadioButton, String> ipMap;
    private JList portsList;

    public NetworkInterfaceAdressGUI() {
        super("Interfaces e portas");
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else if (System.getProperty("os.name").toLowerCase().equals("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel1 = new JPanel();
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
            panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            ipMap = new HashMap<>();
            portsList = new JList(initPortsList().toArray());
            portsList.setVisibleRowCount(6);
            portsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(portsList);
            scrollPane.setMaximumSize(new Dimension(200, 100));
            panel.add(Box.createRigidArea(new Dimension(15, 15)));
            panel1.add(new JLabel("Interfaces disponíveis: "));
            panel1.add(Box.createRigidArea(new Dimension(50, 50)));
            initIpList();
            panel.add(panel1);
            panel.add(Box.createRigidArea(new Dimension(10, 10)));
            panel2.add(new JLabel("Portas disponíveis: "));
            panel2.add(scrollPane);
            panel.add(panel2);
            panel.add(Box.createRigidArea(new Dimension(10, 10)));
            add(panel);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | SocketException e) {
            System.err.println(e.getStackTrace());
        }
    }

    private void initIpList() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        JPanel panelLocal = null;
        for (NetworkInterface networkInterface : Collections.list(networkInterfaces)) {
            panelLocal = new JPanel();
            panelLocal.setLayout(new BoxLayout(panelLocal, BoxLayout.X_AXIS));
            JRadioButton radioButton = new JRadioButton();
            JLabel label = new JLabel();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            List<InetAddress> aux = Collections.list(inetAddresses);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><table>");
            stringBuilder.append(String.format("<tr><td>Interface: %s</td><td></td></tr>", networkInterface.getName()));
            stringBuilder.append(String.format("<tr>&ensp;&ensp;&ensp;%s : %s</tr>", "Ip", aux.get(1).toString().substring(1)));
            stringBuilder.append("<html><table>");
            label.setText(stringBuilder.toString());
            panelLocal.add(radioButton);
            panelLocal.add(Box.createRigidArea(new Dimension(15, 15)));
            panelLocal.add(label);
            panel1.add(panelLocal);
            panel1.add(Box.createRigidArea(new Dimension(10, 10)));
            ipMap.put(radioButton, networkInterface.toString().substring(1));
        }
    }
    private Vector<Integer> initPortsList() {
        Socket socket = null;
        Vector<Integer> ports = new Vector<>();
        for (int i = 1024; i <= 1_100; i ++) if (isPortAvailable(i)) ports.add(i);
        return ports;
    }

    public static boolean isPortAvailable(int port) {
        try (var ss = new ServerSocket(port); var ds = new DatagramSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
