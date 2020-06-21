package br.edu.ifpb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {
    public static void main(String[] args)  {
        try {
            RepoQuestoes repoQuestoes = new RepoQuestoes();
            RepoGrupos repoGrupos = new RepoGrupos();
            RepoQuestoes_IF stub = (RepoQuestoes_IF) UnicastRemoteObject.exportObject(repoQuestoes, 0);
            System.setProperty("java.rmi.server.hostname","localhost");
            RepoGrupos_IF stub1 = (RepoGrupos_IF) UnicastRemoteObject.exportObject(repoGrupos, 5353);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RepoQuestoes", stub);
            registry.bind("RepoGrupos", stub1);
            System.out.println("Servidor ativado");
            repoQuestoes.setQuestoes("Média", 1);
            System.out.printf(stub.getQuestoes().toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}