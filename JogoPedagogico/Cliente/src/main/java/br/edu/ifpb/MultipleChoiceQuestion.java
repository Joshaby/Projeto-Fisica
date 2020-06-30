package br.edu.ifpb;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> alternatives;

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> alternatives) {
        super(id, difficulty, text);
        setAlternatives(alternatives);
    }

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> images, List<String> alternatives) {
        super(id, difficulty, text, images);
        setAlternatives(alternatives);
    }

    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
}
