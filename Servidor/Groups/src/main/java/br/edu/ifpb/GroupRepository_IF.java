package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;

public interface GroupRepository_IF extends Remote {
    boolean generateGroups(List<User> Users) throws RemoteException;
    boolean checkGroup() throws RemoteException;
    boolean removeGroup() throws RemoteException;
    boolean setMaxGroup(int maxGroup);
    int getMaxGroup();
    boolean setGroups(HashSet<Group> groups);

}
