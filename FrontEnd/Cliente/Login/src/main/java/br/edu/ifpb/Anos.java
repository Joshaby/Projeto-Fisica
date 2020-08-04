package br.edu.ifpb;

public class Anos {
    private int id;
    private String Serie;

    public Anos(int id, String serie) {
        this.id = id;
        Serie = serie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    @Override
    public String toString() {
        return getSerie();
    }
}
