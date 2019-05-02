package com.soyardee.questionParser;

import java.io.File;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Basically a wrapper class for the PriorityQueue. Makes calling file actions really easy.
 */
public class ScoreList {

    private PriorityQueue<Score> scoreList;
    private String filepath;


    public ScoreList(String fileLocation) {
        this.filepath = fileLocation;
        File f = new File(filepath);
        if(f.exists()) {
            scoreList = ScoreFileHandler.readIn(filepath);
        }
        else{
            scoreList = new PriorityQueue<>(Collections.reverseOrder());
        }


    }

    public void add(Score score) {
        scoreList.add(score);
    }

    public void remove(String name) {
        scoreList.remove(new Score(name, 0));
    }

    //writes the current queue to the file
    public void writeOut() {
        if(scoreList != null)
            ScoreFileHandler.writeOut(scoreList, "/score/scores.xml");
    }

    public void print() {
        for (Score s: scoreList) {
            System.out.println(s.getName());
        }
    }
}
