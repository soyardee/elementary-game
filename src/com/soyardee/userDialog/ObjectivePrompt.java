package com.soyardee.userDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ObjectivePrompt extends JDialog implements ActionListener {
    private JDialog dialog;
    private JPanel buttonPanel, textPanel, gridPanel;
    private JButton okButton;
    private JTextArea textPane;
    private Dimension screenSize;
    private String instructions;
    private JScrollPane scrollPane;

    public ObjectivePrompt() {
        init();
    }

    private void init() {
        loadInstructions();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        dialog = new JDialog(this, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setTitle("How To Play");

        gridPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();
        textPanel = new JPanel();
        textPane = new JTextArea(instructions);
        scrollPane = new JScrollPane(textPane);

        textPane.setEditable(false);
        textPane.setLineWrap(true);
        textPane.setWrapStyleWord(true);

        Font font = new Font("SansSerif", Font.BOLD, 20);
        textPane.setFont(font);


        okButton = new JButton("ok");
        okButton.addActionListener(this);

        buttonPanel.add(okButton);
        textPanel.add(scrollPane);

        gridPanel.add(buttonPanel, BorderLayout.SOUTH);
        gridPanel.add(scrollPane, BorderLayout.CENTER);

        Dimension d = new Dimension(800, 400);
        dialog.getContentPane().add(gridPanel);
        dialog.setPreferredSize(d);
        dialog.setLocation(
                screenSize.width/2 - d.width/2,
                screenSize.height/2 - d.height/2);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void loadInstructions() {
        instructions = "";
        try {
            InputStream in = getClass().getResourceAsStream("/questions/instructions.txt");
            instructions = readFromInputStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton) {
            dialog.setVisible(false);
            dialog.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
