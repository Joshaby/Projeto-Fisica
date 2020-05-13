package br.edu.ifpb;

import java.util.Arrays;
import java.util.List;

public class Question implements Comparable<Question> {
    private int id;
    private String text;
    private List<String> alternatives;
    private String correctAlternative;

    public Question(int id, String text, List<String> alternatives, String correctAlternative) {
        setId(id);
        setText(text);
        setAlternatives(alternatives);
        setCorrectAlternative(correctAlternative);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<String> getAlternatives() { return alternatives; }
    public void setAlternatives(List<String> alternatives) { this.alternatives = alternatives; }
    public String getCorrectAlternative() { return correctAlternative; }
    public void setCorrectAlternative(String correctAlternative) { this.correctAlternative = correctAlternative; }

    @Override
    public int compareTo(Question question) { return Integer.compare(this.id, question.getId()); }

    @Override
    public String toString() {
        return String.format(
                "Id: %d\nText: %s\nAlternatives: %s\nCorrectAlternative: %s\n",
                getId(), getText(), getAlternatives(), getCorrectAlternative()
        );
    }
}
