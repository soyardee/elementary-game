package com.soyardee.questionPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PromptInterface implements ActionListener {

    JPanel pausePanel;
    JButton resumeButton;
    JFrame frame;

    //ayy learned how thread volatility works in swing
    volatile boolean isClosed;

    public String test = "some data here that has not been dereferenced yet";


    ComponentListener componentListener = new ComponentAdapter() {
        @Override
        public void componentHidden(ComponentEvent e) {
            super.componentHidden(e);
            isClosed = true;
        }
    };

    public void createWindow() {
        frame = new JFrame();
        isClosed = false;
        pausePanel = new JPanel(new FlowLayout());
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        pausePanel.add(resumeButton);
        frame.add(pausePanel);
        frame.setPreferredSize(new Dimension(200, 100));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addComponentListener(componentListener);
    }


    public PromptInterface() {
        frame = null;
        isClosed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if(event == resumeButton) {
            frame.setVisible(false);
        }
    }

    public boolean isClosed() {return isClosed;}

    public void clear() {
        frame = null;
    }

    public JFrame getFrame() {return frame;}
}
