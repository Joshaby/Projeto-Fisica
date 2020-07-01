package br.edu.ifpb;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Base64;
import java.util.Stack;

public class LocalQuestionRepository extends Component {

    private final String SEPARATOR = System.getProperty("os.name").toLowerCase().equals("linux") ? "\\" : "/";

    private QuestionRepository_IF repoQuestionsIf;
    private Stack<Question> questions;

    public LocalQuestionRepository(String ip) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip);
            repoQuestionsIf = (QuestionRepository_IF) registry.lookup("RepoQuestoes");
            questions.addAll(repoQuestionsIf.getQuestions());
        }
        catch (RemoteException | NotBoundException e) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Falha na conexão!",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendAnswer(Group group, String id, String answer) throws RemoteException {
        Answer answerObject = new Answer(id, answer);
        repoQuestionsIf.sendAnswer(group, answerObject);
    }
    public Question getQuestion() {
        Question question = questions.pop();
        return questions.size() > 0 ? question : null;
    }
    public void createQuestionDirectory(Question question) throws IOException {
        String directory = ".cache" + SEPARATOR + "Q" + question.getId() + "_arquivos";
        Files.createDirectories(Path.of(directory));
        if (question.getImages() != null) {
            for (String s : question.getImages()) {
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
}
