package com.soyardee.dataStruct;

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
        if(this.score > other.getScore()) return 1;
        else return -1;
    }

    //technically not the best way of doing it, if this were for real I would probably search for a UUID reference.
    //somebody could have the same score and name.
    public boolean equals(Object o) {
        Score other = (Score) o;
        return other.getName().equals(this.name) && other.getScore() == this.score;
    }

    public String getName() {return name;}
    public int getScore() {return score;}

    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
}
