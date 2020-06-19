package br.edu.ifpb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
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
import java.util.Iterator;

public class boxLayout extends JFrame {
    private JPanel panel;
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
        URL uri = file.toURI().toURL();
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

        panel3.add(createAlternativeJPanel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>", radioButton, label));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>", radioButton1, label1));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>", radioButton2, label2));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>", radioButton3, label3));
        panel3.add(Box.createRigidArea(new Dimension(25, 25)));
        panel3.add(createAlternativeJPanel("<html><p class=MsoNormal style='margin-left:42.0pt;text-align:justify;text-indent:\n" +
                "-21.0pt'>Resistência elétrica de um fio condutor e pressão de um gás a\n" +
                "volume constante.</p></html>", radioButton4, label4));

        panel1.add(panel3);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Questão 13")); // adiciona uma borda em um componente com título na esquerda
        panel2.add(button);
        panel2.add(Box.createHorizontalGlue()); // cria um espaçador na horizontal
        panel2.add(button1);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(button2);
        add(panel);
        // radioButton.addItemListener(new radioButton());
        // initComponents();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
    }

    private class buttonHandler implements ActionListener {
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
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(boxLayout.this);
            }
        }
    }

    private class radioButton implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (radioButton.isSelected()) System.out.println("oi");
        }
    }

    private void initComponents() throws IOException {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://Joshaby:7070@cluster0-e8gs6.mongodb.net/test?authSource=admin&replicaSet=Cluster0-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true");
        MongoClient client = new MongoClient(uri);
        MongoDatabase database = client.getDatabase("Questões");
        MongoCollection<Document> collection = database.getCollection("1 ano");
        FindIterable<Document> documents = collection.find(Filters.eq("ID", "181269"));
        Iterator filtredDocuments = documents.iterator();
        while (filtredDocuments.hasNext()) {
            Document document = (Document) filtredDocuments.next();
            String id = (String) document.get("ID");
            String html = (String) document.get("Texto");
            Files.write(Path.of("Q" + id + ".HTM"), Collections.singleton(html), StandardCharsets.ISO_8859_1);
            editorPane.setPage(new File("Q" + id + ".HTM").toURI().toURL());
            List<String> imagens = (List<String>) document.get("Imagens");
            initDirectory(id, imagens);
        }
    }

    private void initDirectory(String nome, List<String> arquivos) throws IOException {
        Files.createDirectory(Path.of("Q" + nome + "_arquivos"));
        int num = 1;
        for (String i : arquivos) {
            String nomeImagem = "image";
            String tipo = i.substring(0, 3);
            if (num < 10) {
                nomeImagem += ("00" + num);
            }
            else nomeImagem += ("0" + num);
            nomeImagem += ("." + tipo);
            String base64 = i.substring(3);
            System.out.println(base64);
            byte[] decodec = Base64.getDecoder().decode(base64);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodec);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            ImageIO.write(bufferedImage, tipo, new File("Q" + nome + "_arquivos/" + nomeImagem));
            num ++;
        }
    }

    private JPanel createAlternativeJPanel(String text, JRadioButton radioButton, JLabel label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        label = new JLabel(text);
        panel.add(radioButton);
        // panel.add(Box.createRigidArea(new Dimension(5, 5)));
        panel.add(label);
        return panel;
    }

    private JScrollPane createJScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 0), new EmptyBorder(0, 0, 0, 0)));
        return scrollPane;
    }

    private JScrollPane createJScrollPane(int width, int height) {
        JScrollPane scrollPane = new JScrollPane();

        return scrollPane;
    }
}
