package br.edu.ifpb;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ServerConnection {

    private Logic_IF connection;
    private User_IF connection1;
    private Stack<Question> questions;

    public ServerConnection() {
        try {
            questions = new Stack<>();
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // irá estabelecer conexão com o servidor
            connection = (Logic_IF) registry.lookup("ServerLogic"); // irá pegar a referência do stub RepoQuestoes
            connection1 = (User_IF) registry.lookup("GroupRepository"); // irá pegar a referência do stub GroupConnection
        }
        catch (NotBoundException | IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public boolean initializateQuestions(String groupName) throws RemoteException {
        List<Question> aux = getQuestionsList(groupName);
        if (aux == null) return true;
        if (aux.isEmpty()) return false;
        for (Question question : aux) questions.push(question);
        return false;
    }

    public int getPoints(String name) throws RemoteException { return connection.getPoints(name); }
    public void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException { // vai enviar a respotas por servidor
        connection.sendAnswer(round, name, QuestionID, res, time);
    }
    public Logic_IF getConnection() { return connection; }
    public int currentQuestionsNumber() {return questions.size();}
    public User_IF getConnection1() { return connection1; }
    public Stack<Question> getQuestions() { return questions; }
    public int getRound() throws RemoteException { return connection.getRound(); }
    public int getQuestionAmount() { return questions.size(); }
    public List<String> bonusQuestionCheck() throws RemoteException { return connection.bonusQuestionCheck(); }
    public List<Question> getQuestionsList(String groupName) throws RemoteException { return connection.getQuestions(groupName); }
}
