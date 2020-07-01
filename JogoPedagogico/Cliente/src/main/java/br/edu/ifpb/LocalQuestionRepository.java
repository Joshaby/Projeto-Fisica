package br.edu.ifpb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;

public class LocalQuestionRepository {

    private final String SEPARATOR = System.getProperty("os.name").toLowerCase() == "linux" ? "\\" : "/";

    private QuestionRepository_IF repoQuestionsIf;
    private Stack<Question> questions;

    public LocalQuestionRepository() {
        try {
            questions = new Stack<>();
            Registry registry = LocateRegistry.getRegistry("localhost");
            repoQuestionsIf = (QuestionRepository_IF) registry.lookup("RepoQuestoes");
            questions.addAll(repoQuestionsIf.getQuestions());
            Files.createDirectory(Path.of(".cache"));
        }
        catch (NotBoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendAnswer(Group group, String id, String answer) throws RemoteException {
        Answer answerObject = new Answer(id, answer);
        repoQuestionsIf.sendAnswer(group, answerObject);
    }
    public String getQuestion(List<String> alternatives) throws IOException {
        if (questions.isEmpty()) return null;
        Question question = questions.pop();
        createHTMlFile(question.getText(), question.getText());
        MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
        if (question.getImages() != null) {
            List<String> imagesNames = new ArrayList<>();
            createImagesQuestionDirectory(question.getId(), question.getImages(), imagesNames);
            alternatives.addAll(changeImagePaths(question.getId(), question.getImages(), imagesNames));
        }
        else alternatives.addAll(multipleChoiceQuestion.getAlternatives());
        return question.getId();
    }
    public String getQuestion() throws IOException {
        if (questions.isEmpty()) return null;
        Question question = questions.pop();
        createHTMlFile(question.getId(), question.getText());
        if (question.getImages() != null) {
            createImagesQuestionDirectory(question.getId(), question.getImages());
        }
        return question.getId();
    }
    public void createImagesQuestionDirectory(String id, List<String> images, List<String> imagesNames) throws IOException {
        String directory = ".cache" + SEPARATOR + "Q" + id + "_arquivos";
        Files.createDirectories(Path.of(directory));
        if (images != null) {
            for (String s : images) {
                String imageName = s.substring(0, 12);
                imagesNames.add(imageName);
                String format = imageName.substring(9);
                String base64 = s.substring(12);
                byte[] bytes = Base64.getDecoder().decode(base64);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                ImageIO.write(bufferedImage, format, new File(directory + SEPARATOR + imageName));
            }
        }
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
    public List<String> changeImagePaths(String id, List<String> images, List<String> imagesNames) throws MalformedURLException {
        List<String> imagesWithNewPath = new ArrayList<>();
        for (String imageName : images) {
            for (String s : images) {
                if (s.contains(imageName)) {
                    String[] aux = s.split("Q" + id + "_arquivos/" + imageName);
                    URL url = new File(".cache" + SEPARATOR + "Q" + id + "_arquivos" + SEPARATOR + imageName).toURI().toURL();
                    imagesWithNewPath.add(aux[0] + url + aux[1]);
                    break;
                }
            }
        }
        return imagesWithNewPath;
    }
}
