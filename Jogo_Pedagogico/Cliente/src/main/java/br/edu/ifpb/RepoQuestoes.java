package br.edu.ifpb;

import java.util.HashMap;
import java.util.Map;

public class RepoQuestoes implements RepoQuestoes_IF{
    private Map<String, Questao> questoes = new HashMap<>();
    private Map<Grupo, Map<String, String>> repostas;
    private int tipo;

    @Override
    public Map<String, Questao> getQuestoes() {
        return null;
    }

    @Override
    public void enviarRespota(String alternativa, String ID) {

    }
}
