package br.edu.ifpb;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class filterQuestions {

    private static Path questionsLocation = Path.of("/home/jose/Documentos/Engenharia da computação/sistema.elaboreprovas.com.br/files/6/10");
    static String[] ids = new String[]{"212733","212892","212952","213096","213101","193819","194073","194144","203536","203575","212849","193557","193598","193816","193999","194061","194087","194093","212726","176006","176113","176156","176271","181226","181240","181269"};

    public static void main(String[] args) throws IOException {
        Files.createDirectory(Path.of("Questions"));
        copyQuestions(questionsLocation);
    }

    public static void copyQuestions(Path path) throws IOException {
        DirectoryStream<Path> filesAndFolder = Files.newDirectoryStream(path);
        for (Path p : filesAndFolder) {
            if (Files.isDirectory(p)) {
                if (checkQuestion(p.getFileName().toString())) {
                    if (! Files.exists(Path.of("Questions/" + p.getFileName().toString()))) {
                        Files.copy(p, Path.of("Questions").resolve(p.getFileName()));
                        DirectoryStream<Path> images = Files.newDirectoryStream(p);
                        for (Path image : images) {
                            Files.copy(image, Path.of("Questions").resolve(p.getFileName().toString() + "/" + image.getFileName().toString()));
                        }
                    }
                }
                else copyQuestions(p);
            }
            else {
                if (checkQuestion(p.getFileName().toString())) {
                    if (! Files.exists(Path.of("Questions/" + p.getFileName().toString())))
                        Files.copy(p, Path.of("Questions").resolve(p.getFileName()));
                }
            }
        }
    }

    private static boolean checkQuestion(String name) {
        for (String i : ids) {
            if (name.contains(i)) return true;
        }
        return false;
    }
}
