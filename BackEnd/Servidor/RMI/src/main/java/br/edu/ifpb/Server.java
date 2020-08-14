package br.edu.ifpb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args)  {
        try {
            ServerAdministration server = new ServerAdministration(new ServerLogic(5));
            ServerAdm_IF stub2 = (ServerAdm_IF) UnicastRemoteObject.exportObject(server, 1026);
            Logic_IF stub = (Logic_IF) UnicastRemoteObject.exportObject(server.getServerLogic(), 1026);
            User_IF stub1 = (User_IF) UnicastRemoteObject.exportObject(server.getServerLogic().getGroupRepository(), 1026);
            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registry = LocateRegistry.createRegistry(1026);
            registry.bind("ServerLogic", stub);
            registry.bind("GroupRepository", stub1);
            registry.bind("ServerAdministration", stub2);
            System.out.println("Servidor ativado");
            server.startGame();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
