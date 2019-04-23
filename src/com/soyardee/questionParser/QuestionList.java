package com.soyardee.questionParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionList {
    private List<Question> questionList = new ArrayList<Question>();

    private int currentQuestionIndex = 0;

    public QuestionList(String filename){
        readXML(filename);
        Collections.shuffle(questionList);
    }


    private void readXML(String filename) {
        try{
            XMLQuestionHandler handler = new XMLQuestionHandler();
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(handler);
            InputSource source = new InputSource(QuestionList.class.getResourceAsStream(filename));
            parser.parse(source);

            questionList = handler.getQuestionList();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    //debug
    public void printQuestions() {
        for (Question q: questionList) {
            System.out.println(q);
        }
    }

    public Question getQuestion() {
        if(questionList.size() == 0) return null;
        if(currentQuestionIndex >= questionList.size()) {
            Collections.shuffle(questionList);
            currentQuestionIndex = 0;
        }
        Question out = questionList.get(currentQuestionIndex);
        currentQuestionIndex++;
        return out;
    }
}
