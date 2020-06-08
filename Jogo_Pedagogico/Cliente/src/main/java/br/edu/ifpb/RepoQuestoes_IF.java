package br.edu.ifpb;

import java.rmi.Remote;
import java.util.Map;

public interface RepoQuestoes_IF extends Remote {
    Map<String, Questao> getQuestoes();
    void enviarRespota(String alternativa, String ID);
}
