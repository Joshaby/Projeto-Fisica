package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface QuestionRepository_IF extends Remote {
    Map<Question, String> getQuestions() throws RemoteException;
    void sendAnswer(Group group, Answer answer) throws RemoteException;
}