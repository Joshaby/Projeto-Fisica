package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

public class QuestionRepository { // classe que representa um repositório de questões

// DEFAULT STATIC VALUES
    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");

// DEFAULT VARIABLES
    private Map<Question, String> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor
    private Map<String, Integer> points; // guarda os pontos recebidos por questão, onde o ID das questoes são as chaves, e sua pontuação é o valor
    private int year; // representa o ano das questões, por exemplo, 1 ano

//CONSTRUCTORS
    public QuestionRepository() {
        questions = new HashMap<>();
        points = new HashMap<>();
        setYear(-1);
    }

//METHOD THATS REACH MONGODB ON CLOUD AND RETURN THE NUMBER OF QUESTIONS EXPECTED

    public void setQuestions(int round, int amount, int year) { // método para setar as questões para uso do grupo
        try {
            this.setYear(year);
            MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
            MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões
            Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
            mongoLogger.setLevel(Level.SEVERE);
            List<AggregateIterable> randomizedQuestions = new ArrayList<>();
            int stockAmount = amount;
            for (int i = 1; i <= year; i++) {
                int localAmount = (int) Math.ceil(stockAmount / (double) year);
                amount -= localAmount;
                if (localAmount > amount + localAmount) localAmount += amount;
                MongoCollection<Document> collection = dataBase.getCollection(i + " ano");
                randomizedQuestions.add(collection.aggregate(Arrays.asList(
                        match(or(eq("Dificuldade", (round >= 1 && round <= 2) ? "Fácil" : ""),
                                eq("Dificuldade", (round >= 2 && round <= 4) ? "Média" : ""),
                                eq("Dificuldade", (round >= 4) ? "Difícil" : ""))),
                        sample(localAmount))));
            }
            randomizedQuestions.forEach(this::addQuestions);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addQuestions(AggregateIterable randomQuestions) {
        Iterator iterador = randomQuestions.iterator(); // pega o iterador das questões randomizadas
        while (iterador.hasNext()) { // aqui em diante, será pego os dados dos documentos em randomQuestions, será feito castings e uma verificação para definir o tipo de questão a partir de seus dados
            Document document = (Document) iterador.next();
            String id = (String) document.get("ID");
            String difficulty = (String) document.get("Dificuldade");
            String text = (String) document.get("Texto");
            String correctAlternative = (String) document.get("Alternativa correta");
            List<String> alternatives = (List<String>) document.get("Alternativas");
            List<String> images = (List<String>) document.get("Imagens");
            if (alternatives == null) {
                if (images == null) {
                    questions.put(new Question(id, difficulty, text), correctAlternative);
                }
                else questions.put(new Question(id, difficulty, text, images), correctAlternative);
            }
            else {
                if (images == null) {
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, false), correctAlternative);
                }
                else
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, images, alternatives, true), correctAlternative);
            }
            points.put(id, 5);
        }
    }

//SEND QUESTIONS METHODS

    public List<Question> getQuestions() { // pega as questões para uso do grupo
        return Collections.unmodifiableList(List.copyOf(questions.keySet()));
    }

    public List<String> getQuestionsID() { // pega o ID das questões para definir a pontuação extra de cada função
        List<String> aux = new ArrayList<>();
        questions.keySet().forEach(question -> aux.add(question.getId()));
        return aux;
    }

    private Question getQuestionById(String QuestionID){
        AtomicReference<Question> aux = new AtomicReference<Question>();
        questions.keySet().iterator().forEachRemaining(question -> {if(question.getId().equals(QuestionID)) aux.set(question);});
        return (aux == null)? null : aux.get();
    }

    public Map<Question, String> getQuestionsMap() { // retorna o mapa questions
        return this.questions;
    }

// POINTS MANAGEMENT METHODS
    public void decreasePoint(String QuestionID){
        this.points.replace(QuestionID, this.points.get(QuestionID) - 1);
    }

    public Integer getPoints(String QuestionID){
        return this.points.get(QuestionID);
    }

// AnswerAnaliser
    public boolean validateAnswer(String QuestionID, String res){
        return this.questions.get(getQuestionById(QuestionID)).equals(res);
    }

// RESET QUESTIONS
    public void resetQuestions(int round, int amount, int year){
        this.questions = new HashMap<>();
        this.points = new HashMap<>();
        this.setQuestions(round, amount, year);
    }

// GETTERS & SETTERS
    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }

//TO STRING
    @Override
    public String toString() {

        String questionsString = "";
        for (Question question : questions.keySet()) {
            questionsString += question + ", resposta = " + questions.get(question) + '\n';
        }
        return "QuestionRepository{\n" +
                "Questions:\n" + questionsString + "\n\n" +
                '}';
    }
}
