package br.edu.ifpb;

import java.rmi.Remote;
import java.util.List;

public interface RepoQuestoes_IF extends Remote {
    List<Questao> getQuestoes();
    void enviarRespota(String alternativa, String ID);
}
