package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import java.util.Arrays;
import java.util.Iterator;

public class testDataBase {
    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        MongoClient cliente = new MongoClient(uri);
        MongoDatabase dataBase = cliente.getDatabase("Questões");
        MongoCollection<Document> collection = dataBase.getCollection("1 ano");
        AggregateIterable doc = collection.aggregate(Arrays.asList(Aggregates.sample(1)));
        FindIterable<Document> documents = collection.find();
        Iterator filtredDocuments = doc.iterator();
        while (filtredDocuments.hasNext()) {
            Document document = (Document) filtredDocuments.next();
            System.out.println(document.get("ID"));
        }
    }
}
