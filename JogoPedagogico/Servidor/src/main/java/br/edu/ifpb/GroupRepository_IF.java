package br.edu.ifpb;

import java.rmi.Remote;

public interface GroupRepository_IF extends Remote {
    void registerGroup();
    boolean checkGroup();
}
