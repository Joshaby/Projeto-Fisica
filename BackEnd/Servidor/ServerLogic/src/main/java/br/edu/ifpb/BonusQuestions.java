package br.edu.ifpb;

import java.util.*;

public class BonusQuestions {

    private List<String> ids;
    private List<String> groups;
    private Map<String, String> answers;
    private boolean bonusState;

    public BonusQuestions() {
        ids = new ArrayList<>();
        groups = new ArrayList<>();
        answers = new HashMap<>();
        bonusState = false;
    }

    public BonusQuestions(List<String> groups) {
        this();
        this.groups = groups;
        ids = new ArrayList<>();
    }

    public List<String> getGroups() {
        return Collections.unmodifiableList(this.groups);
    }

    public void setQuestions(List<String> grupos, String id){
        this.groups.addAll(grupos);
        ids.add(id);
        setState();
    }

    public boolean hasQuestion(String id){
        return ids.contains(id);
    }

    public void addAnswer(String groupName, String res) {
        answers.put(groupName, res);
    }

    private boolean checkAnswers() {
        for (String group : groups) {
            if(!answers.containsKey(group)) return false;
        }
        return true;

    }

    private void setState(){
        this.bonusState = true;
    }

    private void resetState(){
        this.bonusState = false;
    }

    private void addBonusQuestion(String id){
        this.ids.add(id);
    }


}
