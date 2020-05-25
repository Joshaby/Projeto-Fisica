package br.edu.ifpb;

import java.util.Arrays;
import java.util.List;

// public class Question implements Comparable<Question>
public class Question  {
    private String id;
    private List<String> text;
    private List<String> alternatives;
    private List<String> images;

    public Question(String id, List<String> text, List<String> alternatives) {
        setId(id);
        setText(text);
        setAlternatives(alternatives);
    }
    public Question(String id, List<String> text, List<String> alternatives, List<String> images) {
        this(id, text, alternatives);
        setImages(images);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getText() { return text; }
    public void setText(List<String> text) { this.text = text; }
    public String getAlternatives() { return alternatives.toString(); }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

//    @Override
//    public int compareTo(Question question) { return Integer.compare(this.id, question.getId()); }

    @Override
    public String toString() {
        return String.format("Id: %s\nText: %s\nAlternatives: %s\n", getId(), getText(), getAlternatives());
    }
}
