package br.edu.ifpb;

import javax.swing.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.Enumeration;

public class Server {
    public static void main(String[] args) {
        try {
            ServerLogic serverLogic = new ServerLogic(5);
            Logic_IF stub = (Logic_IF) UnicastRemoteObject.exportObject(serverLogic, 1026);
            User_IF stub1 = (User_IF) UnicastRemoteObject.exportObject(serverLogic.getGroupRepository(), 1026);
            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registry = LocateRegistry.createRegistry(1026);
            registry.bind("ServerLogic", stub);
            registry.bind("GroupRepository", stub1);
            System.out.println("Servidor ativado");
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }

//    public static void main(String args[]) throws SocketException {
////        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
////        for (NetworkInterface netint : Collections.list(nets))
////            displayInterfaceInformation(netint);
//        StringBuilder buff = new StringBuilder();
//        buff.append("<html><table>");
//        buff.append(String.format("<tr><td >%s</td><td>:</td><td>%s</td></tr>", "LO", ""));
//        buff.append(String.format("<tr><td >%s</td><td>:</td><td>%s</td></tr>", "Ip", "192.168.0.12"));
//        buff.append("</table></html>");
//
//        JOptionPane.showMessageDialog(null, new JLabel(buff.toString()));
//    }
//
//    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
//        System.out.printf("Display name: %s\n", netint.getDisplayName());
//        System.out.printf("Name: %s\n", netint.getName());
//        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
//        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
//            System.out.printf("InetAddress: %s\n", inetAddress);
//        }
//        System.out.printf("\n");
//    }
}
