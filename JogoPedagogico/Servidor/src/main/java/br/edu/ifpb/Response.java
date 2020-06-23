package br.edu.ifpb;

/*
    Objeto responsável por transmitir a resposta do cliente ao servidor

    ID: ID relacional da questão com base no parametro "id";
    answer: Alternativa escolhida pelo cliente;
    time: Tempo decorrido para solucionar a questão;
    group: Grupo que enviou a resposta;

 */

public class Response {
    private String ID;
    private String answer;
    private double time;
    private Group group;

    public Response(String ID, String answer, double time) {
        setID(ID);
        setAnswer(answer);
        setTime(time);
    }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public Double getTime() { return time; }
    public void setTime(Double time) { this.time = time; }
}
