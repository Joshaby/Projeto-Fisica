package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface User_IF extends Remote {

    void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException;

    Set<Group> getGroups() throws RemoteException;

    Group getGroupByName(String name) throws RemoteException;

    List<String> realocateGroup(int round) throws RemoteException;

}
