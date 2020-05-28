package br.edu.ifpb;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class test extends JFrame {
    private JLabel label;

    public test() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title>D:\\uploadedFiles\\67e590f2b69f4438d643eaa6287197-c118e92b894f426\\p1e9e8bknb1se01jtqfg417akqc64.pdf</title>\n" +
                "    <style type=\"text/css\">\n" +
                "        <!--\n" +
                "        body {\n" +
                "            font-family: Arial;\n" +
                "            font-size: 20.4px\n" +
                "        }\n" +
                "\n" +
                "        .pos {\n" +
                "            position: absolute;\n" +
                "            z-index: 0;\n" +
                "            left: 0px;\n" +
                "            top: 0px\n" +
                "        }\n" +
                "        -->\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <nobr>\n" +
                "        <nowrap>\n" +
                "            <div class=\"pos\" id=\"_0:0\" style=\"center:0\">\n" +
                "            <div class=\"pos\" id=\"_118:134\" style=\"top:134;left:118\">\n" +
                "                <span id=\"_14.7\" style=\"font-weight:bold; font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    Quest&#227;o 02</span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:168\" style=\"top:168;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    Um corpo desenvolve trajet&#243;ria retil&#237;nea sobre um plano horizontal sem atrito, estando sob\n" +
                "                </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:188\" style=\"top:188;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    a a&#231;&#227;o de uma for&#231;a constante. Caso tal for&#231;a cesse de atuar no corpo, este:\n" +
                "                </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:256\" style=\"top:256;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    a) continuar&#225; seu movimento com acelera&#231;&#227;o constante. </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:290\" style=\"top:290;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    b) ir&#225; se movimentar com velocidade constante. </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:324\" style=\"top:324;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    c) para imediatamente. </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:358\" style=\"top:358;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    d) passar&#225; a ter acelera&#231;&#227;o decrescente. </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_147:393\" style=\"top:393;left:147\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    e) ter&#225; sua por&#231;&#227;o inercial vari&#225;vel. </span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_118:427\" style=\"top:427;left:118\">\n" +
                "                <span id=\"_14.7\" style=\" font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    5</span>\n" +
                "            </div>\n" +
                "            <div class=\"pos\" id=\"_118:461\" style=\"top:461;left:118\">\n" +
                "                <span id=\"_14.7\" style=\"font-weight:bold; font-family:Times New Roman; font-size:14.7px; color:#000000\">\n" +
                "                    Gab<span style=\"font-weight:normal\"> : B</span></span>\n" +
                "            </div>\n" +
                "<img src=\"file:output.jpg\" class=\"center\"></div>\n" +
                "        </nowrap>\n" +
                "    </nobr>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        JEditorPane textPane = new JEditorPane();
        textPane.setEditable(false);
        URL url= test.class.getResource("test.html");
        try {
            textPane.setPage(url);
        } catch (IOException e) {
            textPane.setContentType("text/html");
            textPane.setText("<html>Page not found.</html>");
        }
        add(new JScrollPane(textPane));
    }
    public static void main(String[] args) {
        test t = new test();
        t.setSize(700, 700);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setVisible(true);
        t.setLocationRelativeTo(null);
    }
}
