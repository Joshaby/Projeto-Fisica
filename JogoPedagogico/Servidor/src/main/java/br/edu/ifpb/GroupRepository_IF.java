package br.edu.ifpb;

import java.rmi.Remote;

public interface GroupRepository_IF extends Remote {
    void cadastrarGrupo();
    boolean checkGroup();
}
