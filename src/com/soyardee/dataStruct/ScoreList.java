package com.soyardee.dataStruct;

import com.soyardee.dataStruct.parsers.ScoreFileHandler;

import java.io.File;
import java.util.Arrays;
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

    //compares the object reference
    //make sure to pass in the score reference you created earlier, NOT THE ACTUAL SCORE VALUE
    public int getRank(Score s) {
        Object[] arr = getScoreArray();
        int count = -1;
        for (int i = 0; i < arr.length; i++) {
            if (s == arr[i]) {
                count = i + 1;
                break;
            }
        }
        return count;
    }

    public Score[] getTopScores(int numberOfRanks) {
        //java reference issue. fixed now.
        Object[] arr = getScoreArray();

        int arraysize = (arr.length > numberOfRanks) ? numberOfRanks : arr.length;
        Score[] out = new Score[arraysize];

        for(int i = 0; i < out.length; i++) { out[i] = (Score) arr[i]; }

        Arrays.sort(out);
        Collections.reverse(Arrays.asList(out));

        return out;
    }

    private Object[] getScoreArray() {
        Object[] arr = scoreList.toArray();
        Arrays.sort(arr);
        Collections.reverse(Arrays.asList(arr));
        return arr;
    }
}
