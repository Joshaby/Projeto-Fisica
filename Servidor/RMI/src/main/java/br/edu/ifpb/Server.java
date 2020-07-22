package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            ServerLogic serverLogic = new ServerLogic(5);
            Logic_IF stub = (Logic_IF) UnicastRemoteObject.exportObject((Remote) serverLogic, 1026);
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
}
