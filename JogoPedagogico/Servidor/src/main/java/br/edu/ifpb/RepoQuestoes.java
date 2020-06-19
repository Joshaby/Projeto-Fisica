package br.edu.ifpb;

import java.util.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import static com.mongodb.client.model.Aggregates.*;

public class RepoQuestoes implements RepoQuestoes_IF {

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");

    private Map<Questao, String> questoes = new HashMap<>();
    private Map<Grupo, Map<String, String>> repostas;
    private int ano = 2;

    @Override
    public Map<Questao, String> getQuestoes() { return Collections.unmodifiableMap(questoes); }

    @Override
    public void enviarRespota(String alternativa, String ID) {

    }

    public void setQuestoes(String tipo, int qtde) {
        MongoClient cliente = new MongoClient(uri);
        MongoDatabase bancoDeDados = cliente.getDatabase("Questões");
        MongoCollection<Document> colecao = bancoDeDados.getCollection(ano + " ano");
        FindIterable<Document> questoesIterable = colecao.find(new Document().append("Dificuldade", tipo));
        AggregateIterable doc = colecao.aggregate(Arrays.asList(Filters.eq("Dificulade", tipo)));
        AggregateIterable randomQuestions = colecao.aggregate(Arrays.asList(match(Filters.eq("Dificuldade", tipo)), sample(5)));
        Iterator iterador = randomQuestions.iterator();
        while (iterador.hasNext()) {
            Document document = (Document) iterador.next();
            System.out.println(document.get("ID"));
        }
    }
}
