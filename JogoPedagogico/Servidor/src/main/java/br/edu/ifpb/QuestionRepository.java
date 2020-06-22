package br.edu.ifpb;

import static com.mongodb.client.model.Aggregates.*;
import java.util.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.*;

public class QuestionRepository implements QuestionRepository_IF {

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

    private Map<Question, String> questions;
    private Map<Group, Map<Map<Question, String>, Double>> answers;
    private int year;

    public QuestionRepository(int year) {
        setYear(year);
        questions = new HashMap<>();
        answers = new HashMap<>();
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public Map<Question, String> getQuestions() { return Collections.unmodifiableMap(questions); }

    @Override
    public void sendAnswers(Group group, Question question, String alternative, double time) {
        Map<Question, String> alternativeAndAnswers = new HashMap<>();
        alternativeAndAnswers.put(question, alternative);
        Map<Map<Question, String>, Double> answersKey = new HashMap<>();
        answersKey.put(alternativeAndAnswers, time);
        if (! answers.isEmpty()) {
            if (answers.containsKey(group)) {
                for (Map<Question, String> i : answers.get(group).keySet()) {
                    answersKey.put(i, answers.get(group).get(i));
                }
                answers.put(group, answersKey);
            }
            else answers.put(group, answersKey);
        }
        else answers.put(group, answersKey);
    }

    public void setQuestions(String tipo, int qtde) {
        MongoClient client = new MongoClient(uri);
        MongoDatabase dataBase = client.getDatabase("Questões");
        MongoCollection<Document> collection = dataBase.getCollection(year + " ano");
        AggregateIterable doc = collection.aggregate(Arrays.asList(Filters.eq("Dificulade", tipo)));
        AggregateIterable randomQuestions = collection.aggregate(Arrays.asList(match(Filters.eq("Dificuldade", tipo)), sample(qtde)));
        Iterator iterador = randomQuestions.iterator();
        while (iterador.hasNext()) {
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
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives), correctAlternative);
                }
                else questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, images), correctAlternative);
            }
        }
    }
}
