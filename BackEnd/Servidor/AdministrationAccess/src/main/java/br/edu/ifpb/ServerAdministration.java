package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class ServerAdministration implements ServerAdm_IF{

    private final ServerLogic serverLogic;

    public ServerAdministration(ServerLogic sl) {
        this.serverLogic = sl;
    }

    // ServerAdm Methods

    @Override
    public void startGame()  throws RemoteException {
        System.out.println("The game has been started!");
        serverLogic.startGame();
    }

    @Override
    public void addTestGroup() throws RemoteException {
        System.out.println("Adding test group");
        serverLogic.groupRepository.registerSingleGroup("test", Arrays.asList("asdf","qwert","gfjd"), serverLogic.groupRepository.getYear());
    }

    @Override
    public void removeTestGroup() throws RemoteException {
        serverLogic.groupRepository.removeGroupByName("test");
    }

    @Override
    public void cleanGroups() throws RemoteException {
        serverLogic.groupRepository.setGroups(new HashSet<>());
    }

    public void changeAmount(int amount) throws RemoteException{
        if (serverLogic.getGameState()) {
            System.out.println("Mudando o número de questões para " + amount);
            serverLogic.setAmount(amount);
            serverLogic.questionRepository.resetQuestions(serverLogic.getRound(),
                                                          serverLogic.getAmount(),
                                                          serverLogic.groupRepository.getYear());
        }
    }

    @Override
    public Map<String, Integer> getGroupScores() throws RemoteException{
        return serverLogic.placarSources();
    }

    @Override
    public Map<String, Integer> getUsersScores() throws RemoteException{
        return serverLogic.UsersPlacarSources();
    }


    @Override
    public void cancelQuestion(String id) throws RemoteException {
        System.out.println("Pedido de cancelamento recebido no round: " + serverLogic.getRound() + ", Questao: " + id);
        serverLogic.cancelQuestion(id);
    }

    @Override
    public void resetQuestions() throws RemoteException {
        serverLogic.groupRepository.resetPoints(serverLogic.getRound());
        serverLogic.questionRepository.resetQuestions(serverLogic.getRound(),
                                                      serverLogic.getAmount(),
                                                      serverLogic.groupRepository.getYear());
    }

    @Override
    public void resetPontos() throws RemoteException {
        serverLogic.groupRepository.resetAllPoints();
    }

    @Override
    public void restartGame() throws RemoteException {
        serverLogic.isGameStarted = false;
        serverLogic.setRound(1);
        this.resetQuestions();
        this.resetPontos();
        System.out.println("O jogo foi reiniciado!");
    }

    @Override
    public void FinishServer() throws RemoteException {
        System.out.println("Desligando servidor...");
        System.exit(0);
    }

    public ServerLogic getServerLogic() {
        return serverLogic;
    }
}
