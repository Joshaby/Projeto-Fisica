package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Logic_IF  extends Remote {
    List<Question> getQuestions(String GroupName) throws RemoteException;
    void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException;
    int getPoints(String name) throws RemoteException;
    int totalNumberOfQuestions() throws RemoteException;
    Map<String, Integer> placarSources() throws RemoteException;
    int getQuestionAmout() throws RemoteException;
    void removeGroupByName(String name) throws RemoteException;
}
