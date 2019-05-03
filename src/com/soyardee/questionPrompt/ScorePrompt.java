package com.soyardee.questionPrompt;

import com.soyardee.questionParser.Score;
import com.soyardee.questionParser.ScoreList;

import javax.swing.*;

//TODO add better panel options
public class ScorePrompt extends JFrame {
    //the score entered into the class upon creation to be recorded
    private int score;
    private String name;
    private ScoreList scoreList;
    private Score s;

    public ScorePrompt (int score, ScoreList scoreList) {
        this.score = score;
        this.scoreList = scoreList;
        String input = JOptionPane.showInputDialog(null, "Your score: " + score + "\n Enter Name Below: ");
        if(input != null) {
            s = new Score(input, score);
            scoreList.add(s);
        }
    }

    public Score getScore() {return s;}

    public void write() {scoreList.writeOut();}

    public void showRank() {
        int rank = scoreList.getRank(s);
        String ranks = "Top Scores:\n";
        Score[] scores = scoreList.getTopScores(5);
        for(int i = 1; i <= scores.length; i++) {
            ranks += i + ". " + scores[i-1].getName() + " - " + scores[i-1].getScore() + "\n";
        }
        ranks += "\nYour Rank: " + rank;
        JOptionPane.showMessageDialog(null, ranks);
    }
}
