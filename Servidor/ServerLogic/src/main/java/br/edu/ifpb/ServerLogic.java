package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerLogic implements Logic_IF {
// DEFAULT FINAL REPOS
    private final GroupRepository groupRepository;
    private final QuestionRepository questionRepository;

// DEFAULT VARIABLES
    private Integer round;
    private Integer amount;

// CONSTRUCTOR without parameters
    public ServerLogic(){
        this.groupRepository = new GroupRepository();
        this.questionRepository = new QuestionRepository();
        this.setAmount(5);
        this.setRound(1);
    }

//CONSTRUCTOR with parameters

    public ServerLogic(Integer amount){
        this();
        this.setAmount(amount);
    }


// METHOD TO RECIEVE DATA FROM CLIENT

    @Override
    public void sendAnswer(int round, String GroupName, String QuestionID, String res, int time) throws RemoteException { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário
        Answer answer = new Answer(QuestionID, res); // cria um objeto Answer
        groupRepository.getGroupByName(GroupName).addAnswer(round, answer, time);
        if(questionRepository.validateAnswer(QuestionID,res)){
            groupRepository.getGroupByName(GroupName).addPoints(questionRepository.getPoints(QuestionID) + 1);
            questionRepository.decreasePoint(QuestionID);
        }

        if(this.finishRoundChecker()) {
            this.incrementRound();
            setQuestion();
        }
    }


//METHOD THAT'S FETCH DATA FROM DATABASE

    /*seleciona as questões randomicamente no mongo e seta os pontos extras de cada questão,
      quando uma resposta enviada estiver certa, essa pontuação será decrementada.*/
    public void setQuestion(){
        try{
            if(this.groupRepository.getYear() < 0)
                throw new ServerException("Repositório de grupos não foi inicializado");

            this.questionRepository.setQuestions(this.getRound(),
                                                 this.getAmount(),
                                                 this.groupRepository.getYear());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


// CHECKING QUESTIONS ANSWERED BY THE GROUP

    private List<String> groupNotAnsweredQuestions(String name) throws RemoteException, ServerException {
        if(this.groupRepository.getGroupByName(name) == null) throw new ServerException(
                "O nome do grupo passado não se encontra cadastrado, ou não existe!" + name
        );

        if(getGroupRepository().getGroupByName(name).getAnswers().isEmpty()) {
            return questionRepository.getQuestionsID();
        }

        ArrayList<String> answeredIDs = new ArrayList<>();

        getGroupRepository()
                .getGroupByName(name)
                .getAnswers()
                .get(this.getRound())
                .getAnswers()
                .iterator()
                .forEachRemaining(answer -> {
                answeredIDs.add(answer.getID());
        });

        ArrayList<String> aux = new ArrayList<>();
        questionRepository.getQuestionsID().iterator().forEachRemaining(s -> {
            if(!answeredIDs.contains(s)) aux.add(s);
        });
        return aux;
    }


//METHOD THAT'S SEND QUESTIONS TO CLIENT SESSION

    public List<Question> getQuestions(String GroupName) throws RemoteException {
        try {

            if(this.questionRepository.getQuestionsID().size() == 0) setQuestion();

            List<String> ids = this.groupNotAnsweredQuestions(GroupName);

            ArrayList<Question> NotAnsweredQuestions = new ArrayList<>();

            questionRepository
                    .getQuestions()
                    .iterator()
                    .forEachRemaining(question -> {
                        if(ids.contains(question.getId())) NotAnsweredQuestions.add(question);
                    });
            return NotAnsweredQuestions;
        }
        catch (ServerException err){
            err.printStackTrace();
            return null;
        }
    }


// METHOD THAT'S CHECK IF THE ROUND CAN BE FINISHED

    public boolean finishRoundChecker() {
        AtomicBoolean cond = new AtomicBoolean(true);

        this.groupRepository
                .getGroups()
                .iterator()
                .forEachRemaining(group -> {
                    if(group.getAnswers().keySet().size() < this.questionRepository.getQuestionsID().size()){
                        cond.set(false);
                    }
                });
        return cond.get();
    }


// GETTERS

    public GroupRepository getGroupRepository() { return groupRepository; }

    // irá pegar os pontos de um grupo, de acordo com um id dado
    @Override
    public int getPoints(String name) throws RemoteException {
        AtomicInteger points = new AtomicInteger(); // uma váriavel int que pode ser atualizada atômicamente, ou seja, não é possivel que o escalandor quebre a execução do programa no instante da atualização
        groupRepository.getGroups().forEach(group -> { // varredura com lambda para verificar qual grupo possui seu id igual ao parâmentro id, se for igual, os pontos do grupo é retornato
            if (group.getName().equals(name)) {
                points.set(group.getPoints());
            }
        });
        return points.get();
    }

    public Integer getRound() {
        return round;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public int getQuestionAmout() throws RemoteException {
        return questionRepository.getQuestions().size();
    }


//SETTERS

    public void setRound(Integer round) {
        this.round = round;
    }

    public void setAmount(Integer amount){
        try {
            if(amount <= 0) throw new ServerException("Número de questões invalido!");
            this.amount = amount;
        }catch (ServerException err) {
            err.printStackTrace();
        }
    }

//OTHERS METHODS

    public int totalNumberOfQuestions() throws RemoteException{
        return this.questionRepository.getQuestionsID().size();
    }

    private void incrementRound(){
        this.setRound(this.getRound() + 1);
    }

    @Override
    public Map<String, Integer> placarSources() throws RemoteException {
        Map<String, Integer> aux = new HashMap<>();

        this.groupRepository
                .getGroups()
                .iterator()
                .forEachRemaining(group -> {
                    aux.put(group.getName(), group.getPoints());
                });
        return aux;
    }
}
