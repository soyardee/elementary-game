package com.soyardee.questionParser;

import java.util.ArrayList;
import java.util.Collections;

public class Question {

    private String prompt = "", correctAnswer = "";
    private ArrayList<String> answers = new ArrayList<>();

    public Question() {}


    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionPrompt() {
        return prompt;
    }

    public String[] getAnswers() {
        Collections.shuffle(answers);
        return answers.toArray(new String[0]);
    }

    public String toString() {
        String firstpart = "prompt: " + prompt + " | correct: " + correctAnswer + " wrong: ";
        for(String s : getAnswers()) {
            firstpart += " ";
            firstpart += s;
        }
        return firstpart;
    }
}
