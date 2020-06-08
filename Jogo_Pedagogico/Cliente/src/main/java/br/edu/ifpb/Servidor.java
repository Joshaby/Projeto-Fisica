package br.edu.ifpb;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {
    public static void main(String[] args)  {
        try{
            RepoQuestoes repoQuestoes = new RepoQuestoes();
            RepoGrupos repoGrupos = new RepoGrupos();
            RepoQuestoes_IF stub = (RepoQuestoes_IF) UnicastRemoteObject.exportObject(repoQuestoes, 0);
            RepoGrupos_IF stub1 = (RepoGrupos_IF) UnicastRemoteObject.exportObject(repoGrupos, 1);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RepoQuestoes", stub);
            registry.bind("RepoGrupos", stub1);
            System.out.println("Servidor ativado");
        }
        catch (RemoteException | AlreadyBoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
