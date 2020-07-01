package br.edu.ifpb;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository implements GroupRepository_IF {
    private List<Group> groups;

    public GroupRepository() {
        groups = new ArrayList<>();
    }

    @Override
    public void registerGroup() {

    }

    @Override
    public boolean checkGroup() {
        return false;
    }
}
