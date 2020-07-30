package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ServerAdm_IF extends Remote {

    void startGame() throws RemoteException;

    void changeAmount(int amount) throws RemoteException;

    void addTestGroup() throws RemoteException;

    void removeTestGroup() throws RemoteException;

    void cleanGroups() throws RemoteException;

    void cancelQuestion(String id) throws RemoteException;

    void resetQuestions() throws RemoteException;

    Map<String, Integer> getGroupScores() throws RemoteException;

    Map<String, Integer> getUsersScores() throws RemoteException;

    void resetPontos() throws RemoteException;

    void restartGame() throws RemoteException;

    void FinishServer() throws RemoteException;


}
