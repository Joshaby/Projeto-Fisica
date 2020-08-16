package br.edu.ifpb;

/*
    Objeto responsável por transmitir a resposta do cliente ao servidor

    ID: ID relacional da questão com base no parametro "id";
    answer: Alternativa escolhida pelo cliente;

*/

import java.util.Objects;

public class Answer {
    private String ID;
    private String answer;
    private int time;

    public Answer(String ID, String answer, int time) {
        setID(ID);
        setAnswer(answer);
        setTime(time);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return Objects.equals(ID, answer.ID);
    }

    @Override
    public int hashCode() {
        return ID != null ? ID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "ID='" + ID + '\'' +
                ", answer='" + answer + '\'' +
                ", time=" + time +
                '}';
    }
}
