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
        for (int i = 0; i < out.length; i ++) {
            if (out[i].contains("Questão") && i != 0) {
                questionsMatriz.add(aux);
                aux = new ArrayList<>();
            }
            if (out[i].length() > 2) aux.add(out[i]);
        }
        questionsMatriz.remove(0);
        System.out.println(questionsMatriz);
        Scanner input = new Scanner(System.in);
        int numFoto = 1;
        System.out.println("Tipos de questões: \n");
        System.out.println("\t[1] - Questão normal\n" +
                "\t[2] - Questão com imagem no texto\n" +
                "\t[3] - Questão com imagem no texto e nas alternativas\n" +
                "\t[4] - Questão com imagem na alternativa\n\n");
        System.out.print("Digite a sequência: ");
        String num = input.next();
        for (int i = 0; i < questionsMatriz.size(); i ++) {
            List<String> texto = new ArrayList<>();
            List<String> alternativas = new ArrayList<>();
            List<String> imagensTexto = new ArrayList<>();
            List<String> imagensAlternaivas = new ArrayList<>();
            Question q = null;
            String alternativaCorreta = "";
            for (String s : questionsMatriz.get(i)) {
                if ((! s.contains("Questão")) && (!(s.charAt(1) == ')')) && (! s.contains("Gab"))) texto.add(s);
            }
            if (num.charAt(i) == '1') {
                for (String s : questionsMatriz.get(i)) {
                    if ((!s.contains("Questão")) && (!s.contains("Gab")) && (s.charAt(1) == ')'))
                        alternativas.add(s.substring(3));
                    else if (s.contains("Gab")) alternativaCorreta = s.substring(5);
                }
                q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas);
            }
            if (num.charAt(i) == '2') {
                for (String s : questionsMatriz.get(i)) {
                    if ((!s.contains("Questão")) && (!s.contains("Gab")) && (s.charAt(1) == ')'))
                        alternativas.add(s.substring(3));
                    else if (s.contains("Gab")) alternativaCorreta = s.substring(5);
                }
                System.out.print("Digite a quantidade de fotos presentes no texto: ");
                int qtdeFotos = input.nextInt();
                List<String> nomesFotos = new ArrayList<>();
                for (int j = 0; j < qtdeFotos; j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                }
                imagensTexto.addAll(imagemToBase64(nomesFotos));
                q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas, imagensTexto);
            }
            if (num.charAt(i) == '3') {
                System.out.print("Digite a quantidade de fotos presentes no texto: ");
                int qtdeFotos = input.nextInt();
                List<String> nomesFotos = new ArrayList<>();
                for (int j = 0; j < qtdeFotos; j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                }
                imagensTexto.addAll(imagemToBase64(nomesFotos));
                System.out.print("Digite a quantidade de fotos presentes nas alternativas: ");
                qtdeFotos = input.nextInt();
                nomesFotos = new ArrayList<>();
                for (int j = 0; j < qtdeFotos; j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                }
                imagensAlternaivas.addAll(imagemToBase64(nomesFotos));
                q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas, imagensTexto);
            }
            if (num.charAt(i) == '4') {
                List<String> nomesFotos = new ArrayList<>();
                imagensTexto.addAll(imagemToBase64(nomesFotos));
                System.out.print("Digite a quantidade de fotos presentes nas alternativas: ");
                int qtdeFotos = input.nextInt();
                for (int j = 0; j < qtdeFotos; j ++, numFoto ++) {
                    nomesFotos.add(String.format("media/image%d.png", numFoto));
                }
                imagensAlternaivas.addAll(imagemToBase64(nomesFotos));
                q = new QuestionWithNormalAlternatives(texto, alternativaCorreta, alternativas, imagensTexto);
            }
            questions.add(q);
        }
        System.out.println(questionsMatriz);
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
