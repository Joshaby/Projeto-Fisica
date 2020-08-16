package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Logic_IF  extends Remote {
    void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException;
    List<String> bonusQuestionCheck() throws RemoteException;
    List<Question> getQuestions(String GroupName) throws RemoteException;
    int getPoints(String name) throws RemoteException;
    int totalNumberOfQuestions() throws RemoteException;
    Map<String, Integer> placarSources() throws RemoteException;
    int getQuestionAmount() throws RemoteException;
    Integer getRound() throws RemoteException;
    boolean getGameState() throws RemoteException;
}
