package br.edu.ifpb;

import static com.mongodb.client.model.Aggregates.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.*;

public class QuestionRepository implements QuestionRepository_IF { // classe que representa um repositório de questões

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

    private Map<Question, String> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor
    private Map<Group, List<Response>> answers;
    private Map<Group, Integer> points;
    private Map<String, Integer> pointsPerQuestions;

    private int year; // representa o ano das questões, por exemplo, 1 ano

    public QuestionRepository(int year) {
        setYear(year);
        questions = new HashMap<>();
        answers = new HashMap<>();
        points = new HashMap<>();
        pointsPerQuestions = new HashMap<>();
        pointsPerQuestions.put("12", 3);
        pointsPerQuestions.put("13", 3);
        pointsPerQuestions.put("14", 3);
        questions.put(new Question("12", "média", "oi"), "A");
        questions.put(new Question("13", "média", "oi1"), "B");
        questions.put(new Question("14", "média", "oi2"), "C");
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public Map<Question, String> getQuestions() { return Collections.unmodifiableMap(questions); }

    @Override
    public void sendAnswer(Group group, Response response) {
        if (! answers.isEmpty()) {
            if (answers.containsKey(group)) {
                List<Response> aux = new ArrayList<>();
                aux.add(response);
                aux.addAll(answers.get(group));
                answers.put(group, aux);
            }
            else answers.put(group, Collections.singletonList(response));
        }
        else answers.put(group, Collections.singletonList(response));
        calculatePoints(group, response);
        System.out.println(answers);
        System.out.println(points);
        System.out.println();
    }

    public void calculatePoints(Group group, Response response) {
        if (! points.isEmpty()) {
            if (points.containsKey(group)) {
                for (Question question : questions.keySet()) {
                    if (question.getId().equals(response.getID()) && questions.get(question).equals(response.getAnswer())) {
                        points.put(group, points.get(group) + 1 + pointsPerQuestions.get(response.getID()));
                        if (pointsPerQuestions.get(response.getID()) != 0)
                            pointsPerQuestions.put(response.getID(), pointsPerQuestions.get(response.getID()) - 1);
                        break;
                    }
                }
            }
            else {
                points.put(group, 1 + pointsPerQuestions.get(response.getID()));
                if (pointsPerQuestions.get(response.getID()) != 0)
                    pointsPerQuestions.put(response.getID(), pointsPerQuestions.get(response.getID()) - 1);
            }
        }
        else {
            points.put(group, 1 + pointsPerQuestions.get(response.getID()));
            if (pointsPerQuestions.get(response.getID()) != 0)
                pointsPerQuestions.put(response.getID(), pointsPerQuestions.get(response.getID()) - 1);
        }
    }

    public void setQuestions(String type, int qtde) { // vai pegar algumas questões em um coleção em um banco de dados MongoDB
        MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
        MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões
        MongoCollection<Document> collection = dataBase.getCollection(year + " ano"); // pega a coleção de acordo com o ano estabelecido na criação do objeto
        AggregateIterable randomQuestions = collection.aggregate(Arrays.asList(match(Filters.eq("Dificuldade", type)), sample(qtde))); // esse método ira filtrar as questões por dificuldade, coma variável type, e depois pegará um número aléatorio de questões, número e dado por qtde
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
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives), correctAlternative);
                }
                else questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, images), correctAlternative);
            }
            pointsPerQuestions.put(id, 3); // bônus de cada questão. Quando um grupo enviar uma resposta, se ele acerta, recebe esse bônus. Apôs uma resposta certa, o bônus é desincrementado
        }
    }
}
