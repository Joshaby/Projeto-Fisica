package br.edu.ifpb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Scoreboard_IF extends Remote {
    Map<Integer, List<String>> getScoreboard(int year) throws RemoteException;
}
