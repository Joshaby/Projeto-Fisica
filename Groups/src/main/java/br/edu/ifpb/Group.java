package br.edu.ifpb;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Group implements Comparable<Group> {
    private int ID;
    private HashSet<User> members;
    private int year;
    private int points;
    private ArrayList<Answer> answers;

    public Group(int ID) {
        this.setID(ID);
        this.setMembers(new HashSet<>());
        this.setYear(1);
        this.setPoints();
    }

    public Group(int ID, int year) {
        this.ID = ID;
        this.setYear(year);
        this.setPoints();
    }

    public ArrayList<Answer> getAnswers() { return answers; }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public boolean validateGroup() {
        return getMembers().size() > 0 && getYear() >= 1 && getYear() <= 3;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
        this.members.iterator().forEachRemaining(user -> {user.addPoints(points);});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return new EqualsBuilder()
                .append(ID, group.ID)
                .append(year, group.year)
                .append(members, group.members)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ID)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Group " + ID + ":\n" +
                "     ID= " + ID + "\n" +
                "     members=" + members + "\n" +
                "     year=" + year + "\n" +
                "     points=" + points + "\n";
    }

    @Override
    public int compareTo(Group o) {
        return Integer.compare(this.getID(), o.getID());
    }
}
