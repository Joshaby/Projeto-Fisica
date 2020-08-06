package br.edu.ifpb;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private final ServerAdm_IF serverAdm;

    public RMIConnection(int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1026);
        serverAdm = (ServerAdm_IF) registry.lookup("ServerAdministration");
        registry = LocateRegistry.createRegistry(port);

    }

    public ServerAdm_IF getServerAdm() {
        return serverAdm;
    }
}
