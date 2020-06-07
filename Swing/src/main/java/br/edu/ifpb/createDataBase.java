package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class createDataBase {

    private static Path path = Path.of("Questions");

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> nomes = getAlternativas(path);
        Map<String, List<String>> images = getImagensInBase64(path);
        Map<String, String> texto = getHtml(path);

        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase database = client.getDatabase("Questões");
        MongoCollection<Document> collection = database.getCollection("1 ano");

        for(String s : nomes.keySet()) {
            System.out.println("Questão " + s);
            System.out.println(nomes.get(s));
            System.out.println(images.get(s));
            System.out.println(texto.get(s));
            System.out.println();
//            Document document = new Document()
//                    .append("ID", s)
//                    .append("Dificuldade", "Fácil")
//                    .append("Texto", texto.get(s))
//                    .append("Alternativas", nomes.get(s))
//                    .append("Imagens", images.get(s));
//            collection.insertOne(document);
        }
    }

    public static Map<String, List<String>> getAlternativas(Path path) {
        HashSet<String> nomes = new HashSet<>();
        Map<String, List<String>> alternativasArray = new HashMap<>();
        try {
            DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
            for (Path p : arquivos) {
                if (Files.isDirectory(p)) alternativasArray.putAll(getAlternativas(p));
                else if (p.getFileName().toString().contains("HTM") && (! p.getFileName().toString().contains("G"))) {
                    List<String> html = Files.readAllLines(p, StandardCharsets.ISO_8859_1);
                    List<String> alternativas = new ArrayList<>();
                    for (int i = 0; i < html.size(); i ++) {
                        StringBuilder alternativa = new StringBuilder();
                        if (html.get(i).contains(">a)") || html.get(i).contains(">b)") || html.get(i).contains(">c)") || html.get(i).contains(">d)") || html.get(i).contains(">e)")) {
                            if (html.get(i).contains("<p")) {
                                for (int j = i; j < html.size(); j ++) {
                                    alternativa.append(html.get(j));
                                    if (html.get(j).contains("</p>")) {
                                        alternativas.add(alternativa.toString());
                                        break;
                                    }
                                }
                            }
                            else {
                                for (int j = i - 1; j < html.size(); j ++) {
                                    alternativa.append(html.get(j));
                                    if (html.get(j).contains("</p>")) {
                                        alternativas.add(alternativa.toString());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (alternativas.size() > 0) {
                        if (p.toString().contains("HTM")) {
                            String[] partes = p.toString().split("/");
                            StringBuilder id = new StringBuilder();
                            for (int i = 1; i < partes[1].length(); i ++) {
                                if (partes[1].charAt(i) == '.') break;
                                else id.append(partes[1].charAt(i));
                            }
                            alternativasArray.put(id.toString(), alternativas);
                        }
                    }
                    alternativas = new ArrayList<>();
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return alternativasArray;
    }

    public static Map<String, List<String>> getImagensInBase64(Path path) throws IOException {
        Map<String, List<String>> imagens = new HashMap<>();
        DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
        List<String> base64 = new ArrayList<>();
        for (Path p : arquivos) {
            if (Files.isDirectory(p) && (! p.getFileName().toString().contains("G"))) imagens.putAll(getImagensInBase64(p));
            else if (p.getFileName().toString().contains("gif") || p.getFileName().toString().contains("jpg")) {
                BufferedImage bufferedImage = ImageIO.read(new File(p.toString()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, p.getFileName().toString().substring(p.getFileName().toString().length() - 3), byteArrayOutputStream);
                byte[] dataBytes = byteArrayOutputStream.toByteArray();
                String encoded = Base64.getEncoder().encodeToString(dataBytes);
                base64.add(p.getFileName().toString().substring(p.getFileName().toString().length() - 3) + encoded);
            }
        }
        if (base64.size() > 0) {
            if (path.toString().contains("arquivos")) {
                String[] partes = path.toString().split("/");
                StringBuilder id = new StringBuilder();
                for (int i = 1; i < partes[1].length(); i ++) {
                    if (partes[1].charAt(i) == '_') break;
                    else id.append(partes[1].charAt(i));
                }
                imagens.put(id.toString(), base64);
            }
        }
        return imagens;
    }

    public static Map<String, String> getHtml(Path path) throws IOException {
        Map<String, String> htmls = new HashMap<>();
        DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
        for (Path p : arquivos) {
            if ((! Files.isDirectory(p)) && p.getFileName().toString().contains("Q") && p.getFileName().toString().contains("HTM")) {
                StringBuilder nome = new StringBuilder();
                String[] partes = p.toString().split("/");
                for (int i = 1; i < partes[1].length(); i ++) {
                    if (partes[1].charAt(i) == '.') break;
                    else nome.append(partes[1].charAt(i));
                }
                List<String> allLinesHTML = Files.readAllLines(p, StandardCharsets.ISO_8859_1);
                StringBuilder linesWithoutAlternatives = new StringBuilder();
                for (int i = 0; i < allLinesHTML.size(); i ++) {
                    if (! checkLine1(allLinesHTML, i)) linesWithoutAlternatives.append(allLinesHTML.get(i));
                }
                htmls.put(nome.toString(), linesWithoutAlternatives.toString());
            }
        }
        return htmls;
    }

    public static List<String> getQuestoes(Path path) throws IOException {
        List<String> nomes = new ArrayList<>();
        DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
        for (Path p : arquivos) {
            if (Files.isDirectory(p)) nomes.addAll(getQuestoes(p));
            else if (p.getFileName().toString().contains("HTM") && (!p.getFileName().toString().contains("G"))) {
                nomes.add(p.getFileName().toString());
            }
        }
        return nomes;
    }

    public static List<Integer> checkLines(List<String> html) {
        List<Integer> locates = new ArrayList<>();
        for (int i = 0; i < html.size(); i ++) {
            if (html.get(i).contains(">a)") || html.get(i).contains(">b)") || html.get(i).contains(">c)") || html.get(i).contains(">d)") || html.get(i).contains(">e)")) {
                if (html.get(i).contains("<p")) {
                    for (int j = i; j < html.size(); j ++) {
                        locates.add(j);
                        if (html.get(j).contains("</p>")) {
                            break;
                        }
                    }
                }
                else {
                    for (int j = i - 1; j < html.size(); j ++) {
                        locates.add(j);
                        if (html.get(j).contains("</p>")) {
                            break;
                        }
                    }
                }
            }
        }
        return locates;
    }

    public static boolean checkLine1(List<String> allLinesHTML, int line) {
        List<Integer> locate = checkLines(allLinesHTML);
        for (int j = 0; j < locate.size(); j ++) {
            if (line == locate.get(j)) return true;
        }
        return false;
    }
}