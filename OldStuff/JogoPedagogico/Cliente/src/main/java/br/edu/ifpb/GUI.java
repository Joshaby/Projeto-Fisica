package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class GUI extends JFrame {
    private JPanel panel; // painel principal, contêm todos os outros paineis
    private JPanel panel1; // painel do headbar
    private JPanel panel2; // painel do editorPane, onde será mostrado o HTML, e da entrada de respostas, seja digitada ou selecionada
    private JPanel panel3; // painel com algumas informações, nome do grupo, ano
    private JPanel panel4; // painel das alternativas
    private JLabel icon; // ícone do app
    private JLabel appName; // nome do Aplicativo
    private JLabel time; // tempo restante para responder a questão
    private JLabel cuurentQuestion; // questão atual
    private JButton nextButton; // botão para avançar para a próxima questão
    // private JButton cancelButton;
    private JEditorPane editorPane; // painel de exibição do HTML
    private JScrollPane scrollPane; // scroll bar do editrPane
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    public GUI () throws IOException {
        try {
            if (System.getProperty("os.name").toLowerCase().equals("linux")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            else if (System.getProperty("os.name").toLowerCase().equals("windows")) UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(15,15);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //Draws the rounded opaque panel with borders.
                graphics.setColor(getBackground());
                graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
                graphics.setColor(getForeground());
                graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
            }
        };
        panel.setBounds(10,10,100,30);
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

        // Painel1 e seus componentes

        appName = new JLabel("Jogo de física");
        appName.setFont(new Font("Jogo de física", Font.BOLD, 15));
        cuurentQuestion = new JLabel(" Questão 1 de 5");
        time = new JLabel("Tempo restante: 2:00");
        nextButton = new JButton("Avançar");
        panel1.add(Box.createRigidArea(new Dimension(15, 15)));
        icon = new JLabel();
        icon.setIcon(new ImageIcon(getClass().getResource("logo.png")));
        panel1.add(icon);
        panel1.add(Box.createRigidArea(new Dimension(10, 10)));
        panel1.add(appName);
        panel1.add(Box.createHorizontalGlue());
        panel1.add(time);
        panel1.add(Box.createHorizontalGlue());
        panel1.add(cuurentQuestion);
        panel1.add(Box.createRigidArea(new Dimension(15, 15)));
        panel1.add(nextButton);
        panel1.add(Box.createRigidArea(new Dimension(10, 10)));

        // Painel2 e seus componentes

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(835, 710));
        scrollPane.setBorder(new EmptyBorder( 0, 0, 0, 0));
        panel2.add(Box.createRigidArea(new Dimension(5, 5)));
        panel2.add(scrollPane);
        panel2.add(Box.createRigidArea(new Dimension(5, 5)));
        panel2.setOpaque(false);

        // Painel3 e seus componetes

        panel3.add(Box.createRigidArea(new Dimension(15, 15)));
        panel3.add(new JLabel("Turma dos babões"));
        panel3.add(Box.createRigidArea(new Dimension(15, 15)));
        panel3.add(new JLabel("Ano: 1º ano"));
        panel3.add(Box.createHorizontalGlue());
        panel3.add(new JLabel("Versão: 1.0-SNAPSHOT"));
        panel3.add(Box.createRigidArea(new Dimension(15, 15)));
        panel3.setOpaque(false);

        //

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(panel2);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(panel3);
        panel.add(Box.createRigidArea(new Dimension(5, 5)));
        add(panel);
        initComponents();
    }
    private void initComponents() throws IOException { // função para pegar uma questão numa coleção em um banco de dados mongoDB
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"); //
        MongoClient client = new MongoClient(uri);                                                                                                                                                                    // Faz conexão com o cluster que possui os banco de dados
        MongoDatabase database = client.getDatabase("Questões"); // referencia o banco de dados "Questões"
        MongoCollection<Document> collection = database.getCollection("1 ano"); // referencia a coleção "2 ano" em "Questões"
        FindIterable<Document> documents = collection.find(Filters.eq("ID", "66092")); // pega algum documento que possuir { "ID" : "181269" }
        Iterator filtredDocuments = documents.iterator(); // iterador para documents
        while (filtredDocuments.hasNext()) {
            Document document = (Document) filtredDocuments.next(); // pega o documento e faz um casting para Document
            String id = (String) document.get("ID"); // pega o ID do documento
            String html = (String) document.get("Texto"); // pega o texto do documento, que vai ser uma Sring que representa um HTML
            Files.write(Path.of("Q" + id + ".HTM"), Collections.singleton(html), StandardCharsets.ISO_8859_1); // cria o arquivo html para ele ser usado na interface
            editorPane.setPage(new File("Q" + id + ".HTM").toURI().toURL()); // coloca o html no editorPane, usando o URL do html
            List<String> alternatives = (List<String>) document.get("Alternativas"); // as alternativas da questão, isso em um List
            List<String> imagens = (List<String>) document.get("Imagens"); // pegas as imagens em base64 e com seu nome final, isso em um List
            List<String> nomesImagens = new ArrayList<>(); // List com os nomes finais de cada imagem
            initDirectory(id, imagens, nomesImagens); // olhar coméntario da função
            initAlternatives(alternatives, nomesImagens, id); // olhar coméntario da função
        }
    }

    private void initDirectory(String nome, List<String> arquivos, List<String> nomesImagens) throws IOException { // vai iniciar o diretório de dependência do html, no caso, colocar as imagens no diretório
        String directory = "Q" + nome + "_arquivos"; // nome da pasta
        Files.createDirectory(Path.of(directory)); // cria a pasta
        for (String s : arquivos) {
            String imageName = s.substring(0, 12); // pega o nome final da imagem
            nomesImagens.add(imageName); // adiciona o nome em um List para uso futuro
            System.out.println(imageName.substring(9)); // print para ver extensão da imagem, se ela é gif ou jpg
            String base64 = s.substring(12); // pega a imagem em base64
            byte[] decoded = Base64.getDecoder().decode(base64); // converte o base64 em um lista de bytes
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decoded); // transforma a lista de bytes em um buffer array de bytes na mémoria, ira estabelecer uma conexão entres esse bytes e algum método que leia esses bytes
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream); // cria um buffer com dados da imagem, que está em bytes
            ImageIO.write(bufferedImage, imageName.substring(9), new File(directory + "/" + imageName)); // pega os bytes e grava em um arquivo, é necessário dar o formato original da imagem para n ocorrer erros de criação
        }
    }

    private void initAlternatives(List<String> alternatives, List<String> nomesImagens, String nome) throws MalformedURLException { // função de teste para iniciar as alternativas, para alternativas que possuem imagens.
        String localAlternative = "";
        for (String alternative : alternatives) {
            if (alternative.contains(nomesImagens.get(0))) { // pega a altertiva que contem o nome da primeira imagem em nomesImagens
                localAlternative = alternative;
                break;
            }
        }
        String[] p = localAlternative.split("Q" + nome + "_arquivos/" + nomesImagens.get(0)); // quebra a String no nome da imagem, antecedido por uma pasta. Isso é feito poir, para visualizar a imagem, é necessário usar sua URL, que basicamente consiste do no path absoluto antecedido da String file://
        File file = new File("Q" + nome + "_arquivos/" + nomesImagens.get(0)); // cria um File que representa a imagem
        URL url = file.toURI().toURL(); // pega o URL da imagem
        String html = "<html>" + p[0] + url + p[1] + "</html>"; // cria o html do label, agora com o URL da imagem
        System.out.println(html); // printa o html
        // vai criar as alterntivas
        panel4.add(createAlternativeJPanel(html, radioButton, label)); // cria uma alternativa, olhar o comméntario da função
        panel4.add(Box.createRigidArea(new Dimension(55, 55)));
        panel4.add(createAlternativeJPanel(html, radioButton1, label1));
        panel4.add(Box.createRigidArea(new Dimension(55, 55)));
        panel4.add(createAlternativeJPanel(html, radioButton2, label2));
        panel4.add(Box.createRigidArea(new Dimension(55, 55)));
        panel4.add(createAlternativeJPanel(html, radioButton3, label3));
        panel4.add(Box.createRigidArea(new Dimension(55, 55)));
        panel4.add(createAlternativeJPanel(html, radioButton4, label4));
        panel2.add(panel4);
    }

    private JPanel createAlternativeJPanel(String text, JRadioButton radioButton, JLabel label) { // cria um alternativa
        JPanel panel = new JPanel();
        radioButton = new JRadioButton();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        label = new JLabel(text);
        panel.add(radioButton);
        // panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(label);
        return panel;
    }
}
