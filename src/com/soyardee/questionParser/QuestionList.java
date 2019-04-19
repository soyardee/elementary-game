package com.soyardee.questionParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.ArrayList;
import java.util.List;

public class QuestionList {
    private List<Question> questionList = new ArrayList<Question>();

    public QuestionList(String filename){
        readXML(filename);
    }


    public void readXML(String filename) {
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

    public void printQuestions() {
        for (Question q: questionList) {
            System.out.println(q);
        }
    }
}
