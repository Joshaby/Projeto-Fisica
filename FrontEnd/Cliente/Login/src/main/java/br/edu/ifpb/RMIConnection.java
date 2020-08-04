package br.edu.ifpb;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private final User_IF user_if;

    public RMIConnection(int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        user_if = (User_IF) registry.lookup("GroupRepository");
        registry = LocateRegistry.createRegistry(port);

    }

    public User_IF getUser_if() {
        return user_if;
    }
}
