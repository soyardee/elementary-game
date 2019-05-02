package com.soyardee.questionPrompt;

import javax.swing.*;


public class ScorePrompt extends JFrame {
    //the score entered into the class upon creation to be recorded
    private int score;
    private String name;

    public ScorePrompt (int score) {
        this.score = score;
        String input = JOptionPane.showInputDialog(null, "Enter your name for this score");
    }

    private void enterScore(ScorePrompt scorePrompt) {

    }

}
