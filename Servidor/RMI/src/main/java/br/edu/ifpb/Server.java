package br.edu.ifpb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args)  {
        try {
            ServerLogic serverLogic = new ServerLogic(2);
            Logic_IF stub = (Logic_IF) UnicastRemoteObject.exportObject(serverLogic, 0);
            System.setProperty("java.rmi.server.hostname","localhost");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("ServerLogic", stub);
            System.out.println("Servidor ativado");
            serverLogic.getQuestionRepository().setQuestions(new String[]{"Média", "", ""}, 10);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
