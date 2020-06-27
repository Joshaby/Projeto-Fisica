package br.edu.ifpb;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnection {

    private QuestionRepository_IF repoQuestionsIf;
    private GroupRepository_IF repoGroupsIf;

    public ServerConnection() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            repoQuestionsIf = (QuestionRepository_IF) registry.lookup("RepoQuestoes");
            repoGroupsIf = (GroupRepository_IF) registry.lookup("RepoGrupos");
        }
        catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public QuestionRepository_IF getRepoQuestionsIf() { return repoQuestionsIf; }
    public GroupRepository_IF getRepoGroupsIf() { return repoGroupsIf; }
}
