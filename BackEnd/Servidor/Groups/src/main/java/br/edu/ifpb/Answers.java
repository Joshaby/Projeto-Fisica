package br.edu.ifpb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Answers{
    private Set<Answer> answers;
    private Integer time;

    public Answers() {
        setAnswers(new HashSet<>());
        setTime(0);
    }

    public Answers(Integer time, Set<Answer> answers) {
        setAnswers(answers);
        setTime(time);
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        this.time += answer.getTime();
    }

    public boolean hasAnswer(String id){
        for (Answer answer : answers) {
            if(answer.getID().equals(id)) return true;
        }
        return false;
    }

    public Answer getAnswer(String id){
        for (Answer answer : answers) {
            if(answer.getID().equals(id))
                return answer;
        }
        return null;
    }

    public void removeAnswerbyID(String id){
        for (Answer answer : answers) {
            if(answer.getID().equals(id)){
                answers.remove(answer);
                setTime(getTime() - answer.getTime());
            }
        }
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
