package br.edu.ifpb;

import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

public class ServerTests {


    @Test
    public void QuestsConsumerTest() throws RemoteException {
        ServerLogic s = new ServerLogic(3);
        s.getGroupRepository().registerGroups(AuxiliarMethods.groupGen(5), 1);


        s.getQuestions("helen").iterator().forEachRemaining(question -> {
            try {
                s.sendAnswer(1, "helen", question.getId(), "A", 1200);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        Assert.assertTrue(s.getQuestions("helen").isEmpty());
    }

    @Test
    public void GroupRemoverTest() {
        try {
            Random r = new Random();
            GroupRepository g = new GroupRepository();
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
