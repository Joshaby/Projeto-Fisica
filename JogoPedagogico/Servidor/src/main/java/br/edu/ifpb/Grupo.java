package br.edu.ifpb;

import java.util.Collections;
import java.util.List;

public class Grupo {
    private List<String> members;
    private int year;

    public Grupo (List<String> members, int year) {
        setMembers(members);
        setYear(year);
    }

    public List<String> getMembers() { return Collections.unmodifiableList(members); }
    public void setMembers(List<String> members) { this.members = members; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
