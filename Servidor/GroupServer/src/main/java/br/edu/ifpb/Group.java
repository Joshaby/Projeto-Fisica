package br.edu.ifpb;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Group {
    private int ID;
    private HashSet<User> members;
    private int year;
    private int points;

    public Group(int ID) {
        this.ID = ID;
        this.setMembers(new HashSet<User>());
        this.setYear(1);
        this.setPoints(0);
    }

    public Group(int ID, int year) {
        this.ID = ID;
        this.setYear(year);
        this.setPoints(0);
    }

    public boolean validateGroup(){ return getMembers().size() > 0 && getYear() >= 1 && getYear() <= 3; }

    public List<User> getMembers() { return Collections.unmodifiableList(List.copyOf(members)); }
    public void setMembers(HashSet<User> members) { this.members = members; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public void addMember(User user) { members.add(user); }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    @Override
    public String toString() {
        return "Group{" +
                "ID=" + ID +
                ", members=" + members +
                ", year=" + year +
                ", points=" + points +
                '}';
    }
}
