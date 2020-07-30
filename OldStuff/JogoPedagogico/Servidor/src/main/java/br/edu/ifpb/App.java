package br.edu.ifpb;

import java.util.Arrays;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepository(2);
        Group group = new Group(Arrays.asList(new String[]{"José", "Henrique"}), 2);
        Group group1 = new Group(Arrays.asList(new String[]{"José1", "Henrique1"}), 2);
        Group group2 = new Group(Arrays.asList(new String[]{"José2", "Henrique2"}), 2);
        Question question = new Question("12", "média", "oi");
        Question question1 = new Question("13", "média", "oi");
        Question question2 = new Question("14", "média", "oi");
        Answer answer = new Answer("12", "A");
        Answer answer1 = new Answer("13", "B");
        Answer answer2 = new Answer("14", "C");
//        questionRepository.sendAnswer(group, answer);
//        questionRepository.sendAnswer(group1, answer);
//        questionRepository.sendAnswer(group2, answer);
//        questionRepository.sendAnswer(group2, answer1);
        questionRepository.setQuestions(new String[]{"Média", "Difícil"}, 3, 10);
        Map<Question, String> aux = questionRepository.getQuestions();
        for (Question i : aux.keySet()) {
            System.out.println(i);
        }
    }
}
