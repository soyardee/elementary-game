package com.soyardee.elementaryGame;


//TODO Some *serious* commenting is necessary for this game

import com.soyardee.questionParser.Score;
import com.soyardee.questionParser.ScoreList;

public class Main {
    public static void main(String[] args) {

        /*
        Game game = new Game();
        game.setLevel(3);
        game.start();
        */

        ScoreList list = new ScoreList("scores.xml");
        list.remove("Sawyer");
        list.add(new Score("Bob", 101));
        list.writeOut();
    }
}
