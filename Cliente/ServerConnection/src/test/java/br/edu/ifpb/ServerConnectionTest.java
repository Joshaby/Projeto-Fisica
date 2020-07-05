package br.edu.ifpb;

import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerConnectionTest {
    @Test
    public void QuestionsTest() throws IOException {
        ServerConnection q = new ServerConnection();
        List<String> alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
        alternatives = new ArrayList<>();
        System.out.println(q.getQuestion(alternatives));
        System.out.println(alternatives);
        System.out.println();
//        alternatives = new ArrayList<>();
//        System.out.println(q.getQuestion(alternatives));
    }
}
