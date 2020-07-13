package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Logic_IF  extends Remote {
    void initializate(Map<String, List<String>> groups, int year) throws RemoteException;


    List<Question> getQuestions() throws RemoteException;
    void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException;
    int getPoints(String name) throws RemoteException;
    int getQuestionAmout() throws RemoteException;

    void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException;
    Map<String, List<String>> getGroupsMAP() throws RemoteException;
    Map<String, List<String>> getGroupByName(String name) throws RemoteException;
    List<String> realocateGroup(int round) throws RemoteException;
}
