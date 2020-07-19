package br.edu.ifpb;

import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        Scoreboard scoreboardObject = new Scoreboard();
        Map<Integer, List<String>> scoreboard = scoreboardObject.getScoreboard(1);
        System.out.println(scoreboard);
    }
}
