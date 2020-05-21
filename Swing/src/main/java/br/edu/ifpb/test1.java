package br.edu.ifpb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
        System.out.println(we.getText());
        extractImages(docx);
    }

    public static void extractImages(XWPFDocument docx) {
        try {
            List<XWPFPictureData> piclist = docx.getAllPictures();
            Iterator<XWPFPictureData> iterator = piclist.iterator();
            int i = 0;
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
}
