package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QuestionRepository_IF extends Remote {
    List<Question> getQuestions() throws RemoteException;
}