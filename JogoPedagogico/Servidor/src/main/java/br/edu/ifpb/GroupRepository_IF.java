package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupRepository_IF extends Remote {
    void registerGroup() throws RemoteException;
    boolean checkGroup() throws RemoteException;
}
