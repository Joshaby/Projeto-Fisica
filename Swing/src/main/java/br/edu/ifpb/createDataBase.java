package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jsoup.Jsoup;
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

public class createDataBase { // classe para criar um coleção de questões em um banco de dados mongoDB. São usadas as questões filtradas pela classe filterQuestion

    private static Path path = Path.of("Questões");

    public static void main(String[] args) throws IOException {
        //Map<String, List<String>> nomes = getAlternativas(path);
        Map<String, List<String>> images = getImagensInBase64(path);
        Map<String, String> texto = getHtml(path);
        Map<String, String> alternativasCorretas = getAlternativaCorreta(path);
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase database = client.getDatabase("Questões");
        MongoCollection<Document> collection = database.getCollection("1 ano");
        int i = 0;
        for(String s : texto.keySet()) {
            //if (alternativasCorretas.get(s) != null && alternativasCorretas.get(s).length() == 1) {
                System.out.println("Questão " + s);
                //System.out.println(nomes.get(s));
                System.out.println(images.get(s));
                System.out.println(texto.get(s));
                System.out.println(alternativasCorretas.get(s));
                System.out.println();
                Document document = new Document()
                        .append("ID", s)
                        .append("Dificuldade", "Difícil")
                        .append("Texto", texto.get(s))
                        //.append("Alternativas", nomes.get(s))
                        .append("Alternativa correta", alternativasCorretas.get(s))
                        .append("Imagens", images.get(s));
                collection.insertOne(document);
                i++;
            //}
        }
        System.out.println(i);
    }

    public static Map<String, List<String>> getAlternativas(Path path) { // pega as alternativas do html
        HashSet<String> nomes = new HashSet<>();
        Map<String, List<String>> alternativasArray = new HashMap<>();
        try {
            DirectoryStream<Path> arquivos = Files.newDirectoryStream(path); // No fim das contas, pode ser considerado com uma lista de path, que podem representar, arquivos ou diretórios
            for (Path p : arquivos) {
                if (Files.isDirectory(p)) alternativasArray.putAll(getAlternativas(p));
                else if (p.getFileName().toString().contains("HTM") && (! p.getFileName().toString().contains("G"))) {
                    List<String> html = Files.readAllLines(p, StandardCharsets.ISO_8859_1); // ler todas as linhas do html e passa para um List<String>
                    List<String> alternativas = new ArrayList<>();
                    for (int i = 0; i < html.size(); i ++) { // vai procurar as alternativas, que estam dentros de um parágrafo
                        StringBuilder alternativa = new StringBuilder();
                        if (html.get(i).contains(">a)") || html.get(i).contains(">b)") || html.get(i).contains(">c)") || html.get(i).contains(">d)") || html.get(i).contains(">e)")) {
                            if (html.get(i).contains("<p")) {
                                for (int j = i; j < html.size(); j ++) {
                                    alternativa.append(html.get(j));
                                    if (html.get(j).contains("</p>")) {
                                        String alternativaString = alternativa.toString().replace("a)", "");
                                        alternativaString = alternativaString.replace("b)", "");
                                        alternativaString = alternativaString.replace("c)", "");
                                        alternativaString = alternativaString.replace("d)", "");
                                        alternativaString = alternativaString.replace("e)", "");
                                        alternativas.add(alternativaString);
                                        break;
                                    }
                                }
                            }
                            else {
                                for (int j = i - 1; j < html.size(); j ++) {
                                    alternativa.append(html.get(j));
                                    if (html.get(j).contains("</p>")) {
                                        String alternativaString = alternativa.toString().replace("a)", "");
                                        alternativaString = alternativaString.replace("b)", "");
                                        alternativaString = alternativaString.replace("c)", "");
                                        alternativaString = alternativaString.replace("d)", "");
                                        alternativaString = alternativaString.replace("e)", "");
                                        alternativas.add(alternativaString);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (alternativas.size() > 0) {
                        if (p.toString().contains("HTM")) {
                            String id = getID(p);
                            alternativasArray.put(id, alternativas);
                        }
                    }
                    alternativas = new ArrayList<>();
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return alternativasArray; // retorna um map, onde a chave é um id para um questão, eu seu valor é um List<String> que são as alterntivas dentro de parágrafos
    }

    public static Map<String, List<String>> getImagensInBase64(Path path) throws IOException { // pega as imagens de um html
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
                base64.add(p.getFileName().toString() + encoded);
            }
        }
        if (base64.size() > 0) {
            String oi = "oi";
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
                String nome = getID(p);
                List<String> allLinesHTML = Files.readAllLines(p, StandardCharsets.ISO_8859_1);
                StringBuilder linesWithoutAlternatives = new StringBuilder();
                for (int i = 0; i < allLinesHTML.size(); i ++) {
                    if (! checkLine1(allLinesHTML, i)) linesWithoutAlternatives.append(allLinesHTML.get(i));
                }
                htmls.put(nome, linesWithoutAlternatives.toString());
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

    public static Map<String, String> getAlternativaCorreta(Path path) throws IOException {
        Map<String, String> alternativasCorretas = new HashMap<>();
        DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
        int i = 0;
        for (Path p : arquivos) {
            if (p.getFileName().toString().contains("HTM") && p.getFileName().toString().contains("G")) {
                List<String> linhas = Files.readAllLines(p, StandardCharsets.ISO_8859_1);
                StringBuilder string = new StringBuilder();
                for (String s : linhas) { // juntas todas as linhas do List<String> e joga num StringBuilder
                    string.append(s);
                }
                org.jsoup.nodes.Document document = Jsoup.parse(string.toString()); // HTMl pata Document
                String nome = getID(p);
                String texto = document.text();
                String alternativa = "";
//                if (texto.length() > 6) {
//                    String[] aux = document.text().split(" Gab:");
//                    if (aux[0].length() < 6) alternativa = "JJJ";
//                    else alternativa = aux[0].substring(5);
//                    if (alternativa.charAt(0) == ' ') {
//                        alternativa = alternativa.substring(1);
//                    }
//                }
//                else if (texto.length() == 5) {
//                    alternativa = texto.substring(4);
//                }
//                else if (texto.length() == 6) {
                    alternativa = texto.substring(5);
                //}
                alternativasCorretas.put(nome, alternativa);
            }
        }
        return alternativasCorretas;
    }

    public static String getID(Path p) { // pega um path e pega um id, um número que representa uma questão
        StringBuilder nome = new StringBuilder();
        String[] partes = p.toString().split("/");
        for (int i = 1; i < partes[1].length(); i ++) {
            if (partes[1].charAt(i) == '.') break;
            else nome.append(partes[1].charAt(i));
        }
        return nome.toString();
    }
}