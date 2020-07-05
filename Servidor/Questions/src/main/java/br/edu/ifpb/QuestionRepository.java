package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import java.util.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

public class QuestionRepository implements QuestionRepository_IF { // classe que representa um repositório de questões

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");
    private Map<Question, String> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor
    private int year; // representa o ano das questões, por exemplo, 1 ano

    public QuestionRepository(int year) {
        setYear(year);
        questions = new HashMap<>();
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public List<Question> getQuestions() { // pega as questões para uso do grupo
        List<Question> questionList = new ArrayList<>();
        questions.keySet().forEach(questionList::add);
        return Collections.unmodifiableList(questionList);
    }
    public List<String> getQuestionsID() { // pega o ID das questões para definir a pontuação extra de cada função
        List<String> aux = new ArrayList<>();
        questions.keySet().forEach(question -> aux.add(question.getId()));
        return aux;
    }
    public Map<Question, String> getQuestionsMap() { // retorna o mapa questions
        return this.questions;
    }

    // Parâmetros
    //      String[] difficulties = lista de dificuldades, por exemplo {"Fácil", "Média"}
    //      int amount = quantidade de questões randomizadas a serem buscadas

    public void setQuestions(String[] difficulties, int amount) { // método para setar as questões para uso do grupo
        try {
            MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
            MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões
            List<AggregateIterable> randomizedQuestions = new ArrayList<>();

            for (int i = 1; i <= year; i++) {
                MongoCollection<Document> collection = dataBase.getCollection(i + " ano");
                randomizedQuestions.add(collection.aggregate(Arrays.asList(
                        match(or(eq("Dificuldade", difficulties[0]),
                                eq("Dificuldade", difficulties[1]),
                                eq("Dificuldade", difficulties[2]))),
                        sample(amount / year))));
            }
            for (AggregateIterable randomizedQuestion : randomizedQuestions) {
                addQuestions(randomizedQuestion);
            }
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
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, images, true), correctAlternative);
            }
            System.out.println(id);
        }
    }

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
