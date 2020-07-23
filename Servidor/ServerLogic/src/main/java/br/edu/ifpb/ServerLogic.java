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
    public ServerLogic() {
        this.groupRepository = new GroupRepository();
        this.questionRepository = new QuestionRepository();
        setAmount(5);
        setRound(1);
    }

//CONSTRUCTOR with parameters

    public ServerLogic(Integer amount) throws ServerException {
        this();
        this.setAmount(amount);
    }


// METHOD TO RECIEVE DATA FROM CLIENT

    @Override
    public void sendAnswer(int round, String name, String QuestionID, String res, int time) throws RemoteException { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário
        Answer answer = new Answer(QuestionID, res); // cria um objeto Answer
        groupRepository.getGroups().forEach(group -> { // varredura com lambda para verificar qual grupo possui seu nome igual ao parâmentro name, se for igual, o answer é adicionado no HashMap de respostas.
            if (group.getName().equals(name)) {
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

        if (this.finishRoundChecker()) {
            this.incrementRound();
            this.setQuestion();
        }
    }


//METHOD THAT'S FETCH DATA FROM DATABASE

    /*seleciona as questões randomicamente no mongo e seta os pontos extras de cada questão,
      quando uma resposta enviada estiver certa, essa pontuação será decrementada.*/
    public void setQuestion() {
        try {
            if (this.groupRepository.getYear() < 0)
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
        if (this.groupRepository.getGroupByName(name) == null) throw new ServerException(
                "O nome do grupo passado não se encontra cadastrado, ou não existe!" + name
        );

        ArrayList<String> answeredIDs = new ArrayList<>();

        getGroupRepository()
                .getGroupByName(name)
                .getAnswers()
                .get(getRound())
                .getAnswers()
                .iterator()
                .forEachRemaining(answer -> {
                    answeredIDs.add(answer.getID());
                });

        ArrayList<String> aux = new ArrayList<>();

        questionRepository.getQuestionsID().iterator().forEachRemaining(s -> {
            if (!answeredIDs.contains(s)) aux.add(s);
        });

        return aux;
    }


//METHOD THAT'S SEND QUESTIONS TO CLIENT SESSION

    public List<Question> getQuestions(String GroupName) throws RemoteException {
        try {

            List<String> ids = groupNotAnsweredQuestions(GroupName);

            if (this.questionRepository.getQuestionsID().size() == 0) setQuestion();

            ArrayList<Question> NotAnsweredQuestions = new ArrayList<>();

            questionRepository
                    .getQuestions()
                    .iterator()
                    .forEachRemaining(question -> {
                        if (ids.contains(question.getId())) NotAnsweredQuestions.add(question);
                    });
            return NotAnsweredQuestions;
        } catch (ServerException err) {
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
                    if (group.getAnswers().keySet().size() < this.questionRepository.getQuestionsID().size()) {
                        cond.set(false);
                    }
                });
        return cond.get();
    }


// GETTERS

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

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

    public void setAmount(Integer amount) {
        try {
            if (amount <= 0) throw new ServerException("Número de questões invalido!");
            this.amount = amount;
        } catch (ServerException err) {
            err.printStackTrace();
        }
    }

//OTHERS METHODS

    private void incrementRound() {
        setRound(getRound() + 1);
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