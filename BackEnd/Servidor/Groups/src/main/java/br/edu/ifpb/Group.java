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
    private int rounds;

//CONSTRUCTORS
    //DEFAULT CONSTRUCTOR

    public Group(String name, int year) {
        this.setMembers(new HashSet<>());
        this.setName(name);
        this.setYear(year);
        this.setPoints();
        this.setAnswers(new HashMap<>());
        this.setRounds(4);
    }

    public Group(String name, int year, int rounds) {
        this.setMembers(new HashSet<>());
        this.setName(name);
        this.setYear(year);
        this.setPoints();
        this.setAnswers(new HashMap<>());
        this.setRounds(rounds);
    }

// CONSTRUCTOR WITH POINTS
    public Group(String name, int year,HashSet<User> members, int points) {
        this.setMembers(members);
        this.setName(name);
        this.setYear(year);
        this.addPoints(points);
        this.setAnswers(new HashMap<>());
        this.setRounds(4);
    }

// GETTERS

    public String getName() {
        return name;
    }

    public Map<Integer, Answers> getAnswers() {
        return answers;
    }

    public Answers getAnswersByRound(int round){
        if(answers.get(round) == null){
            answers.put(round, new Answers());
        }
        return answers.get(round);
    }

    public List<User> getMembers() {
        return Collections.unmodifiableList(List.copyOf(members));
    }

    public int getYear() {
        return year;
    }

    public int getPoints() {
        return points;
    }

    public int getRounds() {
        return rounds;
    }

//SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setAnswers(Map<Integer, Answers> answers) {
        this.answers = answers;
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void setPoints() {
        this.points = 0;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    // INCREMENTORS

    public void addAnswers(Set<Answer> answer, Integer time, int round) {
        this.answers.put(round, new Answers(time, answer));
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addPoints(int point) {
        this.points += point;
        this.members.iterator().forEachRemaining(user -> {
            user.addPoints(points);
        });
    }

    public void addAnswer(int round, Answer answer) {
        try{
            this.answers.get(round).addAnswer(answer);
        }
        catch (Exception e){
            this.answers.put(round, new Answers());
            this.answers.get(round).addAnswer(answer);
        }

    }

// OTHER METHODS

    public boolean validateGroup() {
        return !(getMembers().size() > 0 && getYear() >= 1 && getYear() <= 3);
    }

    @Override
    public boolean equals(Object o) {
        return (o != null && getClass() == o.getClass()) && this.getName().equals(((Group) o).getName());
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
        return Integer.compare(this.getPoints(), o.getPoints());
    }

}
