package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testDB {
    public static void main(String[] args) throws IOException {
        // MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase dataBase = mongoClient.getDatabase("test");
        MongoCollection<Document> documentMongoCollection = dataBase.getCollection("questions");
//        Document document = new Document("title", "MondoBD1")
//                .append("description", "text");
        for (String i : dataBase.listCollectionNames()) System.out.println(i);
        XWPFDocument docx = new XWPFDocument(new FileInputStream("P002044.docx"));
        XWPFWordExtractor we = new XWPFWordExtractor(docx);
        // System.out.println(we.getText());
        String[] out = we.getText().split("\n");
        List<String> aux = new ArrayList<>();
        for (String i : out) {
            aux.add(i);
        }
        // System.out.println(aux);
        documentMongoCollection.insertMany(stringToMongoDB(aux));
    }

    private static List<Document> stringToMongoDB(List<String> questoes) {
        List<List<String>> questoesMatriz = new ArrayList<>();
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < questoes.size(); i ++) {
            if (questoes.get(i).contains("Questão") && i != 0) {
                questoesMatriz.add(aux);                         // transforma a lista de string do docx, em uma matriz, uma lista de questões, onde cada questão é uma lista
                aux = new ArrayList<>();
            }
            if (questoes.get(i).length() > 2) aux.add(questoes.get(i));
        }

//        for (List<String> I : questoesMatriz) {
//            for (String i : I) System.out.println(i);
//            System.out.println();
//        }
        List<Question> questionList = new ArrayList<>(); // vai percorrendo a matriz, e passando as strings das sublistas para um objeto questão
        for (List<String> questao : questoesMatriz) {
            List<String> texto = new ArrayList<>();
            String cab = questao.get(questao.size() - 1);
            String nome = questao.get(0);
            List<String> alternativas = new ArrayList<>();
            for (int i = 1; i < questao.size(); i ++) {
                if (questao.get(i).charAt(1) == ')') alternativas.add(questao.get(i));
                else texto.add(questao.get(i));
            }
            questionList.add(new Question(nome, texto, alternativas));
        }
        List<Document> documents = new ArrayList<>();
        for (Question q : questionList) {
            Document document = new Document().append("Número", q.getId())
                    .append("Texto", q.getText())
                    .append("Alternativas", q.getAlternatives());
            documents.add(document);
        }
        return documents;
    }
}
