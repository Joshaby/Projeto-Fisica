package br.edu.ifpb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import javax.imageio.ImageIO;

public class test1 {
    public static void main(String[] args)throws Exception {
        XWPFDocument docx = new XWPFDocument(new FileInputStream("P002044.docx"));
        XWPFWordExtractor we = new XWPFWordExtractor(docx);
        // System.out.println(we.getText());
        String[] out = we.getText().split("\n");
        List<String> aux = new ArrayList<>();
        for (String i : out) {
            aux.add(i);
        }
        // System.out.println(aux);
        stringToMongoDB(aux);
        // extractImages(docx);
//        BufferedImage bImage = ImageIO.read(new File("sample.png")); // conversão entre imagem e byte
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(bImage, "png", bos );
//        byte [] data = bos.toByteArray();
//
//        String encoded = Base64.getEncoder().encodeToString(data); // conversão entre byte pra base64
//        System.out.println(encoded);
//
//        byte[] decoded = Base64.getDecoder().decode(encoded); // conversão entre base64 pra byte
//
//        ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
//        BufferedImage bImage2 = ImageIO.read(bis);
//        ImageIO.write(bImage2, "png", new File("output.png") ); // conversão byte pra imagem
//        System.out.println("image created");
    }

    public static void extractImages(XWPFDocument docx) {
        try {
            List<XWPFPictureData> piclist = docx.getAllPictures();
            System.out.println(piclist.size());
            Iterator<XWPFPictureData> iterator = piclist.iterator();
            int i = 0;
            for (XWPFPictureData aux : piclist) {
                byte[] bytepic = aux.getData();
                System.out.println(aux.getPictureType());
                System.out.println(aux.getFileName().substring(aux.getFileName().length() - 3));
                BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytepic));
                ImageIO.write(imag, "png", new File("/home/jose/" + aux.getFileName()));
            }
            while (iterator.hasNext()) {
                XWPFPictureData pic = iterator.next();
                byte[] bytepic = pic.getData();
                BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytepic));
                ImageIO.write(imag, "png", new File("/home/jose/" + pic.getFileName()));
                i++;
            }

        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private static void stringToMongoDB(List<String> questoes) {
        List<List<String>> questoesMatriz = new ArrayList<>();
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < questoes.size(); i ++) {
            if (questoes.get(i).contains("Questão") && i != 0) {
                questoesMatriz.add(aux);                         // transforma a lista de string do docx, em uma matriz, uma lista de questões, onde cada questão é uma lista
                aux = new ArrayList<>();
            }
            if (questoes.get(i).length() > 2) aux.add(questoes.get(i));
        }

//        for (List<String> I : questoesMatriz) {
//            for (String i : I) System.out.println(i);
//            System.out.println();
//        }
        List<Question> questionList = new ArrayList<>(); // vai percorrendo a matriz, e passando as strings das sublistas para um objeto questão
        for (List<String> questao : questoesMatriz) {
            List<String> texto = new ArrayList<>();
            String cab = questao.get(questao.size() - 1);
            String nome = questao.get(0);
            List<String> alternativas = new ArrayList<>();
            for (int i = 1; i < questao.size(); i ++) {
                if (questao.get(i).charAt(1) == ')') alternativas.add(questao.get(i));
                else texto.add(questao.get(i));
            }
            questionList.add(new Question(nome, texto, alternativas));
        }
        for (Question i : questionList) System.out.println(i);
    }

}
