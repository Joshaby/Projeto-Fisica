package br.edu.ifpb;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> alternatives;
    private boolean containsImage;

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> alternatives, boolean containsImage) {
        super(id, difficulty, text);
        setAlternatives(alternatives);
        setContainsImage(containsImage);
    }

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> images, List<String> alternatives, boolean containsImage) {
        super(id, difficulty, text, images);
        setAlternatives(alternatives);
        setContainsImage(containsImage);
    }

    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public void setContainsImage(boolean containsImage) { this.containsImage = containsImage; }
    public boolean getContainsImage() { return containsImage; }
}
