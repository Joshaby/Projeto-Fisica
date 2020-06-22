package br.edu.ifpb;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class filterQuestions { //classe para pegar questões selecionadas em um txt, saõ selecionadas de acorda com seu número

    private static Path questionsLocation = Path.of("/home/jose/Documentos/sistema.elaboreprovas.com.br/files/6/10/");
    private static List<String> ids;

    public static void main(String[] args) throws IOException {
        ids = setIDS();
        System.out.println(ids);
        copyQuestions(questionsLocation);
    }

    public static void copyQuestions(Path path) throws IOException {
        DirectoryStream<Path> filesAndFolder = Files.newDirectoryStream(path);
        for (Path p : filesAndFolder) {
            if (Files.isDirectory(p)) {
                if (checkQuestion(p.getFileName().toString())) {
                    if (! Files.exists(Path.of("Questões/" + p.getFileName().toString()))) {
                        Files.copy(p, Path.of("Questões").resolve(p.getFileName()));
                        DirectoryStream<Path> images = Files.newDirectoryStream(p);
                        for (Path image : images) {
                            Files.copy(image, Path.of("Questões").resolve(p.getFileName().toString() + "/" + image.getFileName().toString()));
                        }
                    }
                }
                else copyQuestions(p);
            }
            else {
                if (checkQuestion(p.getFileName().toString())) {
                    if (! Files.exists(Path.of("Questões/" + p.getFileName().toString())))
                        Files.copy(p, Path.of("Questões").resolve(p.getFileName()));
                }
            }
        }
    }

    private static List<String> setIDS() throws IOException {
        String txt = "questões1AnoMédia.txt";
        List<String> lines = Files.readAllLines(Path.of(txt));
        List<String> idsLocal = new ArrayList<>();
        for (String s : lines) {
            String[] iDS = s.split("', '");
            idsLocal.addAll(Arrays.asList(iDS));
        }
        return idsLocal;
    }

    private static boolean checkQuestion(String name) {
        for (String i : ids) {
            if (name.contains(i)) return true;
        }
        return false;
    }
}
