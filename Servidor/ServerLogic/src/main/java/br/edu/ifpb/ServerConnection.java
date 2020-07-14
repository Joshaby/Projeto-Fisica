package br.edu.ifpb;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

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
    private Integer amount;

//Construtor padrão
    public ServerConnection() {
        groupRepo = new GroupRepository();
        questRepo = new QuestionRepository();
        setYear(0);
        setRound(0);
        setAmount(5);
    }

// Construtor com número de questões variável
    public ServerConnection(Integer amount) {
        this();
        setAmount(amount);
    }

/*
    Função responsável por receber os dados dos grupos cadastrados pela interface de login,
    recebendo um mapa de que tem sua estrutura como: Uma string que é o nome do grupo determinado
    pela equipe, e uma lista de strings, com o nome de todos os participantes do grupo.

    Após impregar o metodo 'registerGroups' da classe GroupRepository, a função envia a o ano dos
    grupos, o número de questões predefinidos no servidor e o inteiro '1', que representa o primeiro round.

    Esta função deve ser utilizada aṕos a coleta de todos os grupos.
*/
    @Override
    public void initializate(Map<String, List<String>> groups, int year) throws RemoteException{
        try{
            if(getYear() > 0 && year != getYear()) throw new ServerException("Erro no valor do ano recebido");
            if(groups == null) throw new ServerException("Erro no mapa de grupos: null");

            groupRepo.registerGroups(groups, year);
            questRepo.setQuestions(1, this.getAmount(), year);
            setYear(year);
            setRound(1);
        }
        catch (ServerException se){
            System.out.println(se.toString());
        }
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
    public int getQuestionAmout() throws RemoteException {
        return 0;
    }

    @Override
    public void registerGroups(Map<String, List<String>> groups, int year) throws RemoteException {


        groupRepo.registerGroups(groups, year);
    }

    @Override
    public Map<String, List<String>> getGroupsMAP() throws RemoteException {
        return null;
    }

    @Override
    public Map<String, List<String>> getGroupByName(String name) throws RemoteException {
        return null;
    }

    @Override
    public List<String> realocateGroup(int round) throws RemoteException {
        return null;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRound() {
        return round;
    }
    public void setRound(Integer round) {
        this.round = round;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

