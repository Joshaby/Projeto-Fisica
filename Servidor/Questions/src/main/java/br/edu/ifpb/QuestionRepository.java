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
    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(questions.keySet());
        return Collections.unmodifiableList(questionList);
    }

    public List<String> getQuestionsID(){
        List<String> aux = new ArrayList<>();
        for (Question question : questions.keySet()) { aux.add(question.getId()); }
        return aux;
    }

    public Map<Question, String> getQuestionsMap(){ return this.questions; }

    // Parâmetros
    //      String[] difficulties = lista de dificuldades, por exemplo {"Fácil", "Média"}
    //      int amount = quantidade de questões randomizadas a serem buscadas

    public boolean setQuestions(String[] difficulties, int year, int amount){

        while (difficulties.length < 3) difficulties[difficulties.length-1] = "";

        if(year < 1 || year > 3 || amount <= 0 || difficulties.length != 3){return false;}
        try{
            MongoClient client = new MongoClient(uri); // estabelece a conexão com o cluster com MongoDB
            MongoDatabase dataBase = client.getDatabase("Questões"); // pega o banco de dados Questões
            ArrayList<AggregateIterable> randomizedQuestions = null;

            for(int i = 1; i <= year; i++){
                MongoCollection<Document> collection = dataBase.getCollection(i + " ano");
                randomizedQuestions.add(collection.aggregate(Arrays.asList(
                        match(or(eq("Dificuldade", difficulties[0]),
                                eq("Dificuldade", difficulties[1]),
                                eq("Dificuldade", difficulties[2]))),
                        sample(amount))));
            }
            for (AggregateIterable randomizedQuestion : randomizedQuestions) { addQuestions(randomizedQuestion); }
            return true;
        }catch (Throwable err){
            System.out.println("Erro ao inserir questões:\n      err");
            return false;
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
                if (images == null) { questions.put(new Question(id, difficulty, text), correctAlternative); }
                else questions.put(new Question(id, difficulty, text, images), correctAlternative);
            }
            else {
                if (images == null) { questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives), correctAlternative); }
                else questions.put(new MultipleChoiceQuestion(id, difficulty, text, alternatives, images), correctAlternative);
            }
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
