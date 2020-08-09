package br.edu.ifpb;

import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

public class ServerTests {


    @Test
    public void QuestsConsumerTest() throws RemoteException {
        int grupos = 5;
        Random r = new Random();
        for(int vezes = 0; vezes < 30; vezes++){
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
}
