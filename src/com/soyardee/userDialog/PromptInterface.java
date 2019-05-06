package com.soyardee.userDialog;

import com.soyardee.dataStruct.Question;
import com.soyardee.dataStruct.QuestionList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Not a huge fan of this system. This detracts from the gameplay as the user has to take their
 * hands off the keyboard to click the correct answer. The game will immediately resume, and it can
 * be difficult to properly transition back to the game. Not to mention that the question window
 * pops up immediately in a separate thread, causing the player to become confused.
 */


public class PromptInterface implements ActionListener {

    private Random random;

    private JPanel pausePanel, buttonPanel, questionPanel, answerPanel;
    private JButton[] buttonOptions;
    private QuestionList questionList;
    private Question question;
    private String[] answerStrings;
    private JFrame frame;

    //ayy learned how thread volatility works in swing
    private volatile boolean isClosed, correctAnswer, wrongAnswer;


    private ComponentListener componentListener = new ComponentAdapter() {
        @Override
        public void componentHidden(ComponentEvent e) {
            super.componentHidden(e);
            isClosed = true;
        }
    };


    public void createWindow() {
        isClosed = false;
        correctAnswer = false;
        wrongAnswer = false;
        frame = new JFrame();

        question = questionList.getQuestion();
        answerStrings = question.getAnswers();
        buttonOptions = new JButton[answerStrings.length];

        pausePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());

        questionPanel = new JPanel(new FlowLayout());
        JLabel prompt = new JLabel(question.getQuestionPrompt());

        answerPanel = new JPanel(new GridLayout(answerStrings.length, 1));

        //populate the panels

        for(int i=0; i < answerStrings.length; i++) {
            String currentLetter = Character.toString((char) (i+65));
            JButton button = new JButton(currentLetter);
            button.addActionListener(this);
            buttonOptions[i] = button;
            buttonPanel.add(buttonOptions[i]);

            JLabel answer = new JLabel(currentLetter + ": " + answerStrings[i]);
            JPanel answerPanelFlow = new JPanel(new FlowLayout());

            answerPanelFlow.add(answer);

            answerPanel.add(answerPanelFlow);
        }

        questionPanel.add(prompt);
        pausePanel.add(questionPanel, BorderLayout.NORTH);
        pausePanel.add(answerPanel, BorderLayout.CENTER);
        pausePanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(pausePanel);

        frame.setTitle("Reload");
        frame.setPreferredSize(new Dimension(250, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addComponentListener(componentListener);
    }

    public PromptInterface(QuestionList list) {
        this.questionList = list;
        this.random = new Random();
        frame = null;
        isClosed = false;
    }

    private boolean checkAnswer(int index) {
        if(!wrongAnswer) {
            correctAnswer = answerStrings[index].equals(question.getCorrectAnswer());
        }

        return answerStrings[index].equals(question.getCorrectAnswer());
    }

    private void disableButtons(JButton[] buttons, int correctIndex) {
        for(int i = 0; i < buttons.length; i++) {
            if(i != correctIndex) {
                buttons[i].setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton) {
            int i = 0;
            while(i < buttonOptions.length) {
                if (e.getSource() == buttonOptions[i]){
                    break;
                }
                i++;
            }
            if(checkAnswer(i)) {
                frame.setVisible(false);
            }
            else{
                disableButtons(buttonOptions, Arrays.asList(answerStrings).indexOf(question.getCorrectAnswer()));
                wrongAnswer = true;
            }
        }
    }

    public boolean isClosed() {return isClosed;}
    public boolean isCorrectAnswer() {return correctAnswer;}

    public void clear() {
        frame = null;
    }

    public JFrame getFrame() {return frame;}
}
