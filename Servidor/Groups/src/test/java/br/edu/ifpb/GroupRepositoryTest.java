package br.edu.ifpb;

import junit.framework.*;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Stream;

public class GroupRepositoryTest extends TestCase{


    public static void main(String[] args) throws RemoteException {
        GroupRepository groupRepository = new GroupRepository();

        HashSet<String> users = new HashSet<String>(Arrays.asList("josé", "maria", "ze", "talison", "japa", "vinicius", "galego", "pedro", "gabriel", "daniel", "joao", "helen", "luiza"));

        ArrayList<User> lista = new ArrayList<>();
        users.iterator().forEachRemaining(u -> {lista.add(new User(u, 1));});

        groupRepository.generateGroups(lista);

        groupRepository.getGroups().iterator().forEachRemaining(System.out::println);
    }

}
