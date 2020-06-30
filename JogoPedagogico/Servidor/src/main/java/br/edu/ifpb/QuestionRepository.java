package br.edu.ifpb;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import java.util.*;
import java.util.List;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;

public class QuestionRepository implements QuestionRepository_IF { // classe que representa um repositório de questões

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");

    private Map<Question, String> questions; // guarda as questões num mapa, onde as questões são chaves, e suas respostas são seu valor
    private Map<Group, List<Answer>> answers;
    private Map<Group, Integer> points;
    private Map<String, Integer> pointsPerQuestions;

    private int year; // representa o ano das questões, por exemplo, 1 ano

    public QuestionRepository(int year) {
        setYear(year);
        questions = new HashMap<>();
        answers = new HashMap<>();
        points = new HashMap<>();
        pointsPerQuestions = new HashMap<>();
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(questions.keySet());
        return Collections.unmodifiableList(questionList);
    }

    // Parâmetros
    //      Group grou = grupo
    //      Answer answer = objeto resposta que contêm uma resposta e id de uma questão
    @Override
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
                for (Question question : questions.keySet()) {
                    if (question.getId().equals(answer.getID()) && questions.get(question).equals(answer.getAnswer())) {
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

    // Parâmetros
    //      String[] difficulties = lista de dificuldades, por exemplo {"Fácil", "Média"}
    //      int amount = quantidade de questões randomizadas a serem buscadas
    public void setQuestions(String[] difficulties, int year, int amount) { // vai pegar algumas questões em um coleção em um banco de dados MongoDB
        MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
        MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões
        AggregateIterable randomQuestions = null;
        AggregateIterable randomQuestions1 = null;
        AggregateIterable randomQuestions2 = null;
        if (year == 1) {
            MongoCollection<Document> collection = dataBase.getCollection(year + " ano"); // pega a coleção de acordo com o ano estabelecido na criação do objeto
            if (difficulties.length == 2) { // ira filtrar as questões na coleção, de acordo com dificuldade, que podem ser duas, como fácil e média, e a quantidade de questões finais, que seram escolhidas aleatoriamente
                randomQuestions = collection.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(amount)));
                // match: função para combinar filtros
                // or: filtro ou
                // eq: Filtro "igual a"
            }
            else randomQuestions = collection.aggregate(Arrays.asList(match(eq("Dificuldade", difficulties[0])), sample(amount)));
            addQuestions(randomQuestions);
        }
        else if (year == 2) {
            MongoCollection<Document> collection = dataBase.getCollection("1 ano"); // pega a coleção de acordo com o ano estabelecido na criação do objeto
            MongoCollection<Document> collection1 = dataBase.getCollection(year + " ano"); // pega a coleção de acordo com o ano estabelecido na criação do objeto
            if (difficulties.length == 2) {
                randomQuestions = collection.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(2)));
                randomQuestions1 = collection1.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(3)));
                addQuestions(randomQuestions);
                addQuestions(randomQuestions1);
            }
            else {
                randomQuestions = collection.aggregate(Arrays.asList(match(eq("Dificuldade", difficulties[0])), sample(amount)));
                addQuestions(randomQuestions);
            }
        }
        else if (year == 3) {
            MongoCollection<Document> collection = dataBase.getCollection("1 ano");
            MongoCollection<Document> collection1 = dataBase.getCollection("2 ano");
            MongoCollection<Document> collection2 = dataBase.getCollection(year + " ano");
            if (difficulties.length == 2) {
                randomQuestions = collection.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(1)));
                randomQuestions1 = collection1.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(2)));
                randomQuestions2 = collection2.aggregate(Arrays.asList(match(or(eq("Dificuldade", difficulties[0]), eq("Dificuldade", difficulties[1]))), sample(2)));
                addQuestions(randomQuestions);
                addQuestions(randomQuestions1);
                addQuestions(randomQuestions2);
            }
            else {
                randomQuestions2 = collection.aggregate(Arrays.asList(match(eq("Dificuldade", difficulties[0])), sample(amount)));
                addQuestions(randomQuestions2);
            }
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
                    questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives), correctAlternative);
                }
                else questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, images), correctAlternative);
            }
            pointsPerQuestions.put(id, 3); // bônus de cada questão. Quando um grupo enviar uma resposta, se ele acerta, recebe esse bônus. Apôs uma resposta certa, o bônus é desincrementado
        }
    }

    @Override
    public String toString() {

        String questionsString = "";
        for (Question question : questions.keySet()) {
            questionsString += question + ", resposta = " + questions.get(question) + '\n';
        }

        String answerString = "";
        for (Group group : answers.keySet()) {
            answerString += group + ", Resposta = " + answers.get(group) + '\n';
        }

        String pointString = "";
        for (Group group : points.keySet()) {
            pointString += group + ", Pontos = " + points.get(group) + "\n";
        }

        return "QuestionRepository{\n" +
                "Questions:\n" + questionsString + "\n\n" +
                "Answers:\n" + answerString + "\n\n" +
                "Points:\n" + pointString + "\n\n" +
                "PointsPerQuestions:\n" + pointsPerQuestions +
                '}';
    }
}
