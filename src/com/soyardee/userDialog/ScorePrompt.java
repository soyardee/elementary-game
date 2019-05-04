package com.soyardee.userDialog;

import com.soyardee.dataStruct.Score;
import com.soyardee.dataStruct.ScoreList;

import javax.swing.*;

//TODO add better panel options (cards layout to prevent these modal dialogs, gross)
public class ScorePrompt extends JFrame {

    private ScoreList scoreList;
    private Score s;
    private String inputName;

    public ScorePrompt (int score, ScoreList scoreList) {
        this.scoreList = scoreList;
        inputName = JOptionPane.showInputDialog(null, "Your score: " + score + "\n Enter Name Below: ");
        if(inputName == null){
            s = null;
        }
        else{
            inputName = inputName.trim();
            s = new Score(inputName, score);
            if(!inputName.equals("")) {
                scoreList.add(s);
            }
        }
    }

    public String getInputName() {return inputName;}

    public void write() {scoreList.writeOut();}

    public void showRank() {
        int rank = scoreList.getRank(s);
        Score[] scores = scoreList.getTopScores(5);

        StringBuilder b = new StringBuilder();
        b.append("Top Scores:\n");

        for(int i = 1; i <= scores.length; i++) {
            b.append( i );
            b.append(". ");
            b.append(scores[i-1].getName());
            b.append(" - ");
            b.append(scores[i-1].getScore());
            b.append("\n");
        }
        b.append( "\nYour Rank: ");
        b.append(rank);
        JOptionPane.showMessageDialog(null, b.toString());
    }
}
