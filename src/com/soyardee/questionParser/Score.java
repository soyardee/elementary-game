package com.soyardee.questionParser;

public class Score implements Comparable {
    private int score;
    private String name;


    public Score() {
        this.name = "";
        this.score = 0;
    }

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        Score other = (Score) o;
        if(this.score >= other.getScore()) return 1;
        else return -1;
    }

    public boolean equals(Object o) {
        Score other = (Score) o;
        return other.getName().equals(this.name);
    }

    public String getName() {return name;}
    public int getScore() {return score;}

    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
}
