package br.edu.ifpb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {
    public static void main(String[] args)  {
        try {
            QuestionRepository questionRepository = new QuestionRepository(2);
            GroupRepository repoGrupos = new GroupRepository();
            QuestionRepository_IF stub = (QuestionRepository_IF) UnicastRemoteObject.exportObject(questionRepository, 0);
            System.setProperty("java.rmi.server.hostname","localhost");
            GroupRepository_IF stub1 = (GroupRepository_IF) UnicastRemoteObject.exportObject(repoGrupos, 5353);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RepoQuestoes", stub);
            registry.bind("RepoGrupos", stub1);
            System.out.println("Servidor ativado");
            questionRepository.setQuestions(new String[]{"Média"}, 1);
            System.out.printf(stub.getQuestions().toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
