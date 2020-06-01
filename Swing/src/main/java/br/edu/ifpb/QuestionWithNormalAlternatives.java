package br.edu.ifpb;

import java.util.List;

public class QuestionWithNormalAlternatives extends Question {
    private List<String> alternativas;

    public QuestionWithNormalAlternatives(List<String> text, String alternativaCorreta, List<String> imagensTexto, List<String> alternativas) {
        super(text, alternativaCorreta, imagensTexto);
        setAlternativas(alternativas);
    }
    public QuestionWithNormalAlternatives(List<String> text, String alternativaCorreta, List<String> alternativas) {
        super(text, alternativaCorreta);
        setAlternativas(alternativas);
    }

    public List<String> getAlternativas() { return alternativas; }
    public void setAlternativas(List<String> alternativas) { this.alternativas = alternativas; }

    @Override
    public String toString() {
        return super.toString() + "\n" + alternativas.toString();
    }
}
