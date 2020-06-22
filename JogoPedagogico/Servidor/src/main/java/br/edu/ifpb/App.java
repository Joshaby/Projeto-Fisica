package br.edu.ifpb;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepository(2);
        Group group = new Group(Arrays.asList(new String[]{"José", "Henrique", "Azevedo"}), 1);
        Question question = new Question("id", "difficulty", "text");
        questionRepository.sendAnswers(group, question, "a", 1);
        Question question1 = new Question("id1", "difficulty1", "text1");
        questionRepository.sendAnswers(group, question1, "a", 2);
        Group group1 = new Group(Arrays.asList(new String[]{"José1", "Henrique1", "Azevedo1"}), 1);
        Question question2 = new Question("id2", "difficulty2", "text2");
        questionRepository.sendAnswers(group1, question2, "a", 1);
    }
}
