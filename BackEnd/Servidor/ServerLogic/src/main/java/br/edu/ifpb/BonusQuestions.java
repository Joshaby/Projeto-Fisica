package br.edu.ifpb;

import java.util.*;

public class BonusQuestions {

    private List<String> ids;
    private List<String> groups;
    private Map<String, String> answers;

    public BonusQuestions() {
        ids = new ArrayList<>();
        groups = new ArrayList<>();
        answers = new HashMap<>();
    }

    public List<String> getGroups() {
        return Collections.unmodifiableList(this.groups);
    }

    public void setQuestions(List<String> grupos, String id){
        this.groups.addAll(grupos);
        ids.add(id);
    }





}
