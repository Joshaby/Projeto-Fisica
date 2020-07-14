package br.edu.ifpb;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> alternatives;
    private boolean containsImage;

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> alternatives,String res, boolean containsImage) {
        super(id, difficulty, text, res);
        setAlternatives(alternatives);
        setContainsImage(containsImage);
    }

    public MultipleChoiceQuestion(String id, String difficulty, String text, List<String> images, List<String> alternatives, String res, boolean containsImage) {
        super(id, difficulty, text, res, images);
        setAlternatives(alternatives);
        setContainsImage(containsImage);
    }

    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public void setContainsImage(boolean containsImage) { this.containsImage = containsImage; }
    public boolean getContainsImage() { return containsImage; }
}
