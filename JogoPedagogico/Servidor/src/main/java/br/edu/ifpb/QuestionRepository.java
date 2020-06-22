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

public class QuestionRepository implements QuestionRepository_IF { // classe que representa um repositório de questões

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

    private Map<Question, String> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor
    private Map<Group, Map<Map<Question, String>, Double>> answers; // guarda as repostas dos grupos, com cada questão com sua respotas, e tempo levado para responder a questão
    private int year; // representa o ano das questões, por exemplo, 1 ano

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
    public void sendAnswers(Group group, Question question, String alternative, double time) { // vai receber uma respotar de um grupo, com uma referência ao grupo, questão, uma alternativa, e o tempo de resposta
        Map<Question, String> alternativeAndAnswers = new HashMap<>(); // cria um mapa com Questão como chave e sua resposta como valor
        alternativeAndAnswers.put(question, alternative); // coloca a Questão e sua resposta
        Map<Map<Question, String>, Double> answersKey = new HashMap<>(); // cria um mapa com o mapa anterior(Questão e sua resposta) como chave e coloca o tempo como valor
        answersKey.put(alternativeAndAnswers, time); // coloca o mapa anterior e o tempo de resposta
        if (! answers.isEmpty()) {                                              // a partir daqui, será realizado uma busca no mapa answers para verificar se contêm o grupo que enviou a resposta
            if (answers.containsKey(group)) {                                   // se o grupo estiver presente, será pegado os dados dele, questões, respotas e tempo e seram colocados num mapa local, isso pra poder pegar os dados enviado pelo grupo
                for (Map<Question, String> i : answers.get(group).keySet()) {   // se não contiver o grupo ou o mapa naõ tiver nada, ele será adicionado sem complicações
                    answersKey.put(i, answers.get(group).get(i));
                }
                answers.put(group, answersKey);
            }
            else answers.put(group, answersKey);
        }
        else answers.put(group, answersKey);
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
        }
    }
}
