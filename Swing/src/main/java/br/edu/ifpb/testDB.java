package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class testDB {
    public static void main(String[] args) {
        // MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase dataBase = mongoClient.getDatabase("test");
        MongoCollection<Document> documentMongoCollection = dataBase.getCollection("users");
        Document document = new Document("title", "MondoBD1")
                .append("description", "text");
        documentMongoCollection.insertOne(document);
        for (String i : dataBase.listCollectionNames()) System.out.println(i);
    }
}
