package br.edu.ifpb;

import java.util.Arrays;
import java.util.List;

// public class Question implements Comparable<Question>
public class Question  {
    private List<String> text;
    private List<String> alternatives;
    private List<String> images;
    private String alternativaCorreta;

    public Question(List<String> text, List<String> alternatives, String alternativaCorreta) {
        setText(text);
        setAlternatives(alternatives);
        setAlternativaCorreta(alternativaCorreta);
    }
    public Question(String id, List<String> text, List<String> alternatives, List<String> images, String alternativaCorreta) {
        this(text, alternatives, alternativaCorreta);
        setImages(images);
    }

    public List<String> getText() { return text; }
    public void setText(List<String> text) { this.text = text; }
    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    public String getAlternativaCorreta() { return alternativaCorreta; }
    public void setAlternativaCorreta(String alternativaCorreta) { this.alternativaCorreta = alternativaCorreta; }

    @Override
    public String toString() {
        return String.format("Text: %s\nAlternatives: %s\n", getText(), getAlternatives().toString());
    }
}
