package br.edu.ifpb;

import java.util.ArrayList;
import java.util.List;

public class Answers{
    private List<Answer> answers;
    private Integer time;

    public Answers() {
        setAnswers(new ArrayList<>());
        setTime(0);
    }

    public Answers(Integer time, List<Answer> answers) {
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
