package br.edu.ifpb;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bson.Document;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class createDataBase {
    public static void main(String[] args) throws IOException {
//        1211121222121121222111111131122112111113133221321111211112111111121112111
//        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?retryWrites=true&w=majority");
//        MongoClient client = new MongoClient(uri);
//        MongoDatabase database = client.getDatabase("Questões");
//        MongoCollection<Document> collection = database.getCollection("1 ano");
        // collection.insertMany();
        getQuestions();
    }

    private static List<Document> createDocuments() {
        List<Document> documents = new ArrayList<>();

        return documents;
    }

    private static void getQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        XWPFDocument docx = new XWPFDocument(new FileInputStream("P002044.docx"));
        XWPFWordExtractor text = new XWPFWordExtractor(docx);
        String[] out = text.getText().split("\n");
        List<String> aux = new ArrayList<>();
        List<List<String>> questionsMatriz = new ArrayList<>();
        List<Integer> imagensTextoQtde = new ArrayList<>();
        List<Integer> imagensAlternativasQtde = new ArrayList<>();
        for (int i = 0; i < out.length; i ++) {
            if (out[i].contains("Questão") && i != 0) {
                questionsMatriz.add(aux);
                aux = new ArrayList<>();
            }
            if (out[i].length() > 2) aux.add(out[i]);
            if (out[i].contains("Imagens texto:")) imagensTextoQtde.add(Integer.parseInt(out[i].substring(15)));
            if (out[i].contains("Imagens alternativas:")) imagensAlternativasQtde.add(Integer.parseInt(out[i].substring(22)));
        }
        questionsMatriz.remove(0);
        System.out.println(questionsMatriz.size());
        Scanner input = new Scanner(System.in);
        int numFoto = 1;
        for (int i = 0; i < questionsMatriz.size(); i ++) {
            List<String> texto = new ArrayList<>();
            List<String> alternativas = new ArrayList<>();
            List<String> imagensTexto = new ArrayList<>();
            List<String> imagensAlternaivas = new ArrayList<>();
            Question q = null;
            String alternativaCorreta = "";
            for (String s : questionsMatriz.get(i)) {
                if ((! s.contains("Questão")) && (!(s.charAt(1) == ')')) && (! s.contains("Gab:"))) texto.add(s);
                if ((! s.contains("Questão")) && (!(s.charAt(1) == ')')) && (s.contains("Gab:"))) alternativaCorreta = s.substring(5);
            }
            if (imagensTextoQtde.get(i) > 0) {
                List<String> nomesFotos = new ArrayList<>();
                for (int j = 0; j < imagensTextoQtde.get(i); j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                    System.out.println(String.format("media/image%d.png", numFoto));
                }
                imagensTexto.addAll(imagemToBase64(nomesFotos));
            }
            if (imagensAlternativasQtde.get(i) > 0) {
                List<String> nomesFotos = new ArrayList<>();
                for (int j = 0; j < imagensAlternativasQtde.get(i); j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                    System.out.println(String.format("media/image%d.png", numFoto));
                }
                imagensAlternaivas.addAll(imagemToBase64(nomesFotos));
            }
            else {
                for (String s : questionsMatriz.get(i)) {
                    if ((!s.contains("Questão")) && (!s.contains("Gab:")) && (s.charAt(1) == ')'))
                        alternativas.add(s.substring(3));
                }
            }
            if (imagensTextoQtde.get(i) == 0) {
                if (imagensAlternativasQtde.get(i) == 0) {
                    q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas);
                }
                else q = new QuestionWithImagesAlternatives(texto, alternativaCorreta, imagensAlternaivas);
            }
            else {
                if (imagensAlternativasQtde.get(i) == 0) {
                    q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas, imagensTexto);
                }
                else q = new QuestionWithImagesAlternatives(texto, alternativaCorreta, imagensTexto, imagensAlternaivas);
            }
            questions.add(q);
        }
        System.out.println(questions);
    }

    private static List<String> imagemToBase64(List<String> nomesFotos) throws IOException {
        List<String> imagensBase64 = new ArrayList<>();
        for (String i : nomesFotos) {
            BufferedImage bufferedImage = ImageIO.read(new File(i));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArray);
            byte[] data = byteArray.toByteArray();
            String encoded = Base64.getEncoder().encodeToString(data);
            imagensBase64.add(encoded);
        }
        return imagensBase64;
    }
}
