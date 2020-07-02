package br.edu.ifpb;

/*
    Classe responsavel por armazenar e gerir os usuarios e seus dados.

    String name:      Variavel responsavel pelo nome do aluno;
    String year:      Variavel responsavel pelo grau do aluno;
    Integer points:   Variavel responsavel pelo valor total dos pontos do aluno, individualmente;

 */

public class User {

    private String name;
    private Integer year;
    private Integer points;

    public User(String name, Integer year) {
        this.setName(name);
        this.setYear(year);
        this.setPoints(0);
    }

    public boolean validateUser() {return getName().length() > 0 && getYear() >= 1 && getYear() <= 3 && getPoints() >= 0;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getPoints() { return points; }

    public void setPoints(Integer points) { this.points = points; }

    public Integer getYear() { return year; }

    public void setYear(Integer year) { this.year = year; }

    @Override
    public int hashCode() { return name.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!name.equals(user.name)) return false;
        return year.equals(user.year);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", points=" + points +
                '}';
    }
}
