package br.edu.ifpb;

import java.util.List;

public class QuestionWithImagesAlternatives extends Question {
    private List<String> imagensAlternativas;

    public QuestionWithImagesAlternatives(List<String> text, String alternativaCorreta, List<String> imagensTexto, List<String> imagensAlternativas) {
        super(text, alternativaCorreta, imagensTexto);
        setImagensAlternativas(imagensAlternativas);
    }
    public QuestionWithImagesAlternatives(List<String> text, String alternativaCorreta, List<String> imagensAlternativas) {
        super(text, alternativaCorreta);
        setImagensAlternativas(imagensAlternativas);
    }

    public List<String> getImagensAlternativas() { return imagensAlternativas; }
    public void setImagensAlternativas(List<String> imagensAlternativas) { this.imagensAlternativas = imagensAlternativas; }

    @Override
    public String toString() {
        return super.toString() + "\n" + imagensAlternativas.toString();
    }
}
