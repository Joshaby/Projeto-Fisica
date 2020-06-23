package br.edu.ifpb;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepository(2);
        Group group = new Group(Arrays.asList(new String[]{"José", "Henrique"}), 2);
        Group group1 = new Group(Arrays.asList(new String[]{"José1", "Henrique1"}), 2);
        Group group2 = new Group(Arrays.asList(new String[]{"José2", "Henrique2"}), 2);
        Question question = new Question("12", "média", "oi");
        Question question1 = new Question("13", "média", "oi");
        Question question2 = new Question("14", "média", "oi");
        Response response = new Response("12", "A",  2.0);
        Response response1 = new Response("13", "B",  2.0);
        Response response2 = new Response("14", "C",  2.0);
        questionRepository.sendAnswer(group, response);
        questionRepository.sendAnswer(group1, response);
        questionRepository.sendAnswer(group2, response);
        questionRepository.sendAnswer(group2, response1);
    }
}
