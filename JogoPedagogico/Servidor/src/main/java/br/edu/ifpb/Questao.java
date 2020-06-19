package br.edu.ifpb;

import java.util.Collections;
import java.util.List;

public class Questao {
    private String id;
    private String difficulty;
    private String text;
    private String correctAlternative;
    private List<String> images;

    public Questao(String id, String difficulty, String text, String correctAlternative, List<String> images) {
        setId(id);
        setDifficulty(difficulty);
        setText(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAlternative() {
        return correctAlternative;
    }

    public void setCorrectAlternative(String correctAlternative) {
        this.correctAlternative = correctAlternative;
    }

    public List<String> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
