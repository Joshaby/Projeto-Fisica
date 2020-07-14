package br.edu.ifpb;

/*
    Objeto responsável por transmitir a resposta do cliente ao servidor

    ID: ID relacional da questão com base no parametro "id";
    answer: Alternativa escolhida pelo cliente;

*/

public class Answer {
    private String ID;
    private String answer;

    public Answer(String ID, String answer) {
        setID(ID);
        setAnswer(answer);
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

    @Override
    public String toString() {
        return "ID='" + ID + '\'' +
                ", answer='" + answer + '\'';
    }
}
