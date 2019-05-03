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
        else {
            scoreList = new PriorityQueue<>(Collections.reverseOrder());
            System.out.println(filepath + " not found. Creating new file.");
        }
    }

    public void add(Score score) { scoreList.add(score);}
    public void remove(String name, int score) {
        scoreList.remove(new Score(name, score));
    }

    //writes the current queue to the file
    public void writeOut() {
        if(scoreList != null)
            ScoreFileHandler.writeOut(scoreList, filepath);
    }

    //compares the object reference itself
    //make sure to pass in the score reference you created earlier, NOT THE ACTUAL SCORE VALUE
    public int getRank(Score s) {
        Object[] arr = scoreList.toArray();
        int count = -1;
        for (int i = 0; i < arr.length; i++) {
            if (s == (Score) arr[i]) {
                count = i + 1;
                break;
            }
        }
        return count;
    }

    public Score[] getTopScores(int numberOfRanks) {
        //java reference issue. fixed now.
        PriorityQueue<Score> temp = new PriorityQueue<>(scoreList);

        int arraysize = (temp.size() > numberOfRanks) ? numberOfRanks : temp.size();
        Score[] out = new Score[arraysize];

        for(int i = 0; i < out.length; i++) { out[i] = temp.poll(); }

        return out;
    }
}
