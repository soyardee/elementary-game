package com.soyardee.elementaryGame;


//TODO Some *serious* commenting is necessary for this game

import com.soyardee.questionParser.Score;
import com.soyardee.questionParser.ScoreList;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.setLevel(1);
        game.start();

    }
}
