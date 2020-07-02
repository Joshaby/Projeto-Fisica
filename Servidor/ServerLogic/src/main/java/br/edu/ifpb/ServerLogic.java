package br.edu.ifpb;

import java.util.*;

public class ServerLogic {
    private Map<Group, List<Answer>> answers;
    private Map<Group, Integer> points;
    private Map<String, Integer> pointsPerQuestions;
    private GroupRepository groupRepository;
    private QuestionRepository questionRepository;

    public ServerLogic(int year) {
        this.answers = new HashMap<>();
        this.points = new HashMap<>();
        this.pointsPerQuestions = new HashMap<>();
        this.questionRepository = new QuestionRepository(year);
        this.groupRepository = new GroupRepository();
        this.setPointsPerQuestions(questionRepository.getQuestionsID());
    }

    // Parâmetros
    //      Group grou = grupo
    //      Answer answer = objeto resposta que contêm uma resposta e id de uma questão

    public void sendAnswer(Group group, Answer answer) { // irá receber uma resposta de um grupo, o objeto Answer guarda o ID de um questão e a possível resposta da questão pelo usuário
        if (! answers.isEmpty()) { // verifica se o Map de respostas está vazio
            if (answers.containsKey(group)) { // verifica se o mapa contêm o grupo que enviou a resposta
                List<Answer> aux = new ArrayList<>(); // aqui em diante, será pego as respostas do grupo que acabou de enviar uma respotas, e colocara num List local, aux, isso porque não é possível mexer diretamente no List que está em answers. Depois de ter pego, é feito um put para atualizar o List de questões
                aux.add(answer);
                aux.addAll(answers.get(group));
                answers.put(group, aux);
            }
            else answers.put(group, Collections.singletonList(answer)); // se o grupo não estiver presente, será adicionado sem complicações
        }
        else answers.put(group, Collections.singletonList(answer)); // se o List answer não conter nda, será colocado o grupo sem complicações
        calculatePoints(group, answer); // ver comentário da função
    }

    // Parâmetros
    //      Group grou = grupo
    //      Answer answer = objeto resposta que contêm uma resposta e id de uma questão

    public void calculatePoints(Group group, Answer answer) { // calcula o ponto
        if (! points.isEmpty()) {
            if (points.containsKey(group)) {
                for (Question question : questionRepository.getQuestionsMap().keySet()) {
                    if (question.getId().equals(answer.getID()) && questionRepository.getQuestionsMap().get(question).equals(answer.getAnswer())) {
                        points.put(group, points.get(group) + 1 + pointsPerQuestions.get(answer.getID()));
                        if (pointsPerQuestions.get(answer.getID()) != 0)
                            pointsPerQuestions.put(answer.getID(), pointsPerQuestions.get(answer.getID()) - 1);
                        break;
                    }
                }
            }
            else {
                points.put(group, 1 + pointsPerQuestions.get(answer.getID()));
                if (pointsPerQuestions.get(answer.getID()) != 0)
                    pointsPerQuestions.put(answer.getID(), pointsPerQuestions.get(answer.getID()) - 1);
            }
        }
        else {
            points.put(group, 1 + pointsPerQuestions.get(answer.getID()));
            if (pointsPerQuestions.get(answer.getID()) != 0)
                pointsPerQuestions.put(answer.getID(), pointsPerQuestions.get(answer.getID()) - 1);
        }
    }

    public Map<Group, List<Answer>> getAnswers() {
        return answers;
    }

    public Map<Group, Integer> getPoints() {
        return points;
    }

    public void setPointsPerQuestions(List<String> ids) {
        for (String id : ids) {
            this.pointsPerQuestions.put(id, 3);
        }
    }

    @Override
    public String toString() {
        String answerString = "";
        for (Group group : answers.keySet()) {
            answerString += group + ", Resposta = " + answers.get(group) + '\n';
        }

        String pointString = "";
        for (Group group : points.keySet()) {
            pointString += group + ", Pontos = " + points.get(group) + "\n";
        }

        return "Server:" + "\n\n" +
                "Answers:\n" + answerString + "\n\n" +
                "Points:\n" + pointString + "\n\n" +
                "PointsPerQuestions:\n" + pointsPerQuestions
                ;
    }
}
