package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.*;

public class BonusQuestions {

    private QuestionRepository questionRepository;
    private GroupRepository groupRepository;
    private String ID;
    private List<String> groups;
    private Map<String, String> answers;
    private boolean bonusState;
    private int round;

    public BonusQuestions(QuestionRepository questionRepository, GroupRepository groupRepository, int round, String id) {
        ID = id;
        groups = new ArrayList<>();
        answers = new HashMap<>();
        bonusState = false;
        this.questionRepository = questionRepository;
        this.groupRepository = groupRepository;
        this.round = round;
    }

    public List<String> getGroups() {
        return Collections.unmodifiableList(this.groups);
    }

    public void setQuestions(List<String> grupos, String id){
        this.groups.addAll(grupos);
        ID = id;
        setState();
    }

    public boolean hasQuestion(String id){
        return ID.contains(id);
    }

    public void addAnswer(String groupName, String res) throws RemoteException {
        answers.put(groupName, res);
        if(checkAnswers()){
             tiebreaker();
        }
    }

    private boolean checkAnswers() {
        for (String group : groups) {
            if(!answers.containsKey(group)) return false;
        }
        return true;
    }

    private void tiebreaker() throws RemoteException {
        String correctAnswer = questionRepository.getCorrectAnswer(this.ID);

        for (String group : answers.keySet()) {
            if(answers.get(group).equals(correctAnswer)) {
                groupRepository.getGroupByName(group).addPoints(1);
            }
        }

        List<String> aux = groupRepository.realocateGroup(round);
        if(aux == null){
            resetState();
        }
        groups = new ArrayList<>();
        answers = new HashMap<>();
        ID = "";
    }

    public boolean hasGroup(String name){
        return this.groups.contains(name);
    }

    public boolean getState(){
        return this.bonusState;
    }

    private void setState(){
        this.bonusState = true;
    }

    private void resetState(){
        this.bonusState = false;
    }

    private void finishBonusState(){
        resetState();
        this.groups = new ArrayList<>();

    }

    private void addBonusQuestion(String id){
        this.ID = id;
    }


}
