package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface User_IF extends Remote {

    void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException;

    Map<String, List<String>> getGroupsMAP() throws RemoteException;

    Map<String, List<String>> getGroupByNameMap(String name) throws RemoteException;

    boolean registerSingleGroup(String groupName, List<String> members, int year) throws RemoteException;

}
