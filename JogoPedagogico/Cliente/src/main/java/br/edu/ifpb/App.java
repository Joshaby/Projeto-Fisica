package br.edu.ifpb;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    public static void main( String[] args ) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            QuestionRepository_IF repoQuestoesIf = (QuestionRepository_IF) registry.lookup("RepoQuestoes");
            GroupRepository_IF repoGruposIf = (GroupRepository_IF) registry.lookup("RepoGrupos");
            System.out.printf(repoQuestoesIf.getQuestions().toString());
        }
        catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
