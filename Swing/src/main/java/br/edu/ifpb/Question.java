package br.edu.ifpb;

import java.util.List;

public abstract class Question  {
    private List<String> text;
    private String alternativaCorreta;
    private List<String> imagensTexto = null;

    public Question(List<String> text, String alternativaCorreta, List<String> imagensTexto) {
        setText(text);
        setAlternativaCorreta(alternativaCorreta);
        setImagensTexto(imagensTexto);
    }
    public Question(List<String> text, String alternativaCorreta) {
        setText(text);
        setAlternativaCorreta(alternativaCorreta);
    }

    public List<String> getText() { return text; }
    public void setText(List<String> text) { this.text = text; }
    public String getAlternativaCorreta() { return alternativaCorreta; }
    public void setAlternativaCorreta(String alternativaCorreta) { this.alternativaCorreta = alternativaCorreta; }
    public void setImagensTexto(List<String> imagensTexto) { this.imagensTexto = imagensTexto; }
    public List<String> getImagensTexto() { return imagensTexto; }

    @Override
    public String toString() {
        String out = "Question{" +
                "text=" + text.toString() + "\n" +
                ", alternativaCorreta='" + alternativaCorreta  + "\n" ;
        if (imagensTexto == null) return out;
        return out + ", imagensTexto='" + imagensTexto.toString();
    }
}
