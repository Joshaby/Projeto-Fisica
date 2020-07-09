package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Logic_IF  extends Remote {
    List<Question> getQuestions() throws RemoteException;
    void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException;
    int getPoints(String name) throws RemoteException;
    void finishRound() throws RemoteException;
    List<String> placarSources() throws RemoteException;
    int getQuestionAmout() throws RemoteException;
}
