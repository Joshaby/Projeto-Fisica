package br.edu.ifpb;

/*
    Objeto responsável por transmitir a resposta do cliente ao servidor

    ID: ID relacional da questão com base no parametro "id";
    answer: Alternativa escolhida pelo cliente;

*/

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
    public String toString() {
        return "Answer{" +
                "ID='" + ID + '\'' +
                ", answer='" + answer + '\'' +
                ", time=" + time +
                '}';
    }
}
