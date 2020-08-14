package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerLogic implements Logic_IF {
// GLOBAL VARIABLES
    public GroupRepository groupRepository;
    public QuestionRepository questionRepository;
    public BonusQuestions bonusQuestions;
    public Integer round;
    public Integer amount;
    public boolean isGameStarted;

// CONSTRUCTOR without parameters
    public ServerLogic(){
        this.groupRepository = new GroupRepository();
        this.questionRepository = new QuestionRepository();
        this.setAmount(5);
        this.setRound(1);
        this.isGameStarted = false;
        this.bonusQuestions = new BonusQuestions(questionRepository, groupRepository, round, "");
    }

//CONSTRUCTOR with parameters

    public ServerLogic(Integer amount){
        this();
        this.setAmount(amount);
    }


//===================CLIENT METHODS===================


    //METHOD TO RECEIVE DATA FROM CLIENT
    @Override
    public void sendAnswer(int round, String GroupName, String QuestionID, String res, int time) throws RemoteException { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário

        if(!isGameStarted || round != getRound()) return;
        if(this.bonusQuestions.getState() && this.bonusQuestions.hasQuestion(QuestionID)) {
            this.bonusQuestions.addAnswer(GroupName, res);
            return;
        }

        Answer answer = new Answer(QuestionID, res, time); // cria um objeto Answer
        groupRepository.getGroupByName(GroupName).addAnswer(round, answer);
        if(questionRepository.validateAnswer(QuestionID,res)){
            groupRepository.getGroupByName(GroupName).addPoints(questionRepository.getPoints(QuestionID) + 1);
            questionRepository.decreasePoint(QuestionID);
        }

        if(this.finishRoundChecker()) {
            System.out.println("Round: "+ getRound());

            if( EndGameChecker() ) { isGameStarted = false; }

            this.bonusQuestionManager();

            if(isGameStarted){
                this.incrementRound();
                setQuestion();
            }
        }
    }

    //BONUS QUESTION CHECKER
    @Override
    public List<String> bonusQuestionCheck() throws RemoteException{

        // The game already ended.
        if(EndGameChecker()) {
            return Arrays.asList("EndOfTheGame", groupRepository.getGroups().iterator().next().getName());
        }

        // The round has finished, and a BonusQuestion has been generated, and is waiting for answers. With a draw has occurred.
        if(finishRoundChecker()) return bonusQuestions.getGroups();

        // The round and the game has not yet finish.
        return new ArrayList<>();
    }

    //METHOD THAT'S SEND QUESTIONS TO CLIENT SESSION
    @Override
    public List<Question> getQuestions(String GroupName) throws RemoteException {
        try {
            if ( this.getGameState() ) return null;
            if(this.questionRepository.getQuestionsID().size() == 0) setQuestion();

            List<String> ids = this.groupNotAnsweredQuestions(GroupName);

            ArrayList<Question> NotAnsweredQuestions = new ArrayList<>();

            questionRepository
                    .getQuestions()
                    .iterator()
                    .forEachRemaining(question -> {
                        if(ids.contains(question.getId())) NotAnsweredQuestions.add(question);
                    });

            if(bonusQuestions.getState()){
                System.out.println("Bonus ");
                if(bonusQuestions.hasGroup(GroupName)){
                    return NotAnsweredQuestions;
                }
                else return new ArrayList<>();
            }

            return NotAnsweredQuestions;
        } catch (ServerException e) {
            e.printStackTrace();
            return null;
        }
    }

    //will take the points of a group, according to a given id
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

    @Override
    public int totalNumberOfQuestions() throws RemoteException{
        return this.questionRepository.getQuestionsID().size();
    }

    @Override
    public Map<String, Integer> placarSources() throws RemoteException {
        Map<String, Integer> aux = new HashMap<>();
        this.groupRepository.getGroups().iterator().forEachRemaining(
                group -> {
                    aux.put(group.getName(), group.getPoints());
                });
        return aux;
    }

    @Override
    public int getQuestionAmount() throws RemoteException {
        return getAmount();
    }

    @Override
    public Integer getRound() throws RemoteException{
        return round;
    }

    @Override
    public boolean getGameState() throws RemoteException{
        return !this.isGameStarted;
    }


//===================GROUPS MANAGEMENT METHODS===================

    // CHECKING QUESTIONS ANSWERED BY THE GROUP
    private List<String> groupNotAnsweredQuestions(String name) throws RemoteException, ServerException {
        if(this.groupRepository.getGroupByName(name) == null) throw new ServerException(
                "O nome do grupo passado não se encontra cadastrado, ou não existe!" + name
        );

        if(getGroupRepository().getGroupByName(name).getAnswers() == null) {
            return questionRepository.getQuestionsID();
        }

        ArrayList<String> answeredIDs = new ArrayList<>();

        Answers ans = groupRepository.getGroupByName(name).getAnswersByRound(this.getRound());

        if(ans == null) {
            return questionRepository.getQuestionsID();
        }

        ans.getAnswers().iterator().forEachRemaining(answer -> answeredIDs.add(answer.getID()));

        ArrayList<String> aux = new ArrayList<>();
        questionRepository.getQuestionsID().iterator().forEachRemaining(s -> {
            if(!answeredIDs.contains(s)) aux.add(s);
        });
        return aux;
    }

    public GroupRepository getGroupRepository() { return groupRepository; }

    public void removeGroupByName(String name) throws RemoteException {
        this.groupRepository.removeGroupByName(name);
    }


//===================QUESTIONS MANAGEMENT METHODS===================

    // BONUS QUESTIONS MANAGEMENT METHOD
    private void bonusQuestionManager() throws RemoteException {

        List<String> aux = groupRepository.realocateGroup(getRound());

        if(aux != null)
            bonusQuestions =
                    new BonusQuestions(questionRepository,
                            groupRepository,
                            this.round,
                            questionRepository.bonusQuestionFetch(getRound(), groupRepository.getYear()));

        if( EndGameChecker() ) { isGameStarted = false; }
    }

    //METHOD THAT'S FETCH DATA FROM DATABASE
    /*fetch questions randomly from mongo and set the extra points for each question,
     when an answer get sended is correct, this score will be decreased.*/
    private void setQuestion(){
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

    // METHOD THAT CANCEL A SINGLE QUESTION AND FETCH ANOTHER IN PLACE
    public void cancelQuestion(String id){
        //Remove all groups answers for this question
        Map<String, Answer> aux = new HashMap<>();
        this.groupRepository.getGroups().iterator().forEachRemaining(group -> {
            try {
                if(group.getAnswersByRound(getRound()).hasAnswer(id)){
                    aux.put(group.getName(),group.getAnswersByRound(getRound()).getAnswer(id));
                    group.getAnswersByRound(getRound()).removeAnswerbyID(id);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        ArrayList<Answer> sortedList = new ArrayList<>();
        while (aux.size() > 0){
            int menor = (int) Double.POSITIVE_INFINITY;
            String menorG = "";
            for (String s : aux.keySet()) {
                if(aux.get(s).getTime() < menor){
                    menor = aux.get(s).getTime();
                    menorG = s;
                }
                System.out.println(sortedList);
            }
            sortedList.add(aux.get(menorG));
        }
    }


//===================GAME MANAGEMENT METHODS===================

    // METHOD THAT'S CHECK IF THE ROUND CAN BE FINISHED
    private boolean finishRoundChecker() {
        AtomicBoolean cond = new AtomicBoolean(true);

        this.groupRepository
                .getGroups()
                .iterator()
                .forEachRemaining(group -> {
                    try {
                        if(group.getAnswersByRound(getRound()).getAnswers().size() < this.getAmount()){
                            cond.set(false);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        return cond.get();
    }

    // CHECK IF GAME HAS ENDED OR NOT
    private boolean EndGameChecker(){
        return groupRepository.getGroups().size() == 1;
    }

    private void incrementRound() throws RemoteException {
        this.setRound(this.getRound() + 1);
    }

    public Map<String, Integer> UsersPlacarSources(){
        Map<String, Integer> aux = new HashMap<>();

        this.groupRepository
                .getGroups()
                .iterator()
                .forEachRemaining(group -> {
                    for (User member : group.getMembers()) {
                        aux.put(member.getName(), member.getPoints());
                    }
                });
        return aux;
    }


//===================GETTERS===================

    public Integer getAmount() {
        return amount;
    }


//===================SETTERS===================
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

    public void startGame(){
        this.isGameStarted = true;
    }
}
