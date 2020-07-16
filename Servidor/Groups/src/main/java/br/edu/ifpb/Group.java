package br.edu.ifpb;

import java.util.*;

/*
    Classe responsavel por armazenar e organizar todos os dados relativos a cada grupo cadastrado
 */
public class Group implements Comparable<Group> {

//COLLECTIONS
    private Map<Integer, Answers> answers;
    private Set<User> members;

//DEFAULT VARIABLES
    private String name;
    private int year;
    private int points;

//CONSTRUCTORS
    //DEFAULT CONSTRUCTOR
    public Group(String name, int year) {
        this.setMembers(new HashSet<>());
        this.setName(name);
        this.setYear(year);
        this.setPoints();
        this.setAnswers(new HashMap<>());
    }

// CONSTRUCTOR WITH POINTS
    public Group(String name, int year,HashSet<User> members, int points) {
        this.setMembers(members);
        this.setName(name);
        this.setYear(year);
        this.addPoints(points);
        this.setAnswers(new HashMap<>());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Answers> answers) {
        this.answers = answers;
    }

    public void addAnswers(List<Answer> answer, Integer time, int round) {
        this.answers.put(round, new Answers(time, answer));
    }

    public boolean validateGroup() {
        return !(getMembers().size() > 0 && getYear() >= 1 && getYear() <= 3);
    }

    public List<User> getMembers() {
        return Collections.unmodifiableList(List.copyOf(members));
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public int getPoints() {
        return points;
    }

    private void setPoints() {
        this.points = 0;
    }

    public void addPoints(int point) {
        this.points += point;
        this.members.iterator().forEachRemaining(user -> {
            user.addPoints(points);
        });
    }

    public void addAnswer(int round, Answer answer, int time) {
        this.answers.put(round, new Answers());
        this.answers.get(round).addAnswer(answer, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.getName().equals(((Group) o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return this.getName() + ":\n" +
                "     members=" + members + "\n" +
                "     year=" + year + "\n" +
                "     points=" + points + "\n";
    }

    @Override
    public int compareTo(Group o) {
        return this.getName().compareTo(o.getName());
    }


}
