package br.edu.ifpb;

import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalQuestionRepositoryTest {
    @Test
    public void QuestionsTest() throws IOException {
        LocalQuestionRepository q = new LocalQuestionRepository();
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
