package br.edu.ifpb;

import java.util.*;

/*
    Classe responsavel por receber, organizar e distribuir os usuarios em grupo, e regir-los corretamente.

    groups: HashSet que irá conter todos os grupos;
    maxGroup: Variavel que determina o número maximo de grupos;

    public boolean generateGroups(User[] Users): Função responsavel por gerar os grupos e alocalos na lista de grupos;
 */

public class GroupRepository{
    private HashSet<Group> groups;
    private int maxGroup;

    public GroupRepository() {
        groups = new HashSet<>();
        this.maxGroup = 5;
    }

    public GroupRepository(int maxGroup) {
        groups = new HashSet<>();
        this.maxGroup = maxGroup;
    }

    private void removeGroup(Group group){
        if(!group.validateGroup()) return;

        this.groups.remove(group);
    }

    private void realocateMember(User user, int GroupID){
        Group aux = null;
        for (Group group : getGroups()) {
            if(group.getID() == GroupID) {
                aux = group;
                break;
            }
        }

        if(aux != null) aux.addMember(user);
    }

    public boolean realocateGroup(){
        ArrayList<Group> lessAmountOfPoints = new ArrayList<>();
        double menor = Double.POSITIVE_INFINITY;

        for (Group group1 : this.getGroups()) {
            if (group1.getPoints() <= menor) {
                if (group1.getPoints() != menor) {
                    lessAmountOfPoints = new ArrayList<>();
                }
                lessAmountOfPoints.add(group1);
                menor = group1.getPoints();
            }
        }

        menor = Double.POSITIVE_INFINITY;
        Group group = null;
        for (Group lAOP : lessAmountOfPoints) {
            if(lAOP.getMembers().size() < menor) { group = lAOP; }
        }

        if(group != null) this.removeGroup(group);


        return true;
    }

    public boolean generateGroups(List<User> users){

        if(users.size() == 0){return false;}

        int comparador = users.get(0).getYear();

        for (User user : users) {
            if (!user.validateUser() || user.getYear() != comparador) return false;
        }

        ArrayList<Group> aux = new ArrayList<>();
        Collections.shuffle(users);
        for (int i = 1; i <= getMaxGroup(); i++){ aux.add(new Group(i)); }

        for(int i = 0; i < users.size(); i++) {
            aux.get(i%5).addMember(users.get(i));
        }

        aux.forEach(this::addGroup);

        this.groups.removeIf(group -> group.getMembers().size() == 0);

        return true;
    }

    private void addGroup(Group group){
        if(!group.validateGroup()) { return; }
        this.groups.add(group);
    }

    public Group getGroupByID(int ID){
        for (Group group : List.copyOf(this.getGroups())) {
            if (group.getID() == ID) return group;
        }
        return null;
    }

    public HashSet<Group> getGroups() { return this.groups; }

    public boolean setGroups(HashSet<Group> groups) {
        for (Group group : groups) {
            if(!group.validateGroup()) return false;
        }
        this.groups = groups;
        return true;
    }

    public int getMaxGroup() { return maxGroup; }

    public boolean setMaxGroup(int maxGroup) {
        if(maxGroup <= 0){return false;}
        this.maxGroup = maxGroup;
        return true;
    }
}
