package br.edu.ifpb;

import java.util.Arrays;
import java.util.List;

public class Question implements Comparable<Question> {
    private int id;
    private String text;
    private List<String> alternatives;
    private List<String> images;

    public Question(int id, String text, List<String> alternatives, List<String> images) {
        setId(id);
        setText(text);
        setAlternatives(alternatives);
        setImages(images);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    @Override
    public int compareTo(Question question) { return Integer.compare(this.id, question.getId()); }

    @Override
    public String toString() {
        return String.format("Id: %d\nText: %s\nAlternatives: %s\n", getId(), getText(), getAlternatives());
    }
}
