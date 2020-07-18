package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerLogic implements Logic_IF {
// DEFAULT FINAL REPOS
    private final GroupRepository groupRepository;
    private final QuestionRepository questionRepository;

// DEFAULT VARIABLES
    private Integer round;
    private Integer amount;

// CONSTRUCTOR without parameters
    public ServerLogic() throws ServerException {
        this.groupRepository = new GroupRepository();
        this.questionRepository = new QuestionRepository();
        this.setAmount(5);
        this.setRound(1);
    }

//CONSTRUCTOR with parameters

    public ServerLogic(Integer amount) throws ServerException {
        this();
        this.setAmount(amount);
    }


// METHOD TO RECIEVE DATA FROM CLIENT

    @Override
    public void sendAnswer(int round, String GroupName, String QuestionID, String res, int time) throws RemoteException { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário
        Answer answer = new Answer(QuestionID, res); // cria um objeto Answer
        groupRepository.getGroups().forEach(group -> { // varredura com lambda para verificar qual grupo possui seu nome igual ao parâmentro name, se for igual, o answer é adicionado no HashMap de respostas.
            if (group.getName().equals(GroupName)) {
                group.addAnswer(round, answer, time);
                questionRepository.getQuestions().forEach(question -> {
                    if (question.getId().equals(QuestionID) && questionRepository.getQuestionsMap().get(question).equals(res)) { // varredura para saber se a respotas do grupo está certa, se sim, o grupo ganha o ponto
                        if (questionRepository.getPoints(question.getId()) != 0) {
                            group.addPoints(questionRepository.getPoints(question.getId()) + 1);
                            questionRepository.decreasePoint(question.getId());
                        }
                    }
                });
            }
        });
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

    private List<String> groupNotAnsweredQuestions(String name) throws RemoteException {
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

        setQuestion();

        ArrayList<Question> NotAnsweredQuestions = new ArrayList<>();

        List<String> ids = this.groupNotAnsweredQuestions(GroupName);

        questionRepository
                .getQuestions()
                .iterator()
                .forEachRemaining(question -> {
            if(ids.contains(question.getId())) NotAnsweredQuestions.add(question);
        });

        return NotAnsweredQuestions;
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

    public void setAmount(Integer amount) throws ServerException{
            if(amount <= 0) throw new ServerException("Número de questões invalido!");
        this.amount = amount;
    }

//OTHERS METHODS

    public void finishRound() {
        //todo
    }

    @Override
    public List<String> placarSources() throws RemoteException {
        return null;
    }
}

//        Answer answer = new Answer(QuestionID, res);
//        for (Group group : groupRepository.getGroups()) {
//            if (group.getName().equals(name)) {
//                group.addAnswer(round, answer, time);
//                for (Question question : questionRepository.getQuestions()) {
//                    if (question.getId().equals(QuestionID) && questionRepository.getQuestionsMap().get(question).equals(res)) {
//                        if (pointsPerQuestions.get(question.getId()) != 0) {
//                            group.addPoints(pointsPerQuestions.get(question.getId()) + 1);
//                            pointsPerQuestions.put(question.getId(), pointsPerQuestions.get(question.getId()) - 1);
//                        }
//                    }
//                }
//            }
//}
//        System.out.println(name);
//        System.out.println(QuestionID);
//        System.out.println(res);

//    @Override
//    public String toString() {
//        String answerString = "";
//        for (Group group : answers.keySet()) {
//            answerString += group + ", Resposta = " + answers.get(group) + '\n';
//        }
//
//        String pointString = "";
//        for (Group group : points.keySet()) {
//            pointString += group + ", Pontos = " + points.get(group) + "\n";
//        }
//
//        return "Server:" + "\n\n" +
//                "Answers:\n" + answerString + "\n\n" +
//                "Points:\n" + pointString + "\n\n" +
//                "PointsPerQuestions:\n" + pointsPerQuestions
//                ;
//    }