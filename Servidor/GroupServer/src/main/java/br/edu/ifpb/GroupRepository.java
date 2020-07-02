package br.edu.ifpb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/*
    Classe responsavel por receber, organizar e distribuir os usuarios em grupo, e regir-los corretamente.

    groups: HashSet que irá conter todos os grupos;
    maxGroup: Variavel que determina o número maximo de grupos;

    public boolean generateGroups(User[] Users): Função responsavel por gerar os grupos e alocalos na lista de grupos;
 */

public class GroupRepository implements GroupRepository_IF {
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

    public boolean generateGroups(List<User> users) throws RemoteException {

        if(users.size() == 0){return false;}

        for (User user : users) {
            if (!user.validateUser()) return false;
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

    public boolean addGroup(Group group){
        if(!group.validateGroup()) { return false; }
        this.groups.add(group);
        return true;
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

    @Override
    public boolean checkGroup() throws RemoteException { return false; }

    @Override
    public boolean removeGroup() throws RemoteException { return false; }
}
