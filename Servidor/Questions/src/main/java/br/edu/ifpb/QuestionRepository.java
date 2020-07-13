package br.edu.ifpb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.sample;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class QuestionRepository { // classe que representa um repositório de questões

    private static final MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");
    private Map<Question, Integer> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor

    public QuestionRepository() {
        questions = new HashMap<>();
    }

    public List<Question> getQuestions() { // pega as questões para uso do grupo
        return Collections.unmodifiableList(List.copyOf(questions.keySet()));
    }

    public List<String> getQuestionsID() { // pega o ID das questões para definir a pontuação extra de cada função
        List<String> aux = new ArrayList<>();
        questions.keySet().forEach(question -> aux.add(question.getId()));
        return aux;
    }

    // Parâmetros
    //      String[] difficulties = lista de dificuldades, por exemplo {"Fácil", "Média"}
    //      int amount = quantidade de questões randomizadas a serem buscadas

    public void setQuestions(int round, int amount, int year) { // método para setar as questões para uso do grupo
        try {
            MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
            MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões

            int stockAmount = amount;
            for (int i = 1; i <= year; i++) {
                int localAmount = (int) Math.ceil(stockAmount / (double) year);
                amount -= localAmount;
                if (localAmount > amount + localAmount) localAmount += amount;
                int finalI = i;
                dataBase.getCollection(i + " ano").aggregate(Arrays.asList(
                        match(or(eq("Dificuldade", (round >= 1 && round <=2)? "Fácil" : ""),
                                eq("Dificuldade", (round >= 2 && round <=4)? "Média" : ""),
                                eq("Dificuldade", (round >= 4)? "Difícil" : ""))),
                        sample(localAmount))).iterator().forEachRemaining(document -> { addQuestions(document, finalI); });
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
        aqui em diante, será pego os dados dos documentos em randomQuestions, será feito castings e uma verificação
        para definir o tipo de questão a partir de seus dados.

     */

    private void addQuestions(Object randomQuestion, int y) {
        Document document = (Document) randomQuestion;
        String id = (String) document.get("ID");
        String difficulty = (String) document.get("Dificuldade");
        String text = (String) document.get("Texto");
        String correctAlternative = (String) document.get("Alternativa correta");
        List<String> alternatives = (List<String>) document.get("Alternativas");
        List<String> images = (List<String>) document.get("Imagens");
        if (alternatives == null) {
            if (images == null) {
                questions.put(new Question(id, difficulty, text, correctAlternative), 5);
            } else questions.put(new Question(id, difficulty, text, correctAlternative, images), 5);
        } else {
            if (images == null) {
                questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, correctAlternative, false), 5);
            } else
                questions.put(new MultipleChoiceQuestion(id, difficulty, text, images, alternatives, correctAlternative, true), 5);
        }
        System.out.println(id + " - " + difficulty + " - " + correctAlternative + " - " + y + " ano");
    }

    @Override
    public String toString() {

        String questionsString = "";
        for (Question question : questions.keySet()) {
            questionsString += question + ", resposta = " + question.getCorrectAnswer() + '\n';
        }
        return "QuestionRepository{\n" +
                "Questions:\n" + questionsString + "\n\n" +
                '}';
    }
}
