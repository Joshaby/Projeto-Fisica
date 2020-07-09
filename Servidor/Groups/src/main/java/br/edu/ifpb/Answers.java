package br.edu.ifpb;

import java.util.ArrayList;
import java.util.List;

public class Answers{
    private List<Answer> answers;
    private Integer time;

    public Answers(Integer time, List<Answer> answers) {
        setAnswers(answers);
        setTime(time);
    }

    public void addAnswer(Answer answer, int time){
        this.answers.add(answer);
        this.time += time;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
