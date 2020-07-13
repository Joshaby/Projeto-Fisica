package br.edu.ifpb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args)  {
        try {
            GroupRepository groupRepository = new GroupRepository();
            ServerLogic serverLogic = new ServerLogic(2, groupRepository);
            Logic_IF stub = (Logic_IF) UnicastRemoteObject.exportObject(serverLogic, 0);
            User_IF stub1 = (User_IF) UnicastRemoteObject.exportObject(groupRepository, 5355);
            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ServerLogic", stub);
            registry.bind("GroupRepository", stub1);
            System.out.println("Servidor ativado");
            serverLogic.setQuestionAndPoints(2, 12);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
