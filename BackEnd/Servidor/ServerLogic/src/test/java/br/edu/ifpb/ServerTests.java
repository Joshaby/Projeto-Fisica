package br.edu.ifpb;

import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class ServerTests {


    @Test
    public void QuestsConsumerTest() throws RemoteException {
        int grupos = 5;
        Random r = new Random();
        for(int vezes = 0; vezes < 5; vezes++){
            System.out.println("===================== Test: " + vezes + " =====================");
            ServerLogic s = new ServerLogic(5);
            s.getGroupRepository().registerGroups(AuxiliarMethods.groupGen(grupos), 1);
            s.startGame();
            for(int i = 0; i < grupos-1 ; i++){
                //"helen", "Luiza", "Talison", "Kennedy", "josé"

                s.groupRepository.getGroups().iterator().forEachRemaining(group -> {
                    try {
                        s.getQuestions(group.getName()).iterator().forEachRemaining(question -> {
                            try {
                                s.sendAnswer(s.getRound(), group.getName(), question.getId(), String.valueOf((char) (65 + r.nextInt(4))), r.nextInt(200));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
            Assert.assertEquals(1,s.groupRepository.getGroups().size());
            Assert.assertFalse(s.isGameStarted);
        }
    }

    @Test
    public void GroupRemoverTest() {
        Random r = new Random();
        GroupRepository g = new GroupRepository();
        try {
            g.registerGroups(AuxiliarMethods.groupGen(5), 1);
            g.getGroups().iterator().forEachRemaining(group -> {
                group.addPoints(r.nextInt(15));
            });
            List<String> res = g.realocateGroup(1);
            Assert.assertTrue((g.getGroups().size() == 4 && res == null)
                                                              ||
                                         (g.getGroups().size() == 5 && !res.isEmpty()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void GroupStupidTest(){
        Random r = new Random();
        try{
            ServerLogic s = new ServerLogic(10);
            s.getGroupRepository().registerGroups(AuxiliarMethods.groupGen(4), 1);
            s.startGame();

            List<Group> groups = List.copyOf(s.groupRepository.getGroups());

            List<String> aux = new ArrayList<>();

            s.getQuestions(groups.get(0).getName()).iterator().forEachRemaining(question -> {
                aux.add(question.getId());
            });

            String sa = "";

            for (String aux1 : aux) {
                s.sendAnswer(1, groups.get(0).getName(), aux1, "A", 200);
                sa = aux1;
            }
            System.out.println(s.getQuestions(groups.get(0).getName()));
            System.out.println(s.getQuestions(groups.get(0).getName()));
            s.sendAnswer(1, groups.get(0).getName(), sa, /*String.valueOf((char) (65 + r.nextInt(4)))*/"G", r.nextInt(200));
            System.out.println("cu");
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
