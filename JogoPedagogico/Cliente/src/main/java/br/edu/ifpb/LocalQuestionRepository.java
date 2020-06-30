package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.Stack;

public class LocalQuestionRepository {
    private ServerConnection serverConnection;
    private Stack<Question> questions;

    public LocalQuestionRepository(String ip) {
        try {
            serverConnection = new ServerConnection(ip);
            questions = new Stack<>();
            questions.addAll(serverConnection.getRepoQuestionsIf().getQuestions());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
