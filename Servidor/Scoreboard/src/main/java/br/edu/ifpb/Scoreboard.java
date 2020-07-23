package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;

public class Scoreboard implements Scoreboard_IF {

    private static MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/?retryWrites=true&w=majority");

    @Override
    public Map<Integer, List<String>> getScoreboard(int year) {
        Map<Integer, List<String>> scoreboard = new TreeMap<>();
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("Grupos");
        MongoCollection<Document> collection = database.getCollection(String.format("%d ano", year));
        FindIterable<Document> documentAggregateIterable = collection.find();
        Iterator iterator = documentAggregateIterable.iterator();
        iterator.forEachRemaining(doc -> {
            Document document = (Document) doc;
            String groupName = (String) document.get("nome");
            int points = (int) document.get("pontos");
            if (! scoreboard.containsKey(points)) scoreboard.put(points, Collections.singletonList(groupName));
            else {
                List<String> aux = new ArrayList<>();
                aux.addAll(scoreboard.get(points));
                aux.add(groupName);
                scoreboard.put(points, aux);
            }
        });
        System.out.println(scoreboard);
        return scoreboard;
    }
}
