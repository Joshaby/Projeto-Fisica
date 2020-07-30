package br.edu.ifpb;

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

public class boxLayout extends JFrame { //Classe de teste de um possível layout do app do cliente, que nesse caso vai ser um grupo de alunos
    private JPanel panel; // compontentes autoexplicativos
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JButton button;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private ButtonGroup buttonGroup;
    private JEditorPane editorPane;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    public boxLayout() throws IOException, BadLocationException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("Box Layout");
        panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // define a disposição dos componentes dentro de outro componente
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS)); // X_AXIS: disposição da esquerda pra direita
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS)); // Y_AXIS: disposição de cima para baixo
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS)); // Y_AXIS: disposição de cima para baixo
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // adiciona uma borda de 10px a um componente
        button = new JButton("Voltar");
        button1 = new JButton("Avançar");
        button2 = new JButton("Finalizar");
        button1.addActionListener(new buttonHandler());
        button2.addActionListener(new buttonHandler());
        button.addActionListener(new buttonHandler());
        radioButton = new JRadioButton("A -");
        radioButton1 = new JRadioButton("B -");
        radioButton2 = new JRadioButton("C -");
        radioButton3 = new JRadioButton("D -");
        radioButton4 = new JRadioButton("E -");
        buttonGroup = new ButtonGroup(); // usado para criar um lógica entre od radio buttons, não selecionar mais de um ao mesmo temp
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);
        // button2.setPreferredSize(new Dimension(140, 25));
        panel.add(panel1);
        panel.add(Box.createRigidArea(new Dimension(5, 5))); // cria um espaçador de tamanho definido pelo usuário
        panel.add(panel2);
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        File file = new File("Q133393.HTM");
        URL uri = file.toURI().toURL(); // pega o URL do html, para que ele possa ser usado no editorPane
        System.out.println(uri);
        try {
            editorPane.setPage(uri);
        } catch (IOException e) {
            editorPane.setContentType("text/html");
            editorPane.setText("<html>Page not found.</html>");
        }
        scrollPane = new JScrollPane(editorPane); // é usado para ter uma barra de rolamento num JEditoPane
        scrollPane.setPreferredSize(new Dimension(835, 610));
        scrollPane.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 0), new EmptyBorder(0, 0, 0, 0))); // mexe na borda inferior e exterior de um componente
        // panel1.add(Box.createRigidArea(new Dimension(25,25)));
        panel1.add(scrollPane);
        panel1.add(Box.createRigidArea(new Dimension(25, 25)));

        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        panel4.add(radioButton);
        JLabel label = new JLabel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>");
        label.setSize(10, 10);
        panel4.add(label);

        panel1.add(panel3);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Questão 13")); // adiciona uma borda em um componente com título na esquerda
        panel2.add(button);
        panel2.add(Box.createHorizontalGlue()); // cria um espaçador na horizontal
        panel2.add(button1);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(button2);
        add(panel);
        // radioButton.addItemListener(new radioButton());
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); // seta o uso do look and feel GTK na interface
        SwingUtilities.updateComponentTreeUI(this); // faz um update nos compontentes, para o novo look and feel ser usado
    }

    private class buttonHandler implements ActionListener { // Handler de eventos de botões, ira fazer alguma coisa, caso algum botão é clicado
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == button1) {
                panel1.remove(panel3);
                panel1.revalidate();
                panel1.repaint();
            }
            if (actionEvent.getSource() == button2) {
                panel1.add(panel3);
                panel1.revalidate();
                panel1.repaint();
            }
            if (actionEvent.getSource() == button) {
                try {
                    initComponents();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class radioButton implements ItemListener { // Handler de evento de um radiobutton
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (radioButton.isSelected()) System.out.println("oi");
        }
    }

    private void initComponents() throws IOException { // função para pegar uma questão numa coleção em um banco de dados mongoDB
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"); //
        MongoClient client = new MongoClient(uri);                                                                                                                                                                    // Faz conexão com o cluster que possui os banco de dados
        MongoDatabase database = client.getDatabase("Questões"); // referencia o banco de dados "Questões"
        MongoCollection<Document> collection = database.getCollection("2 ano"); // referencia a coleção "2 ano" em "Questões"
        FindIterable<Document> documents = collection.find(Filters.eq("ID", "181269")); // pega algum documento que possuir { "ID" : "181269" }
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
        panel3.add(createAlternativeJPanel(html, radioButton, label)); // cria uma alternativa, olhar o comméntario da função
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel(html, radioButton1, label1));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel(html, radioButton2, label2));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel(html, radioButton3, label3));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel(html, radioButton4, label4));
        panel1.add(panel3);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private JPanel createAlternativeJPanel(String text, JRadioButton radioButton, JLabel label) { // cria um alternativa
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        label = new JLabel(text);
        panel.add(radioButton);
        // panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(label);
        return panel;
    }
}
