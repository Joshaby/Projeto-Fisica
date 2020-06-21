package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface RepoQuestoes_IF extends Remote {
    Map<String, String> getQuestoes() throws RemoteException;
    void enviarRespota(String alternativa, String ID) throws RemoteException;
}
