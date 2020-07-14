package br.edu.ifpb;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
    private String id;
    private String difficulty;
    private String text;
    private List<String> images;

    public Question(String id, String difficulty, String text) {
        setId(id);
        setDifficulty(difficulty);
        setText(text);
    }

    public Question(String id, String difficulty, String text, List<String> images) {
        this(id, difficulty, text);
        setImages(images);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<String> getImages() { return Collections.unmodifiableList(images); }
    public void setImages(List<String> images) { this.images = images; }

    @Override
    public String toString() {
        return "Question: " +
                "id='" + id + "'" +
        ", difficulty='" + difficulty + "'" +
        ", text='" + text + "'" +
        ", images=" + images;
    }
}
