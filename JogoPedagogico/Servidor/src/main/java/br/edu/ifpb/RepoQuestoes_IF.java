package br.edu.ifpb;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

public interface RepoQuestoes_IF extends Remote {
    Map<Questao, String> getQuestoes();
    void enviarRespota(String alternativa, String ID);
}
