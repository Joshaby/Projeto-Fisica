package br.edu.ifpb;

import java.util.Collections;
import java.util.List;

public class Group {
    private List<String> members;
    private int year;

    public Group(List<String> members, int year) {
        setMembers(members);
        setYear(year);
    }

    public List<String> getMembers() { return Collections.unmodifiableList(members); }
    public void setMembers(List<String> members) { this.members = members; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public void addMember(String name) { members.add(name); }

    @Override
    public String toString() {
        return "Grupo: "+ "members=" + members +
                ", year=" + year;
    }
}
