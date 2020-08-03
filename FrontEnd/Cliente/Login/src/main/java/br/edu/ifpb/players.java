package br.edu.ifpb;

public class players {
    private int id;
    private String jogadores;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getJogadores();
    }

    public players(int id, String jogadores) {
        this.id = id;
        this.jogadores = jogadores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJogadores() {
        return jogadores;
    }

    public void setJogadores(String jogadores) {
        this.jogadores = jogadores;
    }
}
