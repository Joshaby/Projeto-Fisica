package br.edu.ifpb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

public class createDataBase {

    private static Path path = Path.of("56");

    public static void main(String[] args) throws IOException {
        List<List<String>> nomes = getAlternativas(path);
        List<String> nomes1 = getQuestoes(path);
        List<List<String>> images = getImagensInBase64(path);
        for (List<String> s : images) {
            for (String string : s) {
                System.out.println(string);
            }
            System.out.println("-----------------------------------------------");
        }
//        System.out.println(nomes.size());
//        System.out.println(nomes1);
//        System.out.println(images);
    }

    public static List<List<String>> getAlternativas(Path path) {
        HashSet<String> nomes = new HashSet<>();
        List<List<String>> alternativasArray = new ArrayList<>();
        try {
            DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
            for (Path p : arquivos) {
                if (Files.isDirectory(p)) alternativasArray.addAll(getAlternativas(p));
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
                    if (alternativas.size() > 0) alternativasArray.add(alternativas);
                    alternativas = new ArrayList<>();
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return alternativasArray;
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

    public static List<List<String>> getImagensInBase64(Path path) throws IOException {
        List<List<String>> imagens = new ArrayList<>();
        DirectoryStream<Path> arquivos = Files.newDirectoryStream(path);
        List<String> base64 = new ArrayList<>();
        for (Path p : arquivos) {
            if (Files.isDirectory(p) && (! p.getFileName().toString().contains("Q"))) imagens.addAll(getImagensInBase64(p));
            else if (p.getFileName().toString().contains("gif") || p.getFileName().toString().contains("jpg")) {
                // System.out.println(p.toString());
                BufferedImage bufferedImage = ImageIO.read(new File(p.toString()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, p.getFileName().toString().substring(p.getFileName().toString().length() - 3), byteArrayOutputStream);
                byte[] dataBytes = byteArrayOutputStream.toByteArray();
                String encoded = Base64.getEncoder().encodeToString(dataBytes);
                base64.add(p.getFileName().toString().substring(p.getFileName().toString().length() - 3) + encoded);
            }
        }
        if (base64.size() > 0) imagens.add(base64);
        return imagens;
    }
}
