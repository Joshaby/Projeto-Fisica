package br.edu.ifpb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;

public class ServerConnection {

    private final String SEPARATOR = System.getProperty("os.name").toLowerCase().equals("linux") ? "/" : String.format("\\") ;

    private ServerConnection_IF serverConnection;
    private Stack<Question> questions;

    public ServerConnection() {
        try {
            questions = new Stack<>();
            Registry registry = LocateRegistry.getRegistry("localhost"); // irá estabelecer conexão com o servidor
            serverConnection = (ServerConnection_IF) registry.lookup("RepoQuestoes"); // irá pegar a referência do stub RepoQuestoes
            for (Question question : serverConnection.getQuestions()) {
                relativePathToURL(question);
                questions.push(question);
            }
            if (! Files.exists(Path.of(".cache"))) { // crirá um pasta oculta chamada cache, com o HTML e imagens das questões
                Files.createDirectory(Path.of(".cache"));
            }
        }
        catch (NotBoundException | IOException e) {
            ExeceptionPanel execeptionPanel = new ExeceptionPanel(e.getMessage());
            System.exit(0);
        }
    }
    public void relativePathToURL(Question question) throws MalformedURLException {
        if (question instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
            if (multipleChoiceQuestion.getContainsImage()) {
                List<String> alternatives = multipleChoiceQuestion.getAlternatives();
                System.out.println(alternatives);
                for (int i = 0; i < alternatives.size(); i ++) {
                    for (String image : multipleChoiceQuestion.getImages()) {
                        String imageName = image.substring(0, 12);
                        if (alternatives.get(i).contains(imageName)) {
                            String[] aux = alternatives.get(i).split("Q" + multipleChoiceQuestion.getId() + "_arquivos/" +  imageName);
                            String finalAlternative = aux[0] + new File(".cache" + SEPARATOR +"Q" + multipleChoiceQuestion.getId() + "_arquivos" + SEPARATOR +  imageName).toURI().toURL() + aux[1];
                            alternatives.set(i, finalAlternative);
                        }
                    }
                }
            }
        }
    }
    public void sendAnswer(int groupId, String id, String answer) throws RemoteException { // vai enviar a respotas por servidor
        serverConnection.sendAnswer(groupId, id, answer);
    }
    public String getQuestion(List<String> alternatives) throws IOException {
        if (questions.isEmpty()) {
            removeDirectory();
            return null;
        }
        Question question = questions.pop();
        createHTMlFile(question.getId(), question.getText());
        if (question instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
            if (question.getImages() != null) createImagesQuestionDirectory(question.getId(), question.getImages());
            alternatives.addAll(multipleChoiceQuestion.getAlternatives());
        }
        else {
            if (question.getImages() != null) createImagesQuestionDirectory(question.getId(), question.getImages());
        }
        return question.getId();
    }
    public void createImagesQuestionDirectory(String id, List<String> images) throws IOException {
        String directory = ".cache" + SEPARATOR + "Q" + id + "_arquivos";
        Files.createDirectories(Path.of(directory));
        if (images != null) {
            for (String s : images) {
                String imageName = s.substring(0, 12);
                String format = imageName.substring(9);
                String base64 = s.substring(12);
                byte[] bytes = Base64.getDecoder().decode(base64);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                ImageIO.write(bufferedImage, format, new File(directory + SEPARATOR + imageName));
            }
        }
    }
    public void createHTMlFile(String id, String text) throws IOException {
        Files.write(Path.of(".cache" + SEPARATOR + "Q" + id + ".HTM")
                , Collections.singletonList(text)
                , Charset.defaultCharset()
                , StandardOpenOption.CREATE_NEW);
    }
    public void removeDirectory() throws IOException {
        DirectoryStream<Path> directoriesAndFiles = Files.newDirectoryStream(Path.of(".cache"));
        for (Path path : directoriesAndFiles) {
            if (Files.isDirectory(path) && path.getFileName().toString().contains("_arquivos")) {
                DirectoryStream<Path> directory = Files.newDirectoryStream(path);
                for (Path image : directory) {
                    Files.delete(image);
                }
                Files.delete(path);
            }
            if (path.getFileName().toString().contains(".HTM")) Files.deleteIfExists(path);
        }
    }
}
