package com.soyardee.elementaryGame;

import javax.swing.*;

//TODO Some *serious* commenting is necessary for this game

public class Main {
    public static void main(String[] args) {
        //introduce the game
        //TODO make this less debug looking and actually implement a better system
        JOptionPane.showMessageDialog(null, "Welcome to the game! <Insert game objective here>");

        Game game = new Game();
        game.start();
    }
}
