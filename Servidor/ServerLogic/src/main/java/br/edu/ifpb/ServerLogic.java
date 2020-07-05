package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerLogic implements Logic_IF {
    private GroupRepository groupRepository;
    private QuestionRepository questionRepository;
    private Map<String, Integer> pointsPerQuestions;

    public ServerLogic(int year) {
        this.questionRepository = new QuestionRepository(year);
        this.groupRepository = new GroupRepository();
        this.pointsPerQuestions = new HashMap<>();
        this.setPointsPerQuestions(questionRepository.getQuestionsID());
    }

    public void setPointsPerQuestions(List<String> ids) { // seta os pontos extras de cada questão, quando um resposta estiver certa de uma questão, essa pontuação extra será diminuida
        for (String id : ids) {
            this.pointsPerQuestions.put(id, 3);
        }
    }
    public QuestionRepository getQuestionRepository() { return questionRepository; }
    public GroupRepository getGroupRepository() { return groupRepository; }

    @Override
    public List<Question> getQuestions() { // irá retorna as questões para o grupo poder responder
        return questionRepository.getQuestions();
    }
    @Override
    public void sendAnswer(int id, String QuestionID, String res) { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário
        Answer answer = new Answer(QuestionID, res); // cria um objeto Answer
        groupRepository.getGroups().forEach(group -> { // varredura com lambda para verificar qual grupo possui seu id igual ao parâmentro id, se for igual, o answer é adicionado no List answers
            if (group.getID() == id) {
                group.addAnswer(answer);
                questionRepository.getQuestions().forEach(question -> {
                    if (question.getId().equals(id) && questionRepository.getQuestionsMap().get(question).equals(res)) // varredura para saber se a respotas do grupo está certa, se sim, o grupo ganha o ponto
                        if (pointsPerQuestions.get(question.getId()) != 0) {
                            group.addPoints(pointsPerQuestions.get(question.getId()) + 1);
                            pointsPerQuestions.put(question.getId(), pointsPerQuestions.get(question) - 1);
                        }
                });
            }
        });
    }
    @Override
    public int getPoints(int id) { // irá pegar os pontos de um grupo, de acordo com um id dado
        AtomicInteger points = new AtomicInteger(); // uma váriavel int que pode ser atualizada atômicamente, ou seja, não é possivel que o escalandor quebre a execução do programa no instante da atualização
        groupRepository.getGroups().forEach(group -> { // varredura com lambda para verificar qual grupo possui seu id igual ao parâmentro id, se for igual, os pontos do grupo é retornato
            if (group.getID() == id) {
                points.set(group.getPoints());
            }
        });
        return points.get();
    }
    @Override
    public void finishRound() {

    }
    @Override
    public List<String> placarSources() throws RemoteException {
        return null;
    }
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
}
