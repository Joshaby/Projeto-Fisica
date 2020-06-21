package br.edu.ifpb;

public class App {
    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepository(2);
        questionRepository.setQuestions("Difícil", 10);
    }
}
