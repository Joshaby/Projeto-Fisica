package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.List;

/*
    Classe principal do Servidor, na qual todas as lógicas e operações centrais são implementadas e execultadas
    para pós processamento, seus resultados e consultas sejam direcionados a conecção rmi e posteriormente ao cliente.

    groupRepo: Constante que ira receber a classe GroupRepository que será inicializada e utilizada como tal;
    questRepo: Constante que ira receber a classe QuestionRepository que será inicializada e utilizada como tal;
 */

public class ServerConnection implements Logic_IF{

    private static GroupRepository groupRepo;
    private static QuestionRepository questRepo;
    private Integer year;
    private Integer round;

    public ServerConnection() {
        groupRepo = new GroupRepository();
        questRepo = new QuestionRepository();
    }

    @Override
    public List<Question> getQuestions() throws RemoteException {
        return null;
    }

    @Override
    public void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException {

    }

    @Override
    public int getPoints(String name) throws RemoteException {
        return 0;
    }

    @Override
    public void finishRound() throws RemoteException {

    }

    @Override
    public List<String> placarSources() throws RemoteException {
        return null;
    }

    @Override
    public int getQuestionAmout() throws RemoteException {
        return 0;
    }
}
