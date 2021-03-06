package com.soyardee.userDialog;

import com.soyardee.dataStruct.QuestionList;

/**
 * This class runs on the main game thread, NOT the swing thread.
 * There was some confusion with the multithreaded architecture of the game
 * and the swing components themselves. Apparently they don't share the same
 * thread, leading to some *really* confusing bugs.
 *
 * The worst was the update method. After checking for the null value of the
 * JFrame, it would only continue the method if the isClosed property was true
 * as well (to know when to dispose the window and unpause the game).
 *
 * When I was debugging it, the statement worked fine with the system print method
 * showing the null status of the window. But when I removed the print statement,
 * the game would not resume (isClosed would still return false, despite reading as
 * true in the other class just before checking the if statement).
 *
 * Turns out, the print statement actually synchronizes all threads when it's called.
 * This means the isClosed variable must read the same across both threads. Today I learned
 * about system multithreading and unsafe thread operations in swing.
 */

public class PromptHandler {

    private boolean pause;
    private boolean correctAnswer;


    private PromptInterface prompt;

    public PromptHandler(QuestionList list) {
        pause = false;
        correctAnswer = false;
        prompt = new PromptInterface(list);
    }

    public void openFrame() {
        pause = true;
        correctAnswer = false;
        prompt.createWindow();
    }

    public void update() {
        if(prompt.getFrame() != null){
            if(prompt.isClosed()) {
                pause = false;
                correctAnswer = prompt.isCorrectAnswer();
                prompt.clear();
            }
        }
    }

    public boolean isPaused() {
        update();
        return pause;
    }

    public boolean isCorrectAnswer() {
        boolean out = correctAnswer;
        correctAnswer = false;
        return out;
    }
}
