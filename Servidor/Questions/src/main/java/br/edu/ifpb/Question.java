package br.edu.ifpb;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Question implements Serializable {
    private String id;
    private String difficulty;
    private String text;
    private List<String> images;
    private String correctAnswer;

    public Question(String id, String difficulty, String text, String res) {
        setId(id);
        setDifficulty(difficulty);
        setText(text);
        setCorrectAnswer(res);
    }

    public Question(String id, String difficulty, String text, String res, List<String> images) {
        this(id, difficulty, text, res);
        setImages(images);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;

        return this.getId().equals(question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question: " +
                "id='" + id + "'" +
                ", difficulty='" + difficulty + "'" +
                ", text='" + text + "'" +
                ", images=" + images;
    }
}
